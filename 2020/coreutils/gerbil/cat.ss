(export main)

;;;; Port of the Unix `cat' utility to Gerbil Scheme.
;;;; Public domain.  No rights reserved.

(def (print-stdin)
  (let loop ((line (read-line)))
    (unless (eof-object? line)
      (displayln line)
      (loop (read-line)))))

(def (print-file file)
  (with-input-from-file
    file
    print-stdin))

(def (main . args)
  (if (null? args)
    (print-stdin)
    (let next-file ((file (car args))
                    (left (cdr args)))
      (print-file file)
      (unless (null? left)
        (next-file (car left)
                   (cdr left))))))
