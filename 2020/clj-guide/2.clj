;;;; <https://clojure.org/guides/learn/sequential_colls>
;;;; Public domain.  No rights reserved.

[1 2 3] ; Vector

(get ["abc" false 99] 0)
(get ["abc" false 99] 1)

(get ["abc" false 99] 14)
(get ["abc" nil 99] 1)

(count [1 2 3])
(count "foobar")

(vector 1 2 3)

(conj [1 2 3] 4 5 6)
(conj [1 2 3] [4 5 6])

(def v [1 2 3])
(conj v 4 5 6)

(def cards '(10 :ace :jack 9))
(first cards)
(second cards)
(rest cards)
(conj cards :queen)

(def stack '(:a :b))
(peek stack)
(pop stack)


(def players #{"Alice" "Bob" "Kelly"})

(conj players "Fred")

(disj players "Bob" "Sal")

(contains? players "Kelly")
(contains? (disj players "Kelly") "Kelly")

(conj (sorted-set) "Bravo" "Charlie" "Sigma" "Alpha")
(sorted-set "Bravo" "Charlie" "Sigma" "Alpha")


(def players #{"Alice" "Bob" "Kelly"})
(def new-players ["Tim" "Sue" "Greg"])
(into players new-players)


(def scores {"Fred" 1400
             "Bob" 1240
             "Angela" 1024})

(def scores {"Fred" 1400, "Bob" 1240, "Angela" 1024})

(assoc scores "Sally" 0)
(assoc scores "Bob" 0)

(dissoc scores "Bob")

(get scores "Angela")

(def directions {:north 0
                 :east 1
                 :south 2
                 :west 3})

(directions :north)

(:north directions)

(def bad-lookup-map nil)
(bad-lookup-map :foo)
(:foo bad-lookup-map)


(get scores "Sam" 0)
(directions :northwest -1)
(:foo bad-lookup-map 1)

(contains? scores "Fred")
(find scores "Fred")
(find scores "Sam")

(keys scores)
(vals scores)


(def players #{"Alice" "Bob" "Kelly"})

(zipmap players (repeat 0))

(doc repeat)
(doc zipmap)

(into {} (map (fn [player] [player 0]) players))

(reduce (fn [m player]
          (assoc m player 0))
          {}
          players)

(def new-scores {"Angela" 300, "Jeff" 900})
(merge scores new-scores)

(doc merge)

(source merge)

(merge-with + scores new-scores)


(def sm (sorted-map "Bravo" 204
                    "Alpha" 35
                    "Sigma" 99
                    "Charlie" 100))

(keys sm)
(vals sm)


(def person
  {:first-name "Kelly"
   :last-name "Keen"
   :age 32
   :occupation "Programmer"})

(get person :occupation)

(person :occupation)

(:occupation person)

(:favourite-colour person "beige")


(assoc person :occupation "Baker")

(dissoc person :age)


(def company
  {:name "WidgetCo"
   :address {:street "123 Main St"
             :city "Springfield"
             :state "IL"}})

(get-in company [:address :city])

(doc assoc-in)
(doc update-in)

(assoc-in company [:address :street] "303 Broadway")


(defrecord Person [first-name last-name age occupation])

(def kelly (->Person "Kelly" "Keen" 32 "Programmer"))

(def kelly (map->Person {:first-name "Kelly"
                         :last-name "Keen"
                         :age 32
                         :occupation "Programmer"}))

(kelly :first-name)
(:first-name kelly)


(str "2 is " (if (even? 2) "even" "odd"))

(if (true? false) "impossible")

(if true :truthy :falsey)

(if (Object.) :truthy :falsey)

(if [] :truthy :falsey)

(if 0 :truthy :falsey)

(if false :truthy :falsey)

(if nil :truthy :falsey)


(if (even? 5)
  (do (println "even")
      true)
  (do (println "odd")
      false))


(when (neg? x)
  (throw (RuntimeException. (str "x must be positive: " x))))

(let [x 5]
  (cond
    (< x 2) "x is less than 2"
    (< x 10) "x is less than 10"))

(let [x 11]
  (cond
    (< x 2) "x is less than 2"
    (< x 10) "x is less than 10"
    :else "x is greater than or equal to 10"))

(defn foo [x]
  (case x
    5 "x is 5"
    10 "x is 10"))

(foo 10)
(foo 11)

(defn foo [x]
  (case x
    5 "x is 5"
    10 "x is 10"
    "x isn't 5 or 10"))

(foo 10)
(foo 11)


(dotimes [i 3]
  (println i))

(range 3)
(range 3 9)

(doseq [n (range 3)]
  (println n))

(doseq [n (range 3 9)]
  (println n))

(doseq [letter [:a :b]
        number (range 3)]
  (prn [letter number]))


(for [letter [:a :b]
      number (range 3)]
  [letter number])


(loop [i 0]
  (if (< i 10)
    (recur (inc i))
    i))

(defn increase [i]
  (if (< i 10)
    (recur (inc i))
    i))


(try
  (/ 2 0)
  (catch ArithmeticException e
    "divide by zero")
  (finally
    (println "cleanup")))

(try
  (throw (Exception. "something went wrong"))
  (catch Exception e (.getMessage e)))

(try
  (throw (ex-info "There was a problem" {:detail 42}))
  (catch Exception e
    (prn (:detail (ex-data e)))))


(let [f (clojure.java.io/writer "/tmp/new")]
  (try
    (.write f "some text")
    (finally
      (.close f))))

(with-open [f (clojure.java.io/writer "/tmp/new")]
  (.write f "some text"))
