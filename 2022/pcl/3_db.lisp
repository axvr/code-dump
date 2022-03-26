(list 1 2 3)

(list :a 1 :b 2 :c 3)

(getf (list :a 1 :b 2 :c 3) :b)


(defun make-cd (album artist rating ripped)
  (list :album  album
        :artist artist
        :rating rating
        :ripped ripped))

(make-cd "Binaural" "Pearl Jam" 10 t)


(defvar *db* nil)

(defun add-record (cd)
  (push cd *db*))


(add-record
  (make-cd "Binaural" "Pearl Jam" 10 t))
(add-record
  (make-cd "Machina / The Machines of God" "The Smashing Pumpkins" 10 t))
(add-record
  (make-cd "The Colour and the Shape" "Foo Fighters" 10 t))
(add-record
  (make-cd "There is Nothing Left to Lose" "Foo Fighters" 8 t))  ; R.I.P. Taylor Hawkings.
(add-record
  (make-cd "Bossanova" "Pixies" 9 t))

*db*


(defun dump-db ()
  (dolist (cd *db*)
    (format t "~{~a:~10t~a~%~}~%" cd)))


(dump-db)


;; t in format is short-hand for *standard-output*
;; if destination is nil, format will return the string.
;; format directives are case insensitive.


(format t "~a" "Foo bar")  ; aesthetic directive: output in human-readable form
(format t "~a" :title)     ; equivalent to (princ)
(format t "~A" "Foo bar")  ; directives are case insensitive.


(format t "~~")  ; ~~ => ~


(format t "~s" "Foo bar")  ; (prin1 "Foo bar")
(prin1 "Foo bar")  ; prints as readable representation


(format t "~d" 1.21)  ; format as decimal number


(format t "~10t~a" "Hello")  ; tabulation directive: emit spaces to start next
                             ; directive at specified column
(format t "~a:~10t~a" :artist "Pearl Jam")
(format t "~a:~10t~a~30t~a" :artist "Pearl Jam" :?)


(format t "~{~a:~10t~a~}" '(:artist "Pearl Jam"))  ; ~{ ~} treat value as a list
(format t "~{~a:~10t~a~}" '(:artist "Pearl Jam" :album "No Code"))
(format t "~{~a:~10t~a~%~}" '(:artist "Pearl Jam" :album "No Code"))  ; ~% newline (same as (terpri))


(format t "~&")  ; ~& starts a fresh line.  (fresh-line)


(defun dump-db ()
  (format t "~{~{~a:~10t~a~%~}~%~}" *db*))


(defun prompt-read (prompt)
  (format *query-io* "~a: " prompt)
  (force-output *query-io*)  ; same as Clojure's (flush) function.
  (read-line *query-io*))


(prompt-read "Artist")


(defun prompt-for-cd ()
  (make-cd
    (prompt-read "Album")
    (prompt-read "Artist")
    (or (parse-integer (prompt-read "Rating") :junk-allowed t) 0)
    (equal "y" (prompt-read "Ripped [y/n]"))))

(defun prompt-for-cd ()
  (make-cd
    (prompt-read "Album")
    (prompt-read "Artist")
    (or (parse-integer (prompt-read "Rating") :junk-allowed t) 0)
    (y-or-n-p "Ripped [y/n]: ")))

(prompt-for-cd)


(defun add-cds ()
  (loop (add-record (prompt-for-cd))
    (when (not (y-or-n-p "Another?"))
      (return))))


(defun save-db (filename)
  (with-open-file (out filename
                   :direction :output
                   :if-exists :supersede)
    (with-standard-io-syntax
      (print *db* out))))

(save-db "cds.db")


(macroexpand-1
  '(with-open-file (out filename
                    :direction :output
                    :if-exists :supersede)
     (with-standard-io-syntax
       (print *db* out))))

(macroexpand-1
  '(with-standard-io-syntax
     (print *db* out)))


(defun load-db (filename)
  (with-open-file (in filename :direction :input)
    (with-standard-io-syntax
      (setf *db* (read in)))))

(defun load-db (filename)
  (with-open-file (in filename)  ; Defaults to :input
    (with-standard-io-syntax
      (setf *db* (read in)))))


