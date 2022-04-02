;;;; Http-Kit mocking lib.
;;;; Public domain.  No rights reserved.

(require '[clojure.test :as test]
         '[org.httpkit.client :as http])
(import '[org.httpkit HttpClient])

(def ^:dynamic *stubs*)

(defn invoke-cb [cb status headers body]
  (.onInitialLineReceived cb nil (org.httpkit.HttpStatus/valueOf status))
  (.onHeadersReceived cb headers)
  (.onBodyReceived cb nil nil)
  (.onCompleted cb)) ; FIXME

(defn fake-http-client [^HttpClient http-client]
  (reify HttpClient
    (exec (url cfg engine cb)
      ;; TODO check for matching *stubs*, else use original.
      (if-let [match (some #(rule-match? @*stubs* %) {:url url, ,,,})]
        (invoke-cb cb match)
        (.exec ^HttpClient (force http-client) url cfg engine cb)))))

(defmacro stub! [stub]
  (swap! *stubs* conj stub))
