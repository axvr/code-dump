(ns uk.axvr.halogen.type
  "Type coercion functions for Halogen."
  (:require [cheshire.core :as json]
            [clojure.edn :as edn]
            [clojure.data.xml :as xml])
  (:import [java.io ByteArrayInputStream
                    InputStream
                    InputStreamReader
                    BufferedReader
                    PushbackReader]))


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

  ;; TODO: clj-http

  ;; TODO: extend-protocol for this in custom HttpKit handler.
  #_org.httpkit.BytesInputStream
  #_(->input-stream [this _]
      (->input-stream (.bytes ^org.httpkit.BytesInputStream this))))


(def media-type-hierarchy
  "Atom of media-type hierarchy rules.

  Currently only used to map HAL variants of media-types to its
  equivalent base media-type for the encode/decode multimethods.

  Example:

    (isa? @media-type-hierarchy
          :application/hal+json
          :application/json)
    => true"
  (-> (make-hierarchy)
      (derive :application/hal+json :application/json)
      (derive :application/hal+xml  :application/xml)
      (derive :application/hal+edn  :application/edn)
      (atom)))


(defmulti encode
  "Encodes data for transmission over HTTP based on its media-type.
  Returns the data as a java.io.InputStream."
  (fn [_ ct] (:media-type ct))
  :hierarchy media-type-hierarchy)

(defmethod encode :application/json
  [body content-type]
  ;; TODO: avoid intermediate string step.
  (->input-stream (json/generate-string body)
                  (:charset content-type)))

(defmethod encode :application/edn
  [body content-type]
  (->input-stream (prn-str body)
                  (:charset content-type)))

;; TODO
(defmethod encode :application/xml
  [body content-type]
  (xml/emit))

(defmethod encode :default
  [body content-type]
  (->input-stream body (:charset content-type)))


(defmulti decode
  "Decodes a java.io.InputStream based on its media-type."
  (fn [_ ct] (:media-type ct))
  :hierarchy media-type-hierarchy)

(defmethod decode :application/json
  [body content-type]
  ;; TODO: split input-stream->buffered-reader out to a function.
  (as-> body %
    (InputStreamReader. ^InputStream % ^String (:charset content-type))
    (BufferedReader. ^InputStreamReader %)
    ;; TODO: custom keyword function to fix CURIEs.
    ;;   - needs to work ONLY on top level keywords in :_links.
    (json/parse-stream ^BufferedReader % keyword)))

(defmethod decode :application/edn
  [body content-type]
  ;; TODO: split input-stream->buffered-reader out to a function.
  (as-> body %
    (InputStreamReader. ^InputStream % ^String (:charset content-type))
    (BufferedReader. ^InputStreamReader %)
    (PushbackReader. ^BufferedReader %)
    (edn/read)))

;; TODO
(defmethod decode :application/xml
  [body content-type])

(defmethod decode :text/plain
  [body content-type]
  (String.
    (.readAllBytes ^InputStream body))
    (:charset content-type))

(defmethod decode :default
  [body content-type]
  body)
