(ns uk.axvr.halogen.types
  "Type coercion functions for Halogen."
  (:require [cheshire.core :as json]
            [clojure.edn :as edn])
  (:import [java.io ByteArrayInputStream
                    InputStream
                    InputStreamReader
                    OutputStreamWriter
                    BufferedWriter
                    BufferedReader
                    PushbackReader
                    PipedInputStream
                    PipedOutputStream]))


(defprotocol Streamable
  (->input-stream [this charset] "Convert \"this\" into an input stream."))

(extend-protocol Streamable
  (class (byte-array 0))
  (->input-stream [this _]
    (ByteArrayInputStream. ^bytes this))

  nil
  (->input-stream [_ _] nil)

  String
  (->input-stream [this charset]
    (->input-stream (.getBytes ^String this ^String charset) charset))

  InputStream
  (->input-stream [this _] this)

  ;; TODO: extend-protocol for this in custom HttpKit handler.
  #_org.httpkit.BytesInputStream
  #_(->input-stream [this _]
      (->input-stream (.bytes ^org.httpkit.BytesInputStream this))))


(defn- input-stream->buffered-reader
  [^InputStream input-stream ^String charset]
  (BufferedReader. (InputStreamReader. input-stream charset)))


(def media-type-hierarchy
  "Atom of media-type hierarchy rules.

  Currently only used to map HAL variants of media-types to its
  equivalent base media-type for the encode/decode multimethods.

  Example:

    (isa? @media-type-hierarchy
          :application/hal+json
          :application/json)
    => true

    (swap! uk.axvr.halogen.types/media-type-hierarchy
           derive :text/html :text/plain)

    (isa? @media-type-hierarchy
          :text/html
          :text/plain)
    => true"
  (-> (make-hierarchy)
      (derive :application/hal+json     :application/json)
      (derive :application/vnd.hal+json :application/hal+json)
      (derive :application/hal+xml      :application/xml)
      (derive :application/vnd.hal+xml  :application/hal+xml)
      (derive :application/hal+edn      :application/edn)
      (atom)))


(defmulti encode
  "Encodes data for transmission over HTTP based on its media-type.
  Returns the data as a java.io.InputStream."
  (fn [_ opts] (:media-type opts))
  :hierarchy media-type-hierarchy
  :default :application/octet-stream)

(defmethod encode :application/json
  [body opts]
  (let [in (PipedInputStream.)]
    ;; NOTE: Stream writing is done in a separate thread to prevent deadlocking.
    (future
      (with-open [out     (PipedOutputStream. in)
                  swriter (OutputStreamWriter. out (:charset opts))
                  bwriter (BufferedWriter. swriter)]
        (json/generate-stream body bwriter opts)))
    in))

(defmethod encode :application/edn
  [body opts]
  (->input-stream (prn-str body) (:charset opts)))

(defmethod encode :application/octet-stream
  [body opts]
  (->input-stream body (:charset opts)))


(defmulti decode
  "Decodes a java.io.InputStream based on its media-type."
  (fn [_ opts] (:media-type opts))
  :hierarchy media-type-hierarchy
  :default :application/octet-stream)

(defmethod decode :application/json
  [body opts]
  (-> body
    (input-stream->buffered-reader (:charset opts))
    ;; TODO: custom keyword function to fix CURIEs.
    ;;   - needs to work ONLY on top level keywords in :_links.
    ;;   - don't convert all keywords?
    (json/parse-stream keyword)))

(defmethod decode :application/edn
  [body opts]
  (-> body
      (input-stream->buffered-reader (:charset opts))
      (PushbackReader.)
      (edn/read)))

(defmethod decode :text/plain
  [body opts]
  (String.
    (.readAllBytes ^InputStream body))
    (:charset opts))

(defmethod decode :application/octet-stream
  [body _]
  body)
