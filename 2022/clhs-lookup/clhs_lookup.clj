(ns clhs-lookup
  "Generate Common Lisp symbol lookup table for looking stuff up in the
  hyperspec.
  
  Public domain.  No rights reserved."
  (:require [net.cgrand.enlive-html :as scrape]
            [clojure.string :as str])
  (:import [java.net URL URI]))

(def clhs-symbols "http://www.lispworks.com/documentation/HyperSpec/Front/X_AllSym.htm")

(defn generate [& _]
  (as-> (URL. clhs-symbols) %
    (scrape/html-resource %)
    (scrape/select % [:ul :li :a])
    (map (fn [{{href :href} :attrs
               [{[sym & _] :content} & _] :content}]
           {:href href, :sym sym})
         %)
    (map (fn [s] (update s :href #(.resolve (URI. clhs-symbols) %))) %)
    (map (fn [{:keys [href sym]}] (str sym " " href)) %)
    (concat ["; Generated from the Common Lisp HyperSpec <http://www.lispworks.com/documentation/HyperSpec/Front/index.htm>" 
             "; Copyright \u00A9 2005 Kent M. Pitman"]
            %)
    (interleave % (repeat "\n"))
    (str/join %)
    (spit "symbols.lisp" %)))
