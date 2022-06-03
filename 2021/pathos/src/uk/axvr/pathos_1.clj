(ns uk.axvr.pathos-1
  (:require [clojure.java.io :as io]))

;;;; Abstract file path representation.

;;; File paths are represented as vectors of each path component rather than
;;; strings.  This avoids needing to handle separator logic and makes it
;;; easier to manipulate file paths.
;;;
;;; ["foo" "bar" "biz.txt"] <-> foo/bar/biz.txt
;;;
;;; TODO: handle absolute paths.
;;; [:root "home" "axvr" "foo" "bar" "biz.txt"] <-> /home/axvr/foo/bar/biz.txt
;;;
;;; https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/File.html
;;; https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/nio/file/Path.html


(comment

  ["foo" "bar" "biz.txt"]  ; File or directory?

  ["foo" "bar" "biz.txt" :file]

  {:type :file
   :path ["foo" "bar" "biz.txt"]}

  {"foo" {"bar" {"biz.txt" :$
                 "foo.txt" :$
                 "biz" {}}}}  ; File does not contain children.

  {"foo" {"bar" {}}}  ; Directory contains children.

  )

(defn from-java-file [file]
  ;; TODO: remove ".." and "."
  (vec (map #(.toString %)
            (.toPath file))))

(defn to-java-file [path]
  ;; TODO: escape separators in path components?
  (apply io/file path))

(defn to-string [path]
  (.toString (to-java-file path)))

;; (defn move [old-path new-path])

;; (defn copy [path new-path])

(defn delete [path]
  (.delete (to-java-file path)))

(defn exists? [path]
  (.exists (to-java-file path)))

(defn create [path type]
  (case type
    :file (if (exists? path)
            true
            (if (> (count path) 1)
              (if (create (drop-last path) :dir)
                (.createNewFile (to-java-file path))
                false)
              (.createNewFile (to-java-file path))))
    :dir (if (exists? path)
           true
           (.mkdirs (to-java-file path)))
    nil))

(defn file? [path]
  (.isFile (to-java-file path)))

(defn dir? [path]
  (.isDirectory (to-java-file path)))

(defn tree [path]
  (->> path
       to-java-file
       file-seq
       (map from-java-file)))

(defn write [path lines]
  (when-not (exists? path)
    (create path :file))
  (with-open [writer (clojure.java.io/writer (to-string path))]
    (doseq [line lines]
      (.write writer line))))

(defn contents [path]
  (clojure.core/slurp (to-string path)))
