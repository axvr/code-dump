#!/usr/local/bin/csi -script

;;;; Port of the Unix `cat' utility to CHICKEN Scheme.
;;;; Public domain.  No rights reserved.

(import (chicken io)
        (chicken process-context))

(define (print-stdin)
  (let loop ((line (read-line)))
    (unless (eof-object? line)
      (display line)
      (newline)
      (loop (read-line)))))

(define (print-file file)
  (with-input-from-file
    file
    print-stdin))

(let ((args (command-line-arguments)))
  (if (null? args)
    (print-stdin)
    (let next-file ((file (car args))
                    (left (cdr args)))
      (print-file file)
      (unless (null? left)
        (next-file (car left)
                   (cdr left)))))
  0)
