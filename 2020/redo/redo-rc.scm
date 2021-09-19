(declare (unit redo-rc))

(module redo-rc (redo-dir read-rc)
  (import scheme
          (chicken io)
          (chicken process-context))

  ;; TODO: how does changing the directory effect this?
  (define redo-dir (string-append (current-directory) "/.redo"))

  (define rc-path (string-append redo-dir "/rc"))

  (define (read-rc)
    (read-lines (open-input-file rc-path))))
