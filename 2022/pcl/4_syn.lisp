1/2

(* 2 1/2)

(+ 1/3 1/3 1/3)

(+ 88888888888888888888888888888888888888888888888888888888888888888888888888888888888888
   11111111111111111111111111111111111111111111111111111111111111111111111111111111111111)

123

3/7

1.0

1.0e0

1.0d0

1.0e-4

+42

-42

-1/4

-2/8

246/2

(+ 0.1 0.2)

.3


(equal "foo" "fo\o")

"fo\\o"

"fo\"o"


'\o

'|(symbol containing brackets!)|

'|f|oo|  ; not possible.

(eq 'foo 'FOO)

(eq '\f\o\o '|foo|)

(not (eq 'foo '|foo|))


*global-variables*

+constants+

%low-level-fns

#\f  ; character

t

nil

()

(eq nil ())

(eq '() ())

(eq t 't)

pi

atom

(atom "hello")

(atom 'foo)

(atom 1)

(atom #\a)

(atom :foo)

(atom #'evenp)

(atom ()) ; (atom nil)

(null (atom '(1 2 3)))

32/4

=      ; compare numbers
char=  ; compare characters

eq     ; "object identity" true if identical.  (Equivalent to Clojure's identical?)
       ; never use eq to compare numbers or characters.
       ; (eq 1 1) can be true/false depending on the CL implementation.
eql    ; like eq but checks if instances of the same class represent the same value.
       ; So (eql 1 1) is true, but (eql 1.0 1) is false.
equal  ; Works on all objects, true if lists have the same structure contents.
       ; Falls back on eql.
equalp ; Same as equal, but ignores character case (in strings and chars) and
       ; considers numbers to be the same if they represent the same mathematical value.
       ; (equalp 1 1.0) is true.
       ; Falls back on eql.


;;;; Top-level comment.
;;; Section/start-of-line comment.
;; Inline comment.
; End of line comment.
