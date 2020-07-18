;;;; First Clojure experiment.  Bad implementation of abstract data types.
;;;; Public domain.  (I don't recommend using this though.)

(defn invoke
  "Nicer syntax for method invocation on objects."
  [object method & parameters]
  (apply (object method) object parameters))

(def an-object
  {:true (fn [self] true)
   :false (fn [self] false)})

(println (invoke an-object :true))
(println (invoke an-object :false))
