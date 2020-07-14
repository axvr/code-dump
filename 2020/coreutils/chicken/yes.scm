#!/usr/local/bin/csi -script

;;;; Port of the Unix `yes' utility to CHICKEN Scheme.
;;;; Public domain.  No rights reserved.

(import (chicken process-context))

(define (yes m)
  (display m)
  (newline)
  (yes m))

(define (string-join strings delimiter)
  (let loop ((init (car strings))
             (rest (cdr strings)))
    (if (null? rest)
      init
      (loop (string-append init " " (car rest))
            (cdr rest)))))

(let ((args (command-line-arguments)))
  (yes (if (null? args)
         "y"
         (string-join args " ")))
  0)
