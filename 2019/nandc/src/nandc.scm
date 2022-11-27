;;; Read from data file

(import (chicken io))

(define (open-nc-file path)
  (read-list (open-input-file path)))

;; (let ((nc (open-nc-file "/home/axvr/Documents/Projects/nandc/src2/ex3.nc")))
;;   (print nc)))

(print (open-nc-file "/home/axvr/Documents/Projects/nandc/src2/ex3.nc"))

;; TODO: write data back to file

;; TODO: use custom environment to eval nc code
