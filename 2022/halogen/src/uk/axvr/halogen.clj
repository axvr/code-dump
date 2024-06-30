(ns uk.axvr.halogen
  (:require [clojure.string :as str]
            [uk.axvr.halogen.utils :refer [when-let* deep-merge amb-get]]
            [uk.axvr.halogen.types :as types]
            [uritemplate-clj.core :refer [uritemplate]])
  (:import java.net.URI))


(defrecord Resource
  [_links _embedded _status _response _base])


(defrecord Link
  [href templated type deprecation name profile title hreflang])


(def ^:dynamic *http-fn*)


;; TODO: separate var for in and out?
;; TODO: is this needed?
(def ^:dynamic *default-media-type* :application/hal+json)
(def ^:dynamic *default-charset* "UTF-8")


(defn- keyword->string
  "Convert a fully qualified keyword to a string."
  [keyword]
  (subs (str keyword) 1))


(def ^{:private true
       :arglists '([headers])}
  content-type-info
  "Extract useful information from the Content-Type header of an HTTP request
  or response."
  (let [build
        (memoize
          (fn [content-type not-found-media-type not-found-charset]
            (let [[_ media-type parameters charset]
                  (re-find #"(?i)^\s*([^;]+)\s*(;\s*charset=\"?([A-Za-z0-9-]+)\"?)?"
                           content-type)
                  parameters (if charset
                               parameters
                               (str/lower-case
                                 (str "; charset=" not-found-charset parameters)))
                  media-type (if media-type
                               (-> media-type str/trim str/lower-case keyword)
                               not-found-media-type)]
              {:content-type (str (keyword->string media-type) parameters)
               :media-type   media-type
               :parameters   parameters
               :charset      (str/upper-case (or charset not-found-charset))})))]
    #(build (or (amb-get % "Content-Type") "")
            *default-media-type*
            *default-charset*)))


(defn- format-rel-name [rel]
  (cond
    (qualified-keyword? rel)
    (recur (name rel))

    (simple-keyword? rel)
    rel

    (and (string? rel) (str/includes? rel ":"))
    (recur (second (str/split rel #":" 2)))

    :else
    (keyword rel)))

(comment
  (format-rel-name "latest-posts")
  (format-rel-name "docs:latest-posts")
  (format-rel-name :latest-posts)
  (format-rel-name :docs/latest-posts)
  )


(defn find-rel
  ([resource rel]
   (when-let* [links (:_links resource)
               rel   (format-rel-name rel)]
     (or (get links rel)
         (some
           (fn [[_ link]]
             (let [match #(when (= (:name %) rel) %)]
               (if (or (vector? link) (list? link))
                 (some match link)
                 (match link))))
           links))))
  ([resource rel idx]
   (when-let* [links (:_links resource)
               rel   (format-rel-name)
               link  (get links rel)]
      (when (or (vector? link) (list? link))
        (or (get link idx)
            (some #(when (= (:name %) idx) %) link))))))


;; TODO: CURIEs.  "bar:foo" -> :bar/foo  namespace qualified-keyword?
;; TODO: support reading CURIEs, but not writing them, because they're stupid.


(defn compile-link
  ([base link]
   (compile-link base link {}))
  ([base link params]
   (when-let [href (:href link)]
     (let [uri (if (:templated link)
                 (uritemplate href (or params {}))
                 href)]
       (.. (URI. base)
           (resolve uri)
           (toURL)
           (toString))))))

(comment
  (compile-link
    "http://localhost:8080"
    {:href "foo/{id}{?q}"
     :templated true}
    {:id "Somet/hing"
     :q [2 3 "he+l/lo"]}))


(defn build-url
  [resource rel params]
  (let [base (:_base resource)
        link (find-rel resource rel)]
    (compile-link base link params)))


(defn request
  ([uri req]
   ;; TODO: convert response to resource.
   ;;   - convert response body.
   ;; TODO: convert request body.
   ;; TODO: pass req and content-type info to encoder/decoder
   (*http-fn* (assoc req :uri uri)))
  ([resource rel req]
   (let [uri (build-url resource rel (:params req))]
     (request uri req))))


;; (defmacro defreq
;;   [method doc]
;;   {:pre [(symbol? method)
;;          (string? doc)]}
;;   `(defn ~method
;;      ~doc
;;      ;; TODO: get request# should be optional?
;;      [resource# rel# request#]
;;      {:pre [(map? resource#)
;;             (map? req#)
;;             rel#]
;;       :post [(map? %)
;;              (contains? % :status)]}
;;      (request (assoc req# :method ~(keyword method)))))

;; (doseq [method #{:head :options :get :post :put :delete :patch}]
;;   (defreq method))


;; (defn enter [url])
;; (defn discover [entry])


(defn resp->hal [])


(defmacro with-defaults
  [defaults & body]
  `(binding [*http-fn*
             (fn [req#]
               (*http-fn* (deep-merge ~defaults req#)))]
     ~@body))


(comment
  (require '[uk.axvr.halogen :as hal]
           '[clj-http.client :as http]
           #_ '[org.httpkit.client :as http])

  (set! hal/*http-fn* http/request)

  (def base-url "http://localhost:8080")

  ;; Configure default headers and stuff.
  (binding [hal/*http-fn*
            (fn [req]
              (http/request
                (merge-with merge
                            {:headers {:content-type  "application/hal+json"
                                       :authorization "admin"}
                             :as :text}
                            req)))]
    )

  (hal/with-defaults
    {:headers {:content-type  "application/hal+json"
               :authorization "admin"}}
    (hal/get "https://example.com"))


  ;; (-> base-url
  ;;     (hal/get)
  ;;     (get-in [:resource :_links :foo])
  ;;     (hal/tpl {:bar "hello"})
  ;;     (hal/post {:something "in the body"})
  ;;     :resource)

  ;; Request
  (-> base-url
      (hal/get)
      (hal/post :foo
        :params {"foo" "hello"}
        :body {:something "in the body"}
        :headers {:content-type "application/hal+json"})
      :resource)
  (-> base-url
      (hal/get)
      (hal/post
        {:rel :foo
         :params {"foo" "hello"}
         :body {:something "in the body"}
         :headers {:content-type "application/hal+json"}})
      :resource)
  (-> base-url
      (hal/get)
      ;; (assoc-in [:_headers :authorization] "admin")
      (hal/post
        {:rel [:foo :biz]
         :params {"foo" "hello"}
         :body {:something "in the body"}
         :headers {:content-type "application/hal+json"}})
      :resource)
  ;; TODO: pass default headers along the chain?

  ;; Response
  (hal/->resource
    {:foo 1
     :bar 2}
    :links {:self "/foo"
            :foo {:href "/foo/{id}" :templated true}}
    :embed {}
    :enc :json) ; TODO: can't do this?  What if need to modify further?
)
