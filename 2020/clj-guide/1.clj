;;;; <https://clojure.org/guides/learn/syntax>
;;;; <https://clojure.org/guides/learn/functions>
;;;; Public domain.  No rights reserved.

"f

o"

\f
#"^foo.*$"

\newline
\tab
\u2014

(doc /)

(defn greet [name]
  (str "Hello, " name))

(greet "students")

(defn messenger
  ([] (messenger "Hello world!"))
  ([msg] (println msg)))

(messenger)
(messenger "Hello class!")

(defn hello [greeting & who]
  (println greeting who))
(hello "Hello" "world" "class")

(fn [message] (println message))
((fn [message] (println message)) "Hello, world")

#(+ 6 %)
(#(+ 6 %) 4)

#(+ %1 %2)
(#(+ %1 %2) 3 5)

(#(println %1 %2 %&) "foo" "bar" "biz")

(apply + '(1 2 3 4))
(apply + 1 2 3 '(4))
(apply + 1 2 3 4 '())

(defn plot [shape coords]
  (plotxy shape (first coords) (second coords)))

(defn plot [shape coords]
  (apply plotxy shape coords))

(let [x 1
      y 2]
  (+ x y))

(defn messenger [msg]
  (let [a 7
        b 5
        c (clojure.string/capitalize msg)]
    (println a b c)))
(messenger "hello")

(defn messenger-builder [greeting]
  (fn [who] (println greeting who)))

(def hello-er (messenger-builder "Hello"))

(hello-er "world!")


(Widget. "foo")
(.nextInt rnd)
Math/PI
(Math/sqrt 25)

(fn [obj] (.length obj))
(#(.length %) "Foo")


(defn greet [] (println "Hello"))

(def greet (fn [] (println "Hello")))
(def greet #(println "Hello"))

(defn greeting
  ([] (greeting "World"))
  ([x] (greeting "Hello" x))
  ([x y] (println (str x ", " y "!"))))

(greeting)
(greeting "Foo")
(greeting "A" "B")

(defn do-nothing [x] x)

(source identity)

(defn always-thing [& xs] 100)

(defn make-thingy [x]
  (fn [& ys] x))

(let [n (rand-int Integer/MAX_VALUE)
      f (make-thingy n)]
  (assert (= n (f)))
  (assert (= n (f 123)))
  (assert (= n (apply f 123 (range)))))

(source constantly)

(defn triplicate [f]
  (f) (f) (f))

(defn opposite [f]
  (fn [& args] (not (apply f args))))

(defn triplicate2 [f & args]
  (triplicate #(apply f args)))

(assert (= -1.0 (Math/cos Math/PI)))

(let [n (rand-int Integer/MAX_VALUE)]
  (assert (= 1.0
             (+ (Math/pow (Math/sin n)
                          2)
                (Math/pow (Math/cos n)
                          2)))))

(defn http-get [url]
  (slurp
    (.openStream
      (java.net.URL. url))))

(defn http-get [url]
  (slurp url))

(assert (.contains (http-get "https://www.w3.org") "html"))

(defn one-less-arg [f x]
  (fn [& args] (apply f x args)))

(defn two-fns [f g]
  (fn [x] (f (g x))))
