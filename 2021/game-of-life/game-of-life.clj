(ns game-of-life
  "Conway's Game of Life simulated in GitHub's contribution graph."
  (:import [java.time LocalDate DayOfWeek]))

;; Public domain.  No rights reserved.

;; true  - alive (dark green)
;; false - dead (white)
;; nil - boundary (light green)


(def day-of-week
  {DayOfWeek/MONDAY    :mon
   DayOfWeek/TUESDAY   :tue
   DayOfWeek/WEDNESDAY :wed
   DayOfWeek/THURSDAY  :thu
   DayOfWeek/FRIDAY    :fri
   DayOfWeek/SATURDAY  :sat
   DayOfWeek/SUNDAY    :sun})


(def day-of-week
  {DayOfWeek/SUNDAY    0
   DayOfWeek/MONDAY    1
   DayOfWeek/TUESDAY   2
   DayOfWeek/WEDNESDAY 3
   DayOfWeek/THURSDAY  4
   DayOfWeek/FRIDAY    5
   DayOfWeek/SATURDAY  6})


(nth (cycle [:sun :mon :tue :wed :thu :fri :sat :sun]) 2)


(.getYear (LocalDate/now))


(day-of-week (.getDayOfWeek (LocalDate/ofYearDay 2021 1)))
(day-of-week (.getDayOfWeek (LocalDate/ofYearDay 2022 1)))


(def start-offset
  {:sun 7, :mon 6, :tue 5, :wed 4, :thu 3, :fri 2, :sat 1})

(defn end-offset [day-of-week]
  (- 8 (start-offset day-of-week)))


(defn year-stats [year]
  (let [date (LocalDate/ofYearDay year 1)]
    {:num-days     (.lengthOfYear date)
     :start-offset (start-offset (day-of-week (.getDayOfWeek date)))
     :end-offset   (end-offset (day-of-week (.. date
                                                (plusYears 1)
                                                (minusDays 1)
                                                (getDayOfWeek))))}))


(year-stats 2021)


(def grid-height 7)
(def grid-width 51)


(clojure.pprint/pprint (concat
                         (list (take 2 (repeat nil)))
                         (partition grid-height (take 357 (repeat false)))
                         (list (take 6 (repeat nil)))))


(defn new-board []
  (partition 7 (take 375 (repeat false))))


(defn number-of-live-neighbours [board x y]
  )


(defn iterate-board [board]
  )


;; Cell 5 has 8 neighbours.
[[1 2 3]
 [4 5 6]
 [7 8 9]]

;; By storing the board as a tape, we can simplify the solution of the boundary
;; wrapping problem.  (Except on the tape edges.)
[0 0 1 4 7 0 0 0 0 2 5 6 0 0 0 0 7 8 9 0 0]
;;                   ^
;; _ 8 7 6 _ _ _ _ 1 0 1 _ _ _ _ 6 7 8 _

;; TODO: configurable height and width.
;;    7 - 3 = 4 (gap between 1 and 6.)

;; Single vector would be efficient for lots of these lookups.


(defn neighbour-indicies [cell-idx width height]
  (let [max-tape-idx (dec (* width height))
        pos-indicies ((juxt (constantly 1) dec identity inc) width)
        indicies (concat
                   pos-indicies
                   (map (partial - 0) pos-indicies))]
    (->> indicies
         (map (partial + cell-idx))
         (map #(cond
                 (< % 0)
                 (+ % max-tape-idx)

                 (> % max-tape-idx)
                 (- % max-tape-idx)

                 :else %)))))

(neighbour-indicies 59 10 10)

[[ 0  1  2  3  4  5  6  7  8  9]
 [10 11 12 13 14 15 16 17 18 19]
 [20 21 22 23 24 25 26 27 28 29]
 [30 31 32 33 34 35 36 37 38 39]
 [40 41 42 43 44 45 46 47 48 49]
 [50 51 52 53 54 55 56 57 58 59]
 [60 61 62 63 64 65 66 67 68 69]
 [70 71 72 73 74 75 76 77 78 79]
 [80 81 82 83 84 85 86 87 88 89]
 [90 91 92 93 94 95 96 97 98 99]
 [10 11 12 13 14 15 16 17 18 19]]


[[0 1 2 3 4 5 6 7 8 9] [10 11 12 13 14 15 16 17 18 19] [20 21 22 23 24 25 26 27 28 29] [30 31 32 33 34 35 36 37 38 39] [40 41 42 43 44 45 46 47 48 49] [50 51 52 53 54 55 56 57 58 59] [60 61 62 63 64 65 66 67 68 69] [70 71 72 73 74 75 76 77 78 79] [80 81 82 83 84 85 86 87 88 89] [90 91 92 93 94 95 96 97 98 99] [100 101 102 103 104 105 106 107 108 109]]


;; NOTE: well that doesn't work...


40 48 49 50 58 59 60 68 69
..             ^^
70 instead.

Detect rollovers?  Biggest number - 3*width.



(defn new-board [width height pad]
  (->> (repeat pad)
       (take (* width height))
       vec))

(comment
  (new-board grid-width grid-height false)
  )
