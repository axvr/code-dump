(def (read-file fname)
  (let* ((in (open-input-file fname))
         (forms
           (let collect ((lst '())
                         (form (read in)))
             (if (eof-object? form)
               lst
               (collect (cons form lst)
                        (read in))))))
    (close-input-port in)
    (reverse forms)))

(def (replace-symbols alist form)
  ;; TODO: expand alist values?
  (eval `(let ,alist
           ,form)))

(replace-symbols '((foo "Hello"))
                 (car (read-file "test.sxml")))

(eval
  (replace-symbols '((foo ''(1 2 3))
                     (fn '(lambda (x) (+ x x))))
                   (car (read-file "test.sxml"))))

(cadr (read-file "test.sxml"))

(eval `(let ((foo "Hello"))
         ,(car (read-file "test.sxml"))))

(let ((d (car (read-file "test.sxml")))
      (foo "hello"))
  d)

;; Wrap data in function which accepts an alist of replacements.  Function returns new data.
