(ns non-determinism)

(def ^:dynamic *cont* identity)

;; Equivalent to PG's `=lambda`.
(defmacro =fn
  "Create a CPS lambda."
  [params & body]
  `(bound-fn ~params ~@body))

;; Equivalent to PG's `=defun`.
(defmacro =defn
  "Define a CPS function."
  [name params & body]
  `(def ~name (bound-fn ~name ~params ~@body)))

(defmacro =bind [params expr & body]
  `(binding [*cont* (=fn [~params] ~@body)] ~expr))

;; Somewhat equivalent to PG's `=values`, except that Clojure doesn't use
;; multiple return values, so this only accepts a single value.
(defmacro =ret
  "Return a value from a CPS function."
  [ret]
  `(*cont* ~ret))

(=defn message []
       (=ret '[hello there]))

(=defn baz []
       (=bind [a b] (message)
              (=ret [b a])))

(clojure.walk/macroexpand-all
 '(=defn baz []
       (=bind [a b] (message)
              (=ret [b a]))))

(baz)
