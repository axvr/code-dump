;;;; Turing Machine simulation in Clojure.
;;;;
;;;; Created by Alex Vear on 2021-02-28.
;;;; Public domain.  No rights reserved.


;;; TODO: clean up this code.


(ns turing-machine)


(defn- lookup-rule [rules state value]
  (get (rules state) value))


(defn- expand-tape
  "Simulate an infinite tape."
  [next-index tape pad-with]
  (cond
    (neg? next-index) (let [diff (- 0 next-index)]
                        {:new-tape  (vec (concat (repeat diff pad-with) tape))
                         :new-index 0})
    (>= next-index (count tape)) (let [diff (- (inc next-index) (count tape))]
                                   {:new-tape  (apply conj tape (repeat diff pad-with))
                                    :new-index next-index})
    :else {:new-tape  tape
           :new-index next-index}))


(defn- apply-rule [rule tape index pad-with]
  (let [{:keys [new-tape new-index]}
        (expand-tape
          (+ index (:move rule))
          (assoc tape index (:write rule))
          pad-with)]
    {:new-state (:state rule)
     :new-index new-index
     :new-tape  new-tape}))


(defn simulate
  "Simulate a Turing Machine."
  [tape state rules index pad-with]
  (if-let [rule (lookup-rule rules state (tape index))]
    (let [{:keys [new-state new-index new-tape]}
          (apply-rule rule tape index pad-with)]
      (recur new-tape new-state rules new-index pad-with))
    {:tape tape, :index index, :state state}))


(comment

  (def binary-increment-rules
    {:a {nil {:write nil
              :state :b
              :move  1}
         0 {:write 0
            :state :a
            :move  -1}
         1 {:write 1
            :state :a
            :move  -1}}
     :b {nil {:write 1
              :state :c
              :move  -1}
         0 {:write 1
            :state :c
            :move  1}
         1 {:write 0
            :state :b
            :move  1}}
     :c {nil {:write nil
              :state :halt
              :move  1}
         0 {:write 0
            :state :c
            :move  -1}
         1 {:write 1
            :state :c
            :move  -1}}
     :halt nil})

  (turing-machine/simulate [1 1 0 1] :a binary-increment-rules 0 nil)

  )
