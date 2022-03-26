(format t "hello, world")


(defun hello-world ()
  (format t "hello, world"))

(hello-world)

(defun hello-name (name)
  (format t "hello, ~A" name))

(hello-name "foo")

*

(foo)


(compile-file "2_hello.lisp") ; Creates .fasl file.
