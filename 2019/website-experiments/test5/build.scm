#!/usr/local/bin/csi -script

;;;; Alex Vear's lispy static website generator.
;;;; Public domain.

(load "lib/alist.scm")

(import (chicken file)
        (chicken io)
        (chicken irregex)
        (prefix alist al/))


(load "defaults.scm")
(import defaults)


(define (attributes fname)
  "Retrieve attributes from a file.

      <!-- METADATA           (attr-start)
      title: Hello, world!    (kv-pair)
      author: Foo Bar         (kv-pair)
      -->                     (attr-end)"
  (let ((attr-start '(: bol (* whitespace) "<!--" (* whitespace) "METADATA" (* whitespace) eol))
        (attr-end '(: bol (* whitespace) "-->"))
        (kv-pair '(: bol (=> key (*? any)) ":" (* whitespace) (=> value (* any)) (* whitespace) eol)))

    (define (get-new-pairs pairs line)
      (let ((regex-match (irregex-match kv-pair line)))
        (if (irregex-match-data? regex-match)
          (cons (cons (irregex-match-substring regex-match 'key)
                      (irregex-match-substring regex-match 'value))
                pairs)
          pairs)))

    (with-input-from-file
      fname
      (lambda ()
        (let loop ((line (read-line))
                   (pairs '())
                   (search #f))
          (cond ((eof-object? line) pairs)
                (search (loop (read-line)
                              (get-new-pairs pairs line)
                              (not (irregex-match? attr-end line))))  ; BUG: will attempt to match k/v pair on closing line.
                (else (loop (read-line)
                            pairs
                            (irregex-match? attr-start line)))))))))


(define (content fname)
  "Grab all content from the specified file."
  (with-input-from-file
    fname
    (lambda ()
      (let loop ((line (read-line))
                 (lines '()))
        (if (eof-object? line)
          (reverse lines)
          (loop (read-line)
                (cons line lines)))))))


;; ,p (inject (attributes file) (content file))
(define (inject token-alist text)
  "Replace tokens in text."
  (define (replace str key val)
    (irregex-replace/all `(: (or "{{" "%7B%7B") (* whitespace) ,key (* whitespace) (or "}}" "%7D%7D")) str val))

  (let ((replace-fns (al/map-on (lambda (k v)
                                  (lambda (x) (replace x k v)))
                                token-alist)))
    (map (lambda (line)
           (chain-apply replace-fns line))
         text)))


(define (contents->string lines)
  (apply string-append
         (map (lambda (x) (string-append x "\n"))
              lines)))


(define (write-to-file fname lines)
  (with-output-to-file
    fname
    (lambda ()
      (for-each (lambda (s)
                  (display s)
                  (display "\n"))
                lines))))


;; TODO: define a pipeline in defaults file?
;; TODO: convert from Markdown to HTML.


(define (build target base template)
  (write-to-file target
                 (inject (chain-apply attr-modifiers
                                      (al/merge default-attrs
                                                (attributes base)))
                         (inject (list (cons ".content"
                                             (contents->string (content base))))
                                 (content template)))))

(build "index.html" "test.html" "template.html")
