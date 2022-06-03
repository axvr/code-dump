(ns uk.axvr.pathos-3
  (:require [clojure.java.io :as io]
            [clojure.string  :as str])
  (:import java.io.File java.net.URL))


;;; A path is represented as a sequence of strings where each string is
;;; a component of a path.
;;;
;;; ["foo" "bar" "biz.txt"] <--> foo/bar/biz.txt (or .\foo\bar\biz.txt)
;;; [:root "home" "axvr" "Projects" "pathos" "README.md"] <--> /home/axvr/Projects/pathos/README.md
;;; [:home "Projects" "pathos" "README.md"] <--> ~/Projects/pathos/README.md
;;; [:C "Users" "Alex" "source" "pathos" "README.md"] <--> C:\Users\Alex\source\pathos\README.md
;;; [:root "Users" "Alex" "source" "pathos" "README.md"] <--> \Users\Alex\source\pathos\README.md
;;; [] <--> .
;;;
;;; :root     - System root (abs path when resolved)
;;; :home     - $HOME (abs path when resolved)
;;; :resource - JWM resources (abs path when resolved)
;;; :up       - .. (relative paths only)
;;; nil       - Ignored
;;;
;;; Each position can contain another vector to represent branches?
;;;
;;; [:home "Projects" ["pathos" "cereal"] "README.md"] <--> ~/Projects/{pathos,cereal}/README.md


;;; Definitions:
;;;   - Path:  [:root "home" "axvr" "Projects"]
;;;   - Route: Anything that isn't a "path" but represents the same thing.
;;;   - File:  A java file object.


;; TODO: standardise exception data.
;; TODO: better name for "route"

(defmulti ->path class)

(defmethod ->path String
  [route]
  (-> route io/file ->path))

(defmethod ->path URL
  [url]
  (let [protocol (.getProtocol url)]
    (if (= "file" protocol)
      (-> url io/file ->path)
      (throw (ex-info "Cannot create a path to a remote file."
                      {:object   url
                       :class    (class url)
                       :protocol protocol})))))

(defmethod ->path File
  [file]
  (let [path (->> (.. file toPath normalize)
                  (map (memfn toString))
                  (replace {".." :up, "~" :user})
                  vec)]
    (if (.isAbsolute file)
      ;; TODO: Windows drive labels to symbols (instead of :root?).
      (conj path :root)
      path)))

(defmethod ->path :default
  [route]
  (if (seq? route) ;; TODO: use spec s/valid? to check if is a valid route.
    route
    (throw (ex-info "Cannot convert given route into a path."
                    {:route route, :class (class route)}))))


(def ^:private user-dir
  (->path (System/getProperty "user.home")))


;; TODO: :up -> ".."
;; TODO: :root (+ on Windows)
;; TODO: :user
;; TODO: resource
(defn ->file [path]
  ;; FIXME: fails if path contains no components.
  (apply io/file path))


(def normalise
  ;; TODO: normalise without converting to file (and back).
  (comp ->path ->file))


;; TODO: test this works.
(defn ->string [path]
  (-> path ->file (. toString)))


(comment
  (->path "../cereal/README.md")
  (->path ".././cereal/README.md")
  (->path "/cereal/README.md")
  (->path "~/cereal/README.md")
  (->path "cereal/README.md")
  )


(defn expand
  "Expands relative-absolute paths into full absolute paths.  E.g. use of :user
  will be expanded."
  ;; TODO: [:root] -> [:C] on Windows.
  [path])

(defn rel->abs [base path])

(defn abs->rel [base path])

;; TODO: use different rules on Windows than Unix-likes?
(defn absolute? [path]
  (let [root (get path 0)]
    (and (not= root :up)
         (symbol? root))))

(defn relative? [path]
  (not (absolute? path)))

(defn file? [path]
  (.isFile (->file path)))

(defn dir? [path]
  (.isDirectory (->file path)))

(defn hidden? [path]
  (.isHidden (->file path)))

(defn exists? [path]
  (.exists (->file path)))

(defn create-file [path]
  ;; TODO: option to auto-create parent dir(s).
  (.createNewFile (->file path)))

(defn create-dir [path]
  (.mkdirs (->file path)))

(defn delete [path]
  (.delete (->file path)))

(defn write [path contents])

(defn open-file [path])

;; (defn read [path])

(defn list-files [path])

(defn list-dirs [path])

;; (defn list [path])

(defn tree [path])

;; (defn empty [path])

;; (defn empty? [path])

(defn copy [path to])

(defn move [path to])

(defn rename [path to])

;; (defn = [& paths])

;; TODO: use hashsets.
;;   - :read
;;   - :write
;;   - :exec
;;   - :hide ???
(defn flags [path])

(defn set-flags [path flags])

(defn remove-flags [path flags])
