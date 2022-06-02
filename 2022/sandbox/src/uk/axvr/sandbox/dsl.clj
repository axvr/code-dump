(ns uk.axvr.sandbox.dsl
  "Mini Clojure-based DSL."
  (:require [potemkin :refer [import-vars]])
  (:refer-clojure :only [defn]))

;; "if" and "def" are not in clojure.core.

(def * clojure.core/*')
(def + clojure.core/+')
(def - clojure.core/-')
(def inc clojure.core/inc')
(def dec clojure.core/dec')

(import-vars
  [clojure.core
   /
   let
   if-not
   when
   when-not
   assoc
   assoc-in
   dissoc
   merge
   update
   update-in
   map
   filter
   reduce
   some
   remove
   keep
   and
   or
   not])

(defn print [x]
  (clojure.core/println x))
