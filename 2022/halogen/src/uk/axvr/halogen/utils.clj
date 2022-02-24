(ns uk.axvr.halogen.utils
  (:require [clojure.string :as str]))


(defn amb-get
  "Get the value of an ambiguous key in a map as efficiently *as possible*.

  If you want to look up a key in a map, but aren't sure what the key will look
  like, this function will try to find it.

  Example:

    Some Clojure HTTP clients encode HTTP headers as keywords, others encode
    them as strings.  Of those some will lower-case those values but some
    won't.  When writing a cross-HTTP-client library, how do you get the value
    of a header?

    (amb-get
      {:content-TYPE \"application/json\"}
      \"Content-Type\"
      \"text/html\")
    => \"applicaion/json\""
  ([m k]
   (when (and (or (string? k) (keyword? k))
              (map? m)
              (seq m))
     (let [kn (name k)]
       (or (get m kn)                     ; Key.
           (get m (keyword k))            ; Key as keyword.
           (let [kl (str/lower-case kn)]
             (or (get m kl)               ; Key in lower case.
                 (get m (keyword kl))     ; Key as lower case keyword.
                 (some #(when (= kl       ; All other forms.
                                 (-> % first name str/lower-case))
                          (second %))
                       m)))))))
  ([m k not-found]
   (or (amb-get m k) not-found)))
