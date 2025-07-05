(ns en-rule
  "Collection of extensions for O'Doyle rules."
  (:require [odoyle.rules :as o]
            [clojure.data :as data])
  (:import (clojure.lang Namespace)))

;; TODO: can this be made simpler?

(def ^:dynamic ^:private *observed-rule-executions* nil)
(def ^:dynamic ^:private *observed-fact-changes* nil)
(def ^:dynamic ^:private *prev-facts* nil)

(defn- diff [before after]
  (into {}
        (remove (comp nil? second))
        (zipmap [:removed :inserted #_:common]
                (data/diff before after))))

(defmacro ^:private observe! [session data]
  `(when *observed-rule-executions*
     (let [new-facts# (set (o/query-all ~session))]
       (vswap! *observed-rule-executions* conj ~data)
       (vswap! *observed-fact-changes* conj
               (diff *prev-facts* new-facts#))
       (set! *prev-facts* new-facts#))))

(defn- observer [{:as _rule, :keys [name]}]
  {:then
   (fn [f session match]
     (observe! session {:rule name, :op :then, :match match})
     (f session match))
   :then-finally
   (fn [f session]
     (observe! session {:rule name, :op :then-finally})
     (f session))})

(defn -enrich-rules
  "Not intended for external use."
  [ruleset rules-source]
  (mapv
   (fn [{:as rule, :keys [name]}]
     (let [rule-source (get rules-source name)]
       (merge (meta rule-source)
              {::source (o/parse ::o/rule rule-source)}
              rule)))
   ruleset))

(defmacro ruleset+
  "Replacement for `odoyle.rules/ruleset` that enriches the rule with
  additional data that can be used for reporting."
  [rules]
  `(-> ~rules o/ruleset (-enrich-rules '~rules)))

(defn fire-rules+
  "Replacement for `odoyle.rules/fire-rules` that instruments for reporting."
  ([session]
   (fire-rules+ session {}))
  ([session opts]
   (binding [*observed-rule-executions* (volatile! (::store session []))
             *observed-fact-changes*    (volatile! (::fact-changes session []))
             *prev-facts*               (::prev-facts session #{})]
     (observe! session {:rule ::fire-rules})
     ;; Insert empty execution to resync rule executions with fact-changes.
     (vswap! *observed-rule-executions* conj nil)
     (let [session (o/fire-rules session opts)]
       (assoc session
              ::store        @*observed-rule-executions*
              ::prev-facts   *prev-facts*
              ::fact-changes (vswap! *observed-fact-changes* conj
                                     (diff *prev-facts* (set (o/query-all session)))))))))

(defn inspect [session & rulesets]
  (cond-> {:raw-session (dissoc session ::fact-changes ::store ::prev-facts)
           :executions  (mapv #(assoc %1 :fact-changes %2)
                              (::store session)
                              (::fact-changes session))}
          (seq rulesets) (assoc :rules (group-by :name (flatten rulesets)))))

(defn add-rules
  "Add rules to an existing (or new) O'Doyle rules session.  The rules will be
  automatically \"observed\" for analysis and reporting unless opted-out."
  ([rules]
   (add-rules (o/->session) rules))
  ([session rules & {:as _opts, :keys [observe?], :or {observe? true}}]
   (transduce
    (map (if observe? #(o/wrap-rule % (observer %)) identity))
    (completing o/add-rule)
    session
    rules)))

(defmacro ns-qualify-keys
  "For any keyword keys in `m` that are not namespace qualified, qualify them
  with the namespace given by `ns`.  If no `ns` is specified, defaults to the
  value of `clojure.core/*ns*`."
  ([m]
   `(ns-qualify-keys ~m *ns*))
  ([m ns]
   `(let [ns# ~ns]
      (update-keys ~m
                   (fn [k#]
                     (if (simple-keyword? k#)
                       (keyword (if (instance? Namespace ns#)
                                  (str ns#)
                                  (name ns#))
                                (name k#))
                       k#))))))

;; TODO: build out reporting functions from the inspect one.

(comment
  (import '(java.time Instant LocalDate)
          '(java.time.temporal ChronoUnit))

  (def education-options-guesser-rules
    (ruleset+
     {::education-options
      ^{:en-rule/doc "When queried, returns a list of possible education options."}
      [:what [::education option mandatory?]]

      ::calc-student-age
      ^{:en-rule/doc "Calculates the age from the student DOB."}
      [:what
       [::student ::date-of-birth dob]
       [::date ::today today]
       :then (o/insert! ::student ::age (.get (LocalDate/.until dob today) ChronoUnit/YEARS))]

      ::primary?
      ^{:en-rule/doc "Determine if primary school is an option."}
      [:what [::student ::age age]
       :when (<= 5 age 11)
       :then (o/insert! ::education ::primary true)]

      ::secondary?
      [:what [::student ::age age]
       :when (<= 11 age 16)
       :then (o/insert! ::education ::secondary true)]

      ::further?
      [:what [::student ::age age]
       :when (<= 16 age 18)
       :then (o/insert! ::education ::college true)]

      ::apprenticeship?
      [:what [::student ::age age]
       :when (<= 16 age)
       :then (o/insert! ::education ::apprenticeship (< age 18))]

      ::higher?
      [:what [::student ::age age]
       :when (<= 18 age)
       :then (o/insert! ::education ::university false)]}))

  (def session (add-rules education-options-guesser-rules))

  (def resulting-session
    (-> session
        (o/insert ::date ::today (LocalDate/now))
        (o/insert ::student (ns-qualify-keys {:date-of-birth (LocalDate/parse "2008-01-01")}))
        ;; FIXME: empty {:fact-changes {}} in executions.
        ;; FIXME: subsequent fire-rules have incorrect inserted facts.
        ;; fire-rules+
        ;; (o/insert ::student ::date-of-birth (LocalDate/parse "2014-08-29"))
        fire-rules+))

  (inspect resulting-session education-options-guesser-rules)
  (o/query-all resulting-session ::education-options)
  )
