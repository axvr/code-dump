(export main)

;;;; Port of the Unix `echo' utility to Gerbil Scheme.
;;;; Public domain.  No rights reserved.

(def (main . args)
  (unless (null? args)
    (display (string-join args " ")))
  (newline))
