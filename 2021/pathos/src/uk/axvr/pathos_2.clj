(ns uk.axvr.pathos-2
  (:require [clojure.java.io :as io]))

;;;; Abstract file path representation.

;;; File paths are represented as vectors of each path component rather than
;;; strings.  This avoids needing to handle separator logic and makes it
;;; easier to manipulate file paths.
;;;
;;; ["foo" "bar" "biz.txt"] <-> foo/bar/biz.txt
;;;
;;; TODO: handle absolute paths.
;;; [:/ "home" "axvr" "foo" "bar" "biz.txt" :#] <-> /home/axvr/foo/bar/biz.txt


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

(defn <-file [file]
  ;; TODO: remove ".." and "."
  (map #(.toString %)
       (.toPath file)))

(defn ->file [path]
  ;; TODO: escape separators in path components?
  (cond
    (= (first path) :/)
    (->file (cons "/" (rest path)))
    ;; TODO: ^ if, not relative path.
    
    (= (last path) :#)
    (->file (drop-last 1 path))
    
    :else
    (apply io/file path)))

(defn to-string [path]
  (.toString (->file path)))

;; (defn move [old-path new-path])

;; (defn copy [path new-path])

(defn delete [path]
  (.delete (->file path)))

(defn exists? [path]
  (.exists (->file path)))

(defn create [path]
  ;; (if (= (last path) :.))
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
  (= (last path) :#))

(defn dir? [path]
  (not (file? path)))

(defn tree [path]
  (->> path
       to-java-file
       file-seq
       (map <-file)))

(defn write [path lines]
  (when-not (exists? path)
    (create path :file))
  (with-open [writer (clojure.java.io/writer (to-string path))]
    (doseq [line lines]
      (.write writer line))))

(defn contents [path]
  (clojure.core/slurp (to-string path)))
