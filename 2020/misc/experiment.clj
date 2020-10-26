;;;; Public domain.  No rights reserved.

;;;; Experimenting with different ways of writing the same Clojure expression.

(filter (fn [x] (< x 50))
        (map (fn [x] (* x x))
             (range 1 11)))

(filter #(< % 50)
        (map #(* % %)
             (range 1 11)))

(->> (range 1 11)
     (map #(* % %))
     (filter #(< % 50)))
