;;;; Hamming codes: report position of incorrect bit in a binary sequence.
;;;;
;;;; Clojure port of the Python code in this video:
;;;; <https://www.youtube.com/watch?v=b3NxrZOu_CE>
;;;;
;;;; Created by Alex Vear on 2021-02-28.
;;;; Public domain.  No rights reserved.


(defn hamming-code [bits]
  (->> bits
    (keep-indexed (fn [i x] (when (= x 1) i)))
    (reduce bit-xor)))


(comment

  (hamming-code [1 1 0 0 1 0 1 0 1 1 1 1 0 1 1 0])  ; -> 0

  (hamming-code [1 1 0 0
                 1 0 1 0
                 1 1 0 1
                 0 1 1 0])  ; -> 10

  )
