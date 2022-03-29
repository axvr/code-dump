(def x 42)

(do
  (println "Before:" x)
  (let [x 5]
    (println "During:" x))
  (println "After:" x))

;; recur will not work if you use this macro.
(defmacro my-let [binds & body]
  (let [binds (apply hash-map binds)]
    `((fn [~@(keys binds)]
        ~@body)
      ~@(vals binds))))

(do
  (println "Before:" x)
  (my-let [x 5]
    (println "During:" x)
    (when (< x 10)
      (recur (inc x))))
  (println "After:" x))

(macroexpand-1
  '(my-let [x 5]
     (println "During:" x)))

(do
  (println "Before:" x)
  ((fn [x]
     (println "During:" x)) 5)
  (println "After:" x))

(defn foo [x]
  (println "Before:" x)
  (my-let [x 5]
    (println "During:" x)
    (when (< x 10)
      (recur (inc x))))
  (println "After:" x))

(foo x)

(defn foo [x]
  (println "Before:" x)
  (let [x 5]
    (println "During:" x)
    (when (< x 10)
      (recur (inc x)))))
