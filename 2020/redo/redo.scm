(declare (uses redo-rc))

(import (chicken file)
        (chicken process)
        (chicken process-context)
        redo-rc)

;; TODO: command line options: help, version, etc.
;;   - Construct list of key/value pairs (if not exiting immediately).

(create-directory redo-dir)

;; TODO: Wipe/create and populate `redo-rc-file` with key/value list.
;; (with-output-to-file (string-append redo-dir "/rc")
;;                      (list->string (command-line-arguments)))

(define (empty? list) (eqv? list '()))

(let ((targets (if (empty? (command-line-arguments))
                 (list "all")
                 (command-line-arguments))))

  (process-wait (process-run "./redo-ifchange" ; TODO: remove the `./`
                             targets)))

;; TODO: exit with same code as returned from (process-wait)

;;; ----------------------------------------

;; TODO: error messages and build progress
;; TODO: parallel builds.
