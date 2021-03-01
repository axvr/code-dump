;;;; Turing Machine simulation in Clojure.
;;;;
;;;; Created by Alex Vear on 2021-02-28.
;;;; Public domain.  No rights reserved.


(ns turing-machine)


(defn- expand-tape [{:keys [head tape blank] :as ar}]
  (conj
    ar
    (cond
      (neg? head) (let [diff (- 0 head)]
                    {:tape (vec (concat (repeat diff blank) tape))
                     :head 0})
      (>= head (count tape)) (let [diff (- (inc head) (count tape))]
                               {:tape (apply conj tape (repeat diff blank))
                                :head head}))))


(defn- next-rule [{:keys [rules state tape head]}]
  (get (rules state) (tape head)))


(defn- apply-rule [ar rule]
  (let [head  (:head ar)
        move  (:move rule)
        write (:write rule)
        tape  (:tape ar)
        state (:state rule)]
    (conj ar
          {:head (+ head move)
           :tape (assoc tape head write)
           :state state})))


(defn run [ar]
  (let [<-ar-> (expand-tape ar)]
    (if-let [rule (next-rule <-ar->)]
      (recur (apply-rule <-ar-> rule))
      ar)))


(comment

  (def erase-left-to-right-rules
    {:a {nil {:write nil, :state :halt, :move 0}
         0   {:write nil, :state :a,    :move 1}
         1   {:write nil, :state :a,    :move 1}}})

  (def binary-increment-rules
    {:a {nil {:write nil, :state :b,    :move   1}
         0   {:write 0,   :state :a,    :move  -1}
         1   {:write 1,   :state :a,    :move  -1}}
     :b {nil {:write 1,   :state :c,    :move  -1}
         0   {:write 1,   :state :c,    :move   1}
         1   {:write 0,   :state :b,    :move   1}}
     :c {nil {:write nil, :state :halt, :move   1}
         0   {:write 0,   :state :c,    :move  -1}
         1   {:write 1,   :state :c,    :move  -1}}})

  (:tape
    (turing-machine/run
      {:rules binary-increment-rules
       :state :a
       :tape [1 1 0 1]
       :head 0
       :blank nil}))

  )
