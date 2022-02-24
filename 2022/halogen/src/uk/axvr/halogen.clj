(ns uk.axvr.halogen
  (:require [clojure.string :as str]
            [uk.axvr.halogen.utils :as u]
            [uritemplate-clj.core :refer [uritemplate]])
  (:import java.net.URL))


;; NOTE: work-in-progress.


(defrecord Resource
  [_links _embedded _status _response _base])


(defrecord Link
  [href templated type deprecation name profile title hreflang])


;; TODO: add rel href to base URL.
;; TODO: CURIEs.
;; TODO: auto-convert body and response.


(def ^:dynamic *http-fn*)

(def ^:dynamic *translators*
  "Converters per media type."
  #{"application/hal+json" {:to   nil
                            :from nil}
    "application/hal+xml"  nil
    "application/hal+edn"  nil})

(def ^:dynamic *default-encoding*
  "application/hal+json")


(defn find-rel
  ([resource rel]
   (let [links (:_links resource)]
     (or (get links rel)
         (some
           (fn [[_ link]]
             (let [match #(when (= (:name %) rel) %)]
               (if (or (vector? link) (list? link))
                 (some match link)
                 (match link))))
           links))))
  ([resource rel idx]
   (let [links (:_links resource)]
     (when-let [link (get links rel)]
       (when (or (vector? link) (list? link))
         (or (get link idx)
             (some #(when (= (:name %) idx) %) link)))))))


(comment
  (find-rel
    {:_links
     {:bar {:href "http://example.com/bar"}
      :foo {:href "http://example.com"}}}
    :foo)

  (find-rel
    {:_links
     {:bar {:href "http://example.com/bar"}
      :foo {:href "http://example.com"}}}
    :biz)

  (find-rel
    {:_links
     {:bar {:href "http://example.com/bar"}
      :foo {:href "http://example.com"
            :name :biz}}}
    :biz)

  (find-rel
    {:_links
     {:bar {:href "http://example.com/bar"}
      :foo [{:href "http://example.com"
             :name :biz}
            {:href "http://example.com/baz"
             :name :baz}]}}
    :baz)

  (find-rel
    {:_links
     {:bar {:href "http://example.com/bar"}
      :foo [{:href "http://example.com"
             :name :biz}
            {:href "http://example.com/baz"
             :name :baz}]}}
    :foo 1)

  (find-rel
    {:_links
     {:bar {:href "http://example.com/bar"}
      :foo [{:href "http://example.com"
             :name :biz}
            {:href "http://example.com/baz"
             :name :baz}]}}
    :foo :biz)
  )





(defn extract-content-type [req]
  (let [content-type (-> req
                         :headers
                         (u/amb-get "Content-Type" "text/html")
                         str/lower-case)
        [mime props] (str/split content-type #";" 2)
        media-type   (str/trim mime)
        properties   (when props (str/trim props))
        charset      (as-> properties %
                       (or % "")
                       (re-find #"charset=([A-Za-z0-9-]+)" %)
                       (nth % 1 "UTF-8")
                       (str/upper-case %))]
    {:content-type content-type
     :media-type   media-type
     :charset      charset
     :properties   properties}))

(comment
  (extract-content-type {:headers {}})
  (extract-content-type
    {:headers {"Content-type" "application/hal+json;charset=utf-16"}})
  )


;; TODO: add base href to rel.
(defn compile-link
  ([link]
   (compile-link link {}))
  ([link params]
   (when-let [href (:href link)]
     (if (:templated link)
       (uritemplate href (or params {}))
       href))))

(comment
  (compile-link
    {:href "http://localhost:8080/foo/{id}{?q}"
     :templated true}
    {:id "Somet/hing"
     :q [2 3 "he+l/lo"]})

  (compile-link
    {:href "/foo/{id}{?q}"
     :templated true}
    {:id "Somet/hing"
     :q [2 3 "he+l/lo"]}))


(defn get
  [resource rel & {:keys [headers body params] :as opts}]
  (let [req (template )])
  (*http-fn*
    {:method :get
     :url ""
     :body nil
     :headers nil}))


(defmacro defreq [])


(defn post
  ""
  [resource rel & {:keys [headers body params] :as opts}]
  nil)


(defn discover [entry]
  ;; GET base, call :discover
  )


(defn resp->hal [])


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

  ;; Response
  (hal/->resource
    {:foo 1
     :bar 2}
    :links {:self "/foo"}
    :embed {}
    :enc :json)
)
