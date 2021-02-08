(ns cube.main)

(defn new-cube []
  {:back
   [[:b :b :b]
    [:b :b :b]
    [:b :b :b]]

   :top
   [[:w :w :w]
    [:w :w :w]
    [:w :w :w]]

   :left
   [[:o :o :o]
    [:o :o :o]
    [:o :o :o]]

   :front
   [[:g :g :g]
    [:g :g :g]
    [:g :g :g]]

   :right
   [[:r :r :r]
    [:r :r :r]
    [:r :r :r]]

   :bottom
   [[:y :y :y]
    [:y :y :y]
    [:y :y :y]]})


(defn fmt-row [[a b c]]
  (format "%s %s %s"
          (name a)
          (name b)
          (name c)))


(defn fmt-face
  ([left front right]
   (str
     "\n+-------+-------+-------+"
     (format "\n| %s | %s | %s |"
             (fmt-row (left 0))
             (fmt-row (front 0))
             (fmt-row (right 0)))
     (format "\n| %s | %s | %s |"
             (fmt-row (left 1))
             (fmt-row (front 1))
             (fmt-row (right 1)))
     (format "\n| %s | %s | %s |"
             (fmt-row (left 2))
             (fmt-row (front 2))
             (fmt-row (right 2)))
     "\n+-------+-------+-------+"))

  ([[row-1 row-2 row-3]]
   (str
     (format "\n        | %s |"
             (fmt-row row-1))
     (format "\n        | %s |"
             (fmt-row row-2))
     (format "\n        | %s |"
             (fmt-row row-3)))))


(defn print-cube [{:keys [back top left front right bottom]}]
  (println
    "        +-------+"
    (fmt-face back)
    "\n        +-------+"
    (fmt-face top)
    (fmt-face left front right)
    (fmt-face bottom)
    "\n        +-------+"))


(defn rotate-face
  "Rotate front of cube clockwise."
  [{:keys [back top left front right bottom]}]
  {:back back
   :top
   (let [[r1 r2 _] top
         [[_ _ a3]
          [_ _ b3]
          [_ _ c3]] left]
     [r1 r2 [a3 b3 c3]])
   :left
   (let [[[a1 a2 _]
          [b1 b2 _]
          [c1 c2 _]] left
         [[d1 d2 d3] _ _] bottom]
     [[a1 a2 d1]
      [b1 b2 d2]
      [c1 c2 d3]])
   :front
   (let [[[a1 a2 a3]
          [b1 b2 b3]
          [c1 c2 c3]] front]
     [[c1 b1 a1]
      [c2 b2 a2]
      [c3 b3 a3]])
   :right
   (let [[[_ a2 a3]
          [_ b2 b3]
          [_ c2 c3]] right
         [_ _ [d1 d2 d3]] top]
     [[d1 a2 a3]
      [d2 b2 b3]
      [d3 c2 c3]])
   :bottom
   (let [[_ r2 r3] bottom
         [[a1 _ _]
          [b1 _ _]
          [c1 _ _]] right]
     [[c1 b1 a1] r2 r3])})



;; (defn invert-face
;;   "direction is :horizontal or :vertical"
;;   [direction])


;; (rotate-cube cube :front) -> cube
;; (defn rotate-cube
;;   "'face' specifies which face should be the new 'front'"
;;   [{:keys [back top left front right bottom] :as cube}
;;    face]
;;   (cond
;;     (= face :front) cube
;;      :else cube))



;; (defn -main [& args]
;;   ())


(comment


  ;;         +-------+
  ;;         | o o o |
  ;;         | o o o |
  ;;         | o o o |
  ;;         +-------+
  ;;         | y y y |
  ;;         | y y y |
  ;;         | y y y |
  ;; +-------+-------+-------+
  ;; | g g g | b b b | w w w |
  ;; | g g g | b b b | w w w |
  ;; | g g g | b b b | w w w |
  ;; +-------+-------+-------+
  ;;         | r r r |
  ;;         | r r r |
  ;;         | r r r |
  ;;         +-------+


  ;;         +-------+
  ;;         | o o o |
  ;;         | o o o |
  ;;         | o o o |
  ;;         +-------+
  ;;         | y y y |
  ;;         | y y y |
  ;;      -> | g g g | ->
  ;; +-------+-------+-------+
  ;; | g g r | b b b | y w w |
  ;; | g g r | b b b | y w w |
  ;; | g g r | b b b | y w w |
  ;; +-------+-------+-------+
  ;;      <- | w w w | <-
  ;;         | r r r |
  ;;         | r r r |
  ;;         +-------+


  ;;  +-------+      +-------+
  ;;  | o o o |      | o o o |
  ;;  | o o o |      | o o o |
  ;;  | o o o |      | o o o |
  ;;  +-------+      +-------+
  ;;  | y y y |      | y y y |
  ;;  | y y y |      | y y y |
  ;;  | y y y |      | g g g |
  ;;  +-------+      +-------+
  ;;  | g g g |      | g g r |
  ;;  | g g g |      | g g r |
  ;;  | g g g |      | g g r |
  ;;  +-------+      +-------+
  ;;  | b b b |      | b b b | -+
  ;;  | b b b |      | b b b |  |
  ;;  | b b b |      | b b b | <+
  ;;  +-------+  ->  +-------+
  ;;  | w w w |      | y w w |
  ;;  | w w w |      | y w w |
  ;;  | w w w |      | y w w |
  ;;  +-------+      +-------+
  ;;  | r r r |      | w w w |
  ;;  | r r r |      | r r r |
  ;;  | r r r |      | r r r |
  ;;  +-------+      +-------+


  ;; scramble


  (-> (new-cube)
      rotate-face
      print-cube)


  )
