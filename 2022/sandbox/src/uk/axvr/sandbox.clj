(ns uk.axvr.sandbox
  (:require [clojure.walk :as walk]
            [clojure.java.io :as io])
  (:import [java.io PushbackReader]))

(defn read-clj-resource
  "Safely read a Clojure file from JVM resources."
  [path]
  (when-let [res (some-> path io/resource)]
    (with-open [rdr (io/reader res)]
      (binding [*read-eval* false]
        (read {:read-cond :preserve, :features #{}}
              (PushbackReader. rdr))))))

(read-clj-resource "example.clj")

(defn create-sandbox []
  (let [ns (gensym "ns")]
    (create-ns ns)
    ns))

(defn verify-code [ns allowed-ns code]
  (walk/walk
    (fn [sym]
      (if (symbol? sym)
        (if-let [var (ns-resolve ns sym)]
          (let [ns (-> var meta :ns .name)]
            ;; TODO: only allow use of symbols added via "sandbox-require".
            ;; TODO: how to handle symbols created in the sandbox?
            (if (allowed-ns ns)
              var
              (throw
                (ex-info (str "Use of namespace " ns " in sandbox not allowed.")
                         {:var var
                          :allowed-ns allowed-ns}))))
          sym)
        sym))
    identity
    code))


;; TODO: how to prevent Java interop?  (E.g. '(System/currentTimeMillis))

(defn sandbox-require [ns & reqs]
  (let [prev-ns (.name *ns*)]
    (in-ns ns)
    (apply require reqs)
    (in-ns prev-ns)))

(def my-sandbox (create-sandbox))
(def code '(print "Hello world!"))
(sandbox-require my-sandbox
  '[uk.axvr.sandbox.dsl :refer :all])
(verify-code my-sandbox #{'uk.axvr.sandbox.dsl} code)
(let [my-ns (.name *ns*)]
  (in-ns my-sandbox)
  (try
    (binding [*print-length* 100
              *print-level*  4]
      (eval code))
    (finally
      (in-ns my-ns))))
(remove-ns my-sandbox)
