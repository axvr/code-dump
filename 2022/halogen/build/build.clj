(ns build
  (:require [clojure.tools.build.api :as b]
            [org.corfield.build :as bb]))

(def lib 'uk.axvr/halogen)

(def version
  (if-let [git-tag (b/git-process {:git-args ["describe" "--tags" "--abbrev=0"]})]
    (subs git-tag 1)
    (throw (ex-info "No Git tags found" {}))))

(defn- mkopts [opts]
  (merge
    {:lib     lib
     :version version
     :src-pom "build/pom.xml"}
    opts))

(defn clean
  "Clean the targets folder."
  [opts]
  (bb/clean (mkopts opts)))

(defn jar
  "Build the JAR."
  [opts]
  (-> (mkopts opts) clean bb/jar))

(defn install
  "Install the JAR locally."
  [opts]
  (bb/install (mkopts opts)))

(defn deploy
  "Deploy the JAR to Clojars."
  [opts]
  (bb/deploy (mkopts opts)))
