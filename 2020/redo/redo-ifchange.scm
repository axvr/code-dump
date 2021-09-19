(declare (uses redo-rc))

(import (chicken process-context)
        (chicken file)
        (chicken format)
        redo-rc)

;; Always start in root directory? Allow relative paths.

;; TODO: use key/value list from rc file.
;; (print (read-rc))

(define (empty? list)
  (eqv? list '()))

(define (full-path file)
  (string-append (current-directory) "/" file))

(cond
  ((empty? (command-line-arguments))
   ;; Nothing to build, exit successfully.
   (exit 0))

  ((= (length (command-line-arguments)) 1)
   ;; Single target: build it.
   (let ((target (car (command-line-arguments))))

     ;; TODO: expand target paths

     ;; TODO: locate correct do file.

     ;; Check targets are valid.
     (when (not (and (file-exists? (full-path target)) (file-executable? (full-path target))))
       (printf "redo: ~A: no .do file~%" (full-path target))
       (exit 1))

     ;; Execute do file.
     ;; - Pipe STDOUT to target.
     ;; - Give arguments.
     ;; - Check contents of $3.
     ;; - Detect error.

     ;; Write to temporary file, then move it.
     ;; Remember to check if changed.
     ))

  ((> (length (command-line-arguments)) 1)
   ;; Multiple targets: manage builds.
   (print (command-line-arguments))

   ;; If more than one target, process-run (self) on each target.

   ;; For each target run redo-ifchange
   ;; Monitor and respond to errors

   ;; redo-ifchange should return the built files/statuses to the above redo-ifchange?
   ))
