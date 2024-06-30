(= (+ 1 2) 3)
(= (+ 1 2 3) 6)
(= (+ 2 -2) 0)
(= (+ 1 -2) -1)
(= (+ -1 -3) -4)


(defun test-+ ()
  (and
    (= (+ 1 2) 3)
    (= (+ 1 2 3) 6)
    (= (+ -1 -3) -4)))

(test-+)


(defun test-+ ()
  (format t "~:[FAIL~;pass~] ... ~a~%" (= (+ 1 2) 3) '(= (+ 1 2) 3))
  (format t "~:[FAIL~;pass~] ... ~a~%" (= (+ 1 2 3) 6) '(= (+ 1 2 3) 6))
  (format t "~:[FAIL~;pass~] ... ~a~%" (= (+ -1 -3) -4) '(= (+ -1 -3) -4)))

(test-+)


(defun report-result (result form)
  (format t "~:[FAIL~;pass~] ... ~a~%" result form))


(defun test-+ ()
  (report-result (= (+ 1 2) 3) '(= (+ 1 2) 3))
  (report-result (= (+ 1 2 3) 6) '(= (+ 1 2 3) 6))
  (report-result (= (+ -1 -3) -4) '(= (+ -1 -3) -4)))

(test-+)


(defmacro check (form)
  `(report-result ,form ',form))

(defun test-+ ()
  (check (= (+ 1 2) 3))
  (check (= (+ 1 2 3) 6))
  (check (= (+ -1 -3) -4)))

(test-+)


(defmacro check (&body forms)
  `(progn
     ,@(mapcar (lambda (f) `(report-result ,f ',f)) forms)))

(defmacro check (&body forms)
  `(progn
     ,@(loop for f in forms collect `(report-result ,f ',f))))

(defun test-+ ()
  (check
    (= (+ 1 2) 3)
    (= (+ 1 2 3) 6)
    (= (+ -1 -3) -4)))

(test-+)


(defun report-result (result form)
  (format t "~:[FAIL~;pass~] ... ~a~%" result form)
  result)

(defmacro with-gensyms (syms &body body)
  "with-gensyms from PG's On Lisp."
  `(let ,(mapcar (lambda (s) `(,s (gensym))) syms)
     ,@body))

(defmacro combine-results (&body forms)
  (with-gensyms (result)
     `(let ((,result t))
        ,@(mapcar (lambda (f) `(unless ,f (setf ,result nil))) forms)
        ,result)))

(defmacro check (&body forms)
  `(combine-results
     ,@(loop for f in forms collect `(report-result ,f ',f))))

(defmacro check (&body forms)
  `(combine-results
     ,@(mapcar (lambda (f) `(report-result ,f ',f)) forms)))

(defun test-+ ()
  (check
    (= (+ 1 2) 3)
    (= (+ 1 2 3) 6)
    (= (+ -1 -3) -4)))

(test-+)


(defun test-* ()
  (check
    (= (* 2 2) 4)
    (= (* 3 5) 15)))

(defun test-arithmetic ()
  (combine-results
    (test-+)
    (test-*)))


(defvar *test-name* nil)

(defun report-result (result form)
  (format t "~:[FAIL~;pass~] ... ~a: ~a~%" result *test-name* form)
  result)

(defun test-+ ()
  (let ((*test-name* 'test-+))
    (check
      (= (+ 1 2) 3)
      (= (+ 1 2 3) 6)
      (= (+ -1 -3) -4))))

(defun test-* ()
  (let ((*test-name* 'test-*))
    (check
      (= (* 2 2) 4)
      (= (* 3 5) 15))))

(defun test-arithmetic ()
  (combine-results
    (test-+)
    (test-*)))

(test-arithmetic)


(defmacro deftest (name params &body body)
  `(defun ,name ,params
     (let ((*test-name* ',name))
       ,@body)))

(deftest test-+ ()
  (check
    (= (+ 1 2) 3)
    (= (+ 1 2 3) 6)
    (= (+ -1 -3) -4)))

(deftest test-* ()
  (check
    (= (* 2 2) 4)
    (= (* 3 5) 15)))


(defmacro deftest (name params &body body)
  `(defun ,name ,params
     (let ((*test-name* (append *test-name* (list ',name))))
       ,@body)))


(deftest test-arithmetic ()
  (combine-results
    (test-+)
    (test-*)))

(test-arithmetic)