;; Multi-line comment block  can also use #|| and ||#
#|

Difference between print, princ and prin1:

- princ prints string in human-readable format.
- prin1 prints string in Lisp-readable format.
- print same as prin1 but with extra newline in front.

|#


;; #+ is the same as Clojure's discard macro, but isn't working for me ???
#+(format t "Hello world!")


(format t "Hello ~A ~A!" "Foo")  ; errors: opens debugger


(remove-if-not #'evenp '(1 2 3 4 5 6 7 8 9 10))
(remove-if #'evenp '(1 2 3 4 5 6 7 8 9 10))


(defun select (field value)
  (remove-if-not
    #'(lambda (cd) (equal value (getf cd field)))
    *db*))


(select :artist "Pearl Jam")
(select :artist "The Smashing Pumpkins")
(select :title "Siamese Dream")


(remove-if-not
  #'(lambda (x) (= 0 (mod x 2)))
  (list 1 2 3 4 5 6 7 8 9 10))


(defun select-by-artist (artist)
  (remove-if-not
    #'(lambda (cd) (equal artist (getf cd :artist)))
    *db*))


(defun select (selector-fn)
  (remove-if-not selector-fn *db*))

(defun artist-selector (artist)
  #'(lambda (cd) (equal artist (getf cd :artist))))

(artist-selector "Pearl Jam")

(select (artist-selector "Pearl Jam"))


(defun where (&key title artist rating ripped)
  #'(lambda (cd)
      (and
        (if title  (equal title  (getf cd :title))  t)
        (if artist (equal artist (getf cd :artist)) t)
        (if rating (equal rating (getf cd :rating)) t)
        (if ripped (equal ripped (getf cd :ripped)) t))))

(select (where :artist "Foo Fighters"))


(defun update (selector-fn &key title artist rating (ripped nil ripped-p))
  (setf *db*
        (mapcar
          #'(lambda (row)
              (when (funcall selector-fn row)
                (if title    (setf (getf row :title)  title))
                (if artist   (setf (getf row :artist) artist))
                (if rating   (setf (getf row :rating) rating))
                (if ripped-p (setf (getf row :ripped) ripped)))
              row)
          *db*)))


(defun delete (selector-fn)
  (setf *db* (remove-if selector-fn *db*)))


(defun where (&rest conds)
  ;; TODO: mapcar over plist.
  #'(lambda (cd)
      ()))

(where :artist "Foo Fighters")


(defun partition (source n &key ignore-extras)
  "Altered version of Paul Graham's \"group\" function from \"On Lisp\"."
  (when (zerop n) (error "zero length"))
  (labels ((rec (source acc)
             (let ((rest (nthcdr n source)))
               (if (consp rest)
                 (rec rest (cons (subseq source 0 n) acc))
                 (nreverse (if ignore-extras
                             acc
                             (cons source acc)))))))
    (when source
      (rec source nil))))

(partition (list :a 1 :b 2 :c 3) 2)
(partition (list :a 1 :b 2 :c) 2)
(partition (list :a 1 :b 2 :c) 2 :ignore-extras t)


;; The examples in Practical Common Lisp use macros to remove duplicate code
;; from "where", but it is possible to achieve the same result using functions.
(defun where (&rest conds)
  #'(lambda (cd)
      (every #'(lambda (r)
                 (equal (cadr r) (getf cd (car r))))
             (partition conds 2 :ignore-extras t))))

(select (where :artist "Pearl Jam"))


(defmacro backwards (expr)
  (reverse expr))

(backwards (2 1 +))


(defun make-comparison-expr (field value)
  (list 'equal (list 'getf 'cd field) value))

(defun make-comparison-expr (field value)
  `(equal (getf cd ,field) ,value))

(make-comparison-expr :artist "Mogwai")

(defun make-comparisons-list (fields)
  (loop while fields
    collecting (make-comparison-expr (pop fields) (pop fields))))

(defmacro where (&rest clauses)
  `#'(lambda (cd)
       (and ,@(make-comparisons-list clauses))))

(select (where :artist "Pearl Jam"))


;; Footnote from chapter 3: format can convert numbers into the written form of
;; the spoken English number.
(format nil "~r" 1606938044258990275541962092)
