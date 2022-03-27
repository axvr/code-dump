(pprint '(hello world))


(defmacro foo ((a b c) &body x)
  (list a b c x))

(defun foo (x)
  "This is foo!"
  (destructuring-bind (a b . c) x
    (list a b c)))

(foo '(1 2 3 4 5 6 7))


(defun foo (a b &optional c d)
  (list a b c d))

(foo 1 2)
(foo 1 2 3)
(foo 1 2 3 4)

;;; If an optional parameter is not given, it will default to nil.

(defun foo (a b &optional (c 5) d)
  (list a b c d))

(foo 1 2)
(foo 1 2 3)
(foo 1 2 3 4)


(defun make-rectangle (width &optional (height width))
  (list width height))

(make-rectangle 1 1)
(make-rectangle 1 2)
(make-rectangle 2)


(defun make-rectangle (width &optional (height width height-p))
  (list width height height-p))

;; Check if an optional parameter was given, or if the default value was used.

(make-rectangle 1)
(make-rectangle 1 2)


(unintern 'make-racktangle)  ; Remove symbol from a package.


;;; &body can be used instead of &rest in defmacro (to change
;;; pretty-printing behaviour/indentation)
(defun rest-params (foo bar &rest x)
  (list (list foo bar) x))

(rest-params 1 2 3 4 5 6)


(defun key-params (foo &key x y z)
  (list foo x y z))

(key-params 4 :z 3 :y 2)
(key-params 4 :z 3 :x 1 :y 2)


(defun key-params2 (foo &key (x 9) (y 8 y-p) z)
  (list foo x (list y y-p) z))

(key-params2 4 :y 5)


;; Map the keywords params to different internal names.
(defun foo (&key ((:apple a))
                 ((:box b) 0)
                 ((:charlie c) 0 c-supplied-p))
  (list a b c c-supplied-p))

(foo :box 12 :apple 2)

(foo :not-an-allowed-key 12)  ; Errors!


;; Order of parameter use:
;;   1. required params
;;   2. optional params
;;   3. rest params
;;   4. keyword params
;; All 4 types can be used at once, if in the above order.
;;
;; Note: when using rest and keyword params, rest will contain the keyword
;; param values too.


(defun returns-early ()
  (format t "Foo bar!")
  (return-from returns-early)
  (format t "Hello world!"))

(returns-early)


(defun foo (n)
  (dotimes (i 10)
    (dotimes (j 10)
      (when (> (* i j) n)
        (return-from foo (list i j))))))

(foo 10)

;; return-from takes the name of a block to return from (in this case: foo).
;; defun automatically wraps the body in a block with the same name as the
;; function.

;; (return x) is the same as (return-from nil x) because the block name is
;; nil, it cannot be used to return from a function.


(eq (function foo) #'foo)

(funcall #'foo 10)
(apply #'foo '(10))


(defun plot (fn min max step)
  (loop for i from min to max by step do
    (loop repeat (funcall fn i) do (format t "*"))
    (format t "~%")))

(plot #'exp 0 4 1/2)
(plot #'1+ 0 7 1)

(apply #'plot (list #'exp 0 4 1/2))
(apply #'plot #'exp (list 0 4 1/2))


(funcall #'(lambda (x y) (+ x y)) 2 3)
((lambda (x y) (+ x y)) 2 3)


;; lambda is a macro which expands to (function (lambda (...) ...))
;; This means that you don't need to put #' infront of one, but you
;; probably should anyway.
