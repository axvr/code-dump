(define (ratio h w)
  (round-dp (/ h w) 2))

(define (round-dp x dp)
  (let ((scale (expt 10 dp)))
    (/ (round (* x scale)) scale)))

; (define tile '(1 2))  ; width x height ratio.

;;; Actual length.
;;; 41.6 x 59.5  -> 1 : 0.7 | 10 : 7
(define paper-height 41.6)
(define paper-width 59.5)

;;; Smaller paper size.
;;; 40   x 50    -> 1 : 0.8 | 10 : 8
(define clean-height 40)
(define clean-width 50)

(define (sq-len edge-length num-sqrs)
  (floor (/ edge-length num-sqrs)))


(sq-len paper-height 7)
(sq-len paper-width 12)

(sq-len clean-height 8)
(sq-len clean-width 10)


(define (squares height width)
  (let* ((rat (ratio height width))
         (tile-height-mult 2)
         ())
    ()))

(squares paper-height paper-width)
