(import (chicken file))

(define path "")

(define basename "index")

(if (equal? basename "index")
    (set! path (string-append "dist/" basename ".html"))
    (begin
      ;; (create-directory (string-append "dist/" basename) #t)
      (set! path (string-append "dist/" basename "/index.html"))))

(print path)

;; string-suffix-ci?

(print (find-files "." test: '(: "./index.md")))



;;;; Experiments with constructing and manipulating dictionaries

;; (define (get-value map key)
;;   (if (eq? map '())
;;     '()
;;     (if (equal? key (caar map))
;;       (cdar map)
;;       (get-value (cdr map) key))))

;; (define tree1
;;   '((one . 1)
;;     (two . 2)
;;     (three . 3)))

;; (get-value tree1 'one)
;; (get-value tree1 'two)
;; (get-value tree1 'three)
;; (get-value tree1 'four)

;; (define tree2
;;   '(("one" . 1)
;;     ("two" . 2)
;;     ("three" . 3)))

;; (get-value tree2 "one")
;; (get-value tree2 "two")
;; (get-value tree2 "three")
;; (get-value tree2 "four")
