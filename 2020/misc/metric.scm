;;;; Public domain.  No rights reserved.

;;;; Experimenting with how taking a successive-mean of a set of values behaves
;;;; and if it could be used as a very simple machine learning algorithm.

(define scores (list 2 2 4 3 5 5))

(define (successive-mean values)
  (let loop ((vs (reverse values)))
    (if (= (length vs) 1)
      (car vs)
      (mean (list (loop (cdr vs))
                  (car vs))))))

(define (mean values)
  (/ (apply + values)
     (length values)))

(define (report lst)
  (display "Metric: ")
  (print (successive-mean lst))
  (display "Average: ")
  (print (mean lst)))
