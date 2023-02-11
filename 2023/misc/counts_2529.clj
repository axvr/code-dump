[-2 -1 -1 1 2 3]
[-3 -2 -1 0 0 1 2]
[5 20 66 1314]

(->> [-3 -2 -1 0 0 1 2]
     (group-by
       #(cond
          (pos? %) :pos
          (neg? %) :neg
          :else    :zero))
     ((juxt :pos :neg))
     (map count)
     (apply max))

(->> [-3 -2 -1 0 0 1 2]
     (remove zero?)
     (partition-by pos?)
     (map count)
     (apply max))

(transduce
  (comp
    (remove zero?)
    (partition-by pos?)
    (map count))
  max
  0
  [-3 -2 -1 0 0 1 2])

(let [calc (comp
             (remove zero?)
             (partition-by pos?)
             (map count))]
  (transduce counts max 0 [-3 -2 -1 0 0 1 2]))

(apply max ((juxt #(count (filter pos? %))
                  #(count (filter neg? %)))
            [-3 -2 -1 0 0 1 2]))



(defn maximum-count [nums]
  (max (count (filter neg? nums))
       (count (filter pos? nums))))

(defn maximum-count [nums]
  (->> nums
       (remove zero?)
       (partition-by pos?)
       (map count)
       (apply max)))



(def counts
  (comp
    (remove zero?)
    (partition-by pos?)
    (map count)))

;; Fastest, simplest solution
;; (Could get faster, but more complex with loop/recur)
(defn maximum-count [nums]
  (transduce counts max 0 nums))

(c/bench (maximum-count [-3 -2 -1 0 0 1 2]))
