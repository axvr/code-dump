(ns uk.axvr.halogen-test
  (:require [clojure.test :refer :all]
            [uk.axvr.halogen :as h]))


;; TODO
(deftest content-type-info
  )
(comment
  (content-type-info {:headers {}})
  (content-type-info
    {:headers {"Content-type" "application/hal+json;charset=utf-16"}})
  )


;; TODO
(deftest find-rel
  )
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
