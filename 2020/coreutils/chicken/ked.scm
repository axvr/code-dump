#!/usr/local/bin/csi -script

;;;; Ed-like editor based on KRUSADER.
;;;; Public domain.  No rights reserved.

(import (chicken io)
        (chicken process-context))

(define (input-prompt prompt)
  (display prompt)
  (read-line))

(let input ((i ""))
  (cond
    ((eq? i #!eof) (newline) 0)
    ((equal? i "q") 0)
    (else
      (input (input-prompt "KED> ")))))
