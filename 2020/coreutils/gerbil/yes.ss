(export main)

;;;; Port of the Unix `yes' utility to Gerbil Scheme.
;;;; Public domain.  No rights reserved.

(def (yes m)
  (displayln m)
  (yes m))

(def (main . args)
  (yes (if (null? args)
         "y"
         (string-join args " "))))
