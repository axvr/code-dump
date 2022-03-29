(defmacro if-let ((name expr) &body body)
  `(let ((,name ,expr))
     (if ,name
       ,@body)))

(if-let (x (car '(1 2 3)))
  (format t "True:  ~A~%" x)
  (format t "False: ~A~%" x))


(defun primep (num)
  (when (> num 1)
    (loop for fac
          from 2 to (isqrt num)
          never (zerop (mod num fac)))))

(defun next-prime (num)
  (loop for n
        from num
        when (primep n)
        return n))

(next-prime 4)


(do-primes (p 0 19)
  (format t "~d " p))


(defmacro do-primes ((var from to) &body body)
  `(do ((,var (next-prime ,from) (next-prime (1+ ,var))))
       ((> ,var ,to))
     ,@body))


(defmacro do-primes ((var from to) &body body)
  (let ((end-name (gensym)))
    `(do ((,var (next-prime ,from) (next-prime (1+ ,var)))
          (,end-name ,to))
         ((> ,var ,end-name))
       ,@body)))

;; Avoid macro issues by trying to:
;; 1. include subforms in the expansion in the same order they are given
;;    when the macro is called.
;; 2. make sure given subforms are only evaluated once.  (Make sure
;;    looping won't evaluate subform more than once.)
;; 3. Use gensym.


(defmacro do-primes ((var from to) &body body)
  (with-gensyms (end-name)
    `(do ((,var (next-prime ,from) (next-prime (1+ ,var)))
          (,end-name ,to))
         ((> ,var ,end-name))
       ,@body)))


(defmacro with-gensyms (syms &body body)
  "with-gensyms from Seibel's Practical Common Lisp."
  `(let ,(loop for n in syms collect `(,n (gensym)))
     ,@body))

(defmacro with-gensyms (syms &body body)
  "with-gensyms from PG's On Lisp."
  `(let ,(mapcar (lambda (s) `(,s (gensym))) syms)
     ,@body))

(macroexpand-1
  '(do-primes (p 0 19)
    (format t "~d " p)))


(defmacro once-only ((&rest names) &body body)
  "Used to generate code that evaluates certain macro arguments only
  once and in a particular order."
  (let ((gensyms (loop for n in names collect (gensym))))
    `(let (,@(loop for g in gensyms collect `(,g (gensym))))
       `(let (,,@(loop for g in gensyms for n in names collect ``(,,g ,,n)))
          ,(let (,@(loop for n in names for g in gensyms collect `(,n ,g)))
             ,@body)))))

(defmacro do-primes ((var from to) &body body)
  (once-only (from to)
    `(do ((,var (next-prime ,from) (next-prime (1+ ,var))))
         ((> ,var ,to))
       ,@body)))

(do-primes (p 0 19)
  (format t "~d " p))
