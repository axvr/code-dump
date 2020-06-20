#!/usr/local/bin/csi -script

;;;; Port of the Unix `cat' utility to CHICKEN Scheme.
;;;; Public domain.  No rights reserved.

(import (chicken process-context))

(define (string-join strings delimiter)
  (let loop ((init (car strings))
             (rest (cdr strings)))
    (if (null? rest)
      init
      (loop (string-append init " " (car rest))
            (cdr rest)))))

(let ((args (command-line-arguments)))
  (unless (null? args)
    (display (string-join args " ")))
  (newline)
  0)
