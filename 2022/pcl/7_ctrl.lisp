if
when
unless
cond


progn


not
and
or


do
dolist
dotimes
loop


tagbody
go


;; Equivalent to Clojure's doseq macro.
(dolist (x '(1 2 3))
  (print x))

(dolist (x '(1 2 3))
  (print x)
  (when (evenp x)
    (return)))  ; Use return to break from dolist.


(dotimes (i 4)
  (print i))

(dotimes (i 4)
  (print i)
  (when (evenp i)
    (return)))  ; Return will also break from dotimes.


(dotimes (x 20)
  (dotimes (y 20)
    (format t "~3d " (* (1+ x) (1+ y))))
  (format t "~%"))


;; (do (variable-defs)
;;     (end-test-form result-forms)
;;   statements)

;; variable-def
;; (var init-form step-form) or just var
;; if no init-form is given, will default var to nil

;; When end-test-form evals to true, result-forms are evaled, the value
;; of the final result form is the return value.

(do ((n 0 (1+ n))
     (cur 0 next)
     (next 1 (+ cur next)))
    ((= 10 n) cur))

;; The n next and cur values used in the step forms are the old values
;; (from the previous iteration).

(do ((i 0 (1+ i)))  ; same as dotimes
    ((>= i 4))
  (print i))

(dotimes (i 4)
  (print i))

(dotimes (i 10)
  (print i)
  (sleep 2))

(do ()
    ((> (get-universal-time) *some-future-date*))
  (format t "Waiting~%")
  (sleep 60))

(defmacro my-dotimes ((var count &optional result) &body body)
  `(do ((,var 0 (1+ ,var)))
       ((> ,var (1- ,count)) ,result)
     ,@body))

(my-dotimes (i 5 'done)
  (print i))

(dotimes (i 5)
  (print i))


(defun listify (obj)
  "If obj is not a list, wrap it in a list.  Taken from PG's On Lisp."
  (if (listp obj) obj (list obj)))

(defun comp (&rest fns)
  "Compose functions together.  Taken from PG's On Lisp."
  (if fns
    (let ((fn1 (car (last fns)))
          (fns (butlast fns)))
      (lambda (&rest params)
        (reduce #'funcall fns
                :from-end t
                :initial-value (apply fn1 params))))
    #'identity))

(defun alist-vals (alist)
  "Get all values from an alist."
  (mapcar (comp #'cadr #'listify) alist))

(defun alist-keys (alist)
  "Get all keys from an alist."
  (mapcar (comp #'car #'listify) alist))

(alist-keys '((a 1) (b 2) c))
(alist-vals '((a 1) (b 2) c))

(defmacro letn (name binds &body body)
  "Common Lisp implementation of named let from Scheme."
  `(labels ((,name ,(alist-keys binds)
                     ,@body))
     (,name ,@(alist-vals binds))))

(letn foo ((x 2)
           (y 42))
  (print x)
  (print y)
  (when (< x 10)
    (foo (1+ x) (1+ y))))

(defun good-reverse (lst)
  "PG's good-reverse function from On Lisp."
  (labels ((rev (lst acc)
             (if (null lst)
               acc
               (rev (cdr lst)
                    (cons (car lst) acc)))))
    (rev lst nil)))

(good-reverse '(1 2 3 4 5 6 7 8 9))

(defun good-reverse2 (lst)
  "My version of PG's good-reverse function using my \"named let\"
  macro."
  (letn rev ((lst lst) acc)
    (if lst
      (rev (cdr lst)
           (cons (car lst) acc))
      acc)))

(good-reverse2 '(1 2 3 4 5 6 7 8 9))


(equal
  (macroexpand-1
    '(letn rev ((lst lst) acc)
           (if lst
             (rev (cdr lst)
                  (cons (car lst) acc))
             acc)))
  '(labels ((rev (lst acc)
              (if lst
                (rev (cdr lst)
                     (cons (car lst) acc))
                acc)))
     (rev lst nil)))


loop

;; Infinite loop.
(loop
  (princ "Hello world!"))

(loop
  (when (> (get-universal-time) *some-future-date*)
    (return))  ; Use return to break from loop
  (format t "Waiting~%")
  (sleep 60))


(do (nums (i 1 (1+ i)))
    ((> i 10) (nreverse nums))
  (push i nums))

(loop for i from 1 to 10 collecting i)
(loop for i from 1 to 10 collect i)

(loop for x from 1 to 10 summing (expt x 2))
(loop for x from 1 to 10 sum (expt x 2))

(loop for x across "the quick brown fox jumps over the lazy dog"
      counting (find x "aeiou"))
(loop for x across "the quick brown fox jumps over the lazy dog"
      count (find x "aeiou"))

(loop for i below 10
      and a = 0 then b
      and b = 1 then (+ b a)
      finally (return a))

;; across and below collecting counting finally for summing then to
