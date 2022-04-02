;;;; Http mocking lib.  (Needs a name.)
;;;; Public domain.  No rights reserved.

(def ^:dynamic *stubs* (atom (list)))

(defn- http-stub-match?
  "Check if a stub matches a given HTTP request."
  [req stub]
  (second stub)
  ;; TODO write this.
  ;; TODO decide on HTTP stub format.
  )

(defn http
  "HTTP mocking function."
  [real-http-fn]
  (fn [req]
    (if-let [resp (some (partial http-stub-match? req) @*stubs*)]
      resp
      (real-http-fn req))))

(defmacro with-stub-scope
  "Scope the registered stubs to the lexical scope."
  [& body]
  `(binding [*stubs* (atom @*stubs*)]  ; might not work as expected.
     ~@body))

(defmacro with-mock
  "Register the below scope with a mocked dynamic fn."
  [*dynfn* mock & body]
  `(with-stub-scope
     (binding [~*dynfn* (~mock ~*dynfn*)]
       ~@body)))

(defmacro with-http-mock
  "Register the below scope with a mocked HTTP fn."
  [*dynfn* & body]
  `(with-mock ~*dynfn* http
     ~@body))

(defn stub!
  "Register a stub in the current stub scope."
  [stub]
  (swap! *stubs* conj stub))

(defmacro with-stub
  "Register a stub for tests in the body."
  [stub body]
  `(with-stub-scope (stub! ~stub) ~@body))

(defmacro with-stubs
  "Like with-stub but registers multiple stubs at once."
  [[stub & stubs] body]
  (if (seq stubs)
    `(with-stub ~stub
       (with-stubs ~stubs
         ~@body))
    `(with-stub ~stub
       ~@body)))

;; (mock/with-mock *http-fn* mock/http
;;   (mock/stub! ,,,)
;;   (mock/stub! ,,,)
;;   (mock/with-stub-scope
;;     (mock/stub! ,,,)
;;     (testing "something"
;;       (is something)))
;;   (testing "something else"
;;     (is (= something else)))
;;   ,,,)

;; (mock/with-mock *http-fn* mock/http
;;   (mock/with-stubs
;;     [,,,
;;      ,,,
;;      ,,,]
;;     ,,,))

(def ^:dynamic *http-fn* http/request)
(require '[org.httpkit.client :as http])
(set! *http-fn* )

@(*http-fn* {:url "https://example.com" :method :get})

;; (clojure.pprint/pprint
;;   (clojure.walk/macroexpand-all
;;     '(with-http-mock *http-fn*
;;        (with-stub [{:url "https://example.com/hello"}
;;                    {:status 200 :body "Hello world!"}]
;;          @(*http-fn* {:url "asdfa"})))))

;; (with-http-mock *http-fn*
;;   (with-stub [{:url "https://example.com/hello"}
;;               {:status 200 :body "Hello world!"}]
;;     @(*http-fn* {:url "asdfa"})))
