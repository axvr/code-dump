;;;; Following along to "Scheme Lisp: Feel the Cool" by Andy Balaam
;;;; 2019-12-13
;;;; Public domain.

(define (abs x)
  (if (< x 0)
    (- x)
    x))

(print (abs -3))



(import (chicken sort))

(sort (list 9 3 5) <)

(length (list 1 2 3))

(sorted? (list 1 2 3) >)


(define x (list 1 2 3))

(car x)
(cdr x)

(cons "a" "b")

(cons (cons 5 6) 7)

(define p (cons 1 2))
(car p)
(cdr p)


(define null '())

(cons 2 '())
(cons 2 null)

(cons 1 (cons 2 null))




(define (sum vs)
  (if (= 1 (length vs))
    (car vs)
    (+ (car vs)
       (sum (cdr vs)))))

(sum '(1 2 3))

(cdr '())



(define (double x) (* 2 x))

(define (apply-twice fn value)
  (fn (fn value)))

(apply-twice double 5)

(map double (list 3 4 5))

(map (lambda (x) (* x x)) (list 3 4 5))



(define s (list '+ 4 7))
(print s)
(eval s)



(define (switchop a) (cons '* (cdr a)))
(define s2 (switchop s))

(cons 2 '(3 4))

(print '(* 3 6))


(list '* 3 6)
(list (quote *) 3 6)

'(* 3 6)
(quote (* 3 6))


(import (chicken sort))

(sort (list 5 4 3 2 1) <)

(sort (list "abc" "a" "ab") string<?)


(define (counter)
  (define c 0)
  (lambda ()
    (set! c (+ c 1))
    c))


(define (times-n n) (lambda (x) (* n x)))
(define b (times-n 7))
(b 3)



(define (mcons a b)
  (lambda (cmd)
    (if (equal? cmd "car")
      a
      b)))

(define (mcar pair) (pair "car"))
(define (mcdr pair) (pair "cdr"))

(define foo (mcons 1 2))
(mcar foo)
(mcdr foo)

(define (new/cons a b)
  (lambda (cmd)
    (if (eq? cmd 'car)
      a
      b)))

(define (new/car pair) (pair 'car))
(define (new/cdr pair) (pair 'cdr))

(define bar (new/cons 1 2))
(new/car bar)
(new/cdr bar)



(define (minc x) (lambda () x))

(define (mdec x) (x))

(define n0 (lambda () '()))
(define n1 (minc n0))
(define n2 (minc n1))
(define n3 (minc n2))
(define n4 (minc n3))
(define n5 (minc n4))

(define (mzero? x) (null? (x)))

(define (mequal? x y)
  (cond
    ((mzero? x) (mzero? y))
    ((mzero? y) (mzero? x))
    (else (mequal? (mdec x) (mdec y)))))

(define (m+ x y)
  (if (mzero? y)
    x
    (m+ (minc x) (mdec y))))
