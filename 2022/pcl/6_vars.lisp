(let ((foo 1) (bar 2))
  (+ foo bar))

(let (foo bar)
  (and (null foo)
       (null bar)))

(let (foo (bar 2))
  (and (null foo)
       (= 2 bar)))


setf
setq
psetf
psetq


defvar
defparameter


;;; Somewhat equivalent to a dynamically-scoped defonce in Clojure.
(defvar *count* 0
  "Count of widgets made so far.")
;;; Although not defined in the spec, most CL implementations handle
;;; dynamic vars in a thread-safe manner.  (Like Clojure)

;;; Somewhat equivalent to a dynamically-scoped def in Clojure.
(defparameter *gap-tolerance* 0.001
  "Tolerance to be allowed in widget gaps.")


(defun increment-widget-count ()
  (incf *count*))

(increment-widget-count)


(let ((*standard-output* *some-other-stream*))
  (stuff))


(defvar *x* 10)
(defun foo () (format t "X: ~d~%" *x*))
(foo)  ; => 10

(let ((*x* 20))
  (foo))  ; => 20


(defun bar ()
  (foo)
  (let ((*x* 20))  ; Knows *x* is dynamic because it is declared "special"
    (foo))
  (foo))

(bar)

(defun foo ()
  (format t "Before assignment~18tX: ~d~%" *x*)
  (setf *x* (1+ *x*))
  (format t "After assignment~18tX: ~d~%" *x*))

(foo)

*x*


(defconstant +const+ 42)
;; Name cannot be used to mean ANYTHING else.  WIll always be 42.
;; Probably best to avoid and use defparameter instead.

(let ((+const+ 13))  ; errors
  +const+)


;; setf is the general purpose assignment operator/macro
;; (setf place value)

(setf x 12)

;; If setf detects it is operating on a variable, it will use setq

;; setf has no effect on other bindings of the variable.
(defun foo (x)
  (setf x 10)  ; Does not change the 10 of the outer scope.
  x)  ; 10
x ; 12

(foo 1)

(defun foo2 ()
  (setf y 21)  ; undefined variable warning.
  y)

(foo2) ; sets the global y

y


(setf x 2)
(setf y 3)

(setf x 2 y 3)
(psetf x 2 y 3)  ; set in parallel.

(setf x (setf y (random 10))) ; x and y are assigned to the same value.


(makunbound '*count*)  ; undefine a defvar


(setf x 10)  ; simple variable
(setf (aref a 0) 10)  ; array index
(setf (gethash 'key hash) 10)  ; hash map
(setf (field 0) 10)  ; slot named "field"

;; Use setf for all assignments.  Sometimes setq is manually used for
;; assigning variables, but that is the old style.


;; Add custom types to setf
defsetf
define-setf-expander


(setf x (+ x 1))  ; (incf x)
(setf x (- x 1))  ; (decf x)

(incf x 10)  ; (setf x (+ x 10))

;; incf and decf are "modify macros"
;; safe when "getter" cannot be executed more than once.


push
pop
pushnew


rotatef
shiftf

(rotatef a b)  ; swaps the values of a and b
(shiftf a b 10)  ; shifts values in the variables to the left, using the
                 ; last value as the new value for the last.
