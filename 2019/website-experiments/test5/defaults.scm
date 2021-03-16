(module defaults (file default-attrs attr-modifiers)

  (import scheme)

  (load "lib/alist.scm")

  (import (prefix alist al/))


  (define file "index.md") ; TODO: replace with CLI args

  (define default-attrs
    '(("author" . "Alex Vear")
      ("copyright" . "Public domain.  No rights reserved.")))


  ;; Full page title.
  (define (page-title attrs)
    (let ((title (al/get attrs "title"))
          (author (al/get attrs "author")))
      (al/assoc attrs ".page-title" (if (> (string-length title) 0)
                                      (string-append title " — " author)
                                      author))))

  ;; HTML meta tags.
  (define (meta-tags attrs)
    (let ((pairs (al/dissoc
                   (al/map-on-keys
                     (lambda (x)
                       (if (eq? #\. (car (string->list x)))
                         "PRIVATE"
                         x))
                     attrs)
                   "PRIVATE")))
      (al/assoc attrs
                ".meta-tags"
                (apply string-append
                       (al/map-on
                         (lambda (k v)
                           (string-append "<meta name=\"" k "\" content=\"" v "\">"))
                         pairs)))))

  ;; Page redirects.
  (define (redirect attrs)
    (let ((redirect-uri (al/get attrs ".redirect")))
      (al/assoc attrs ".redirect-tag" (if (eq? redirect-uri '())
                                    ""
                                    (string-append "<meta http-equiv=\"refresh\" content=\"0;url=" redirect-uri "\"/>")))))

  (define attr-modifiers
    (list page-title
          meta-tags
          redirect)))
