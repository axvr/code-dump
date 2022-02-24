(ns uk.axvr.halogen.utils-test
  (:require [clojure.test :refer :all]
            [uk.axvr.halogen.utils :as u]))

(deftest amb-get
  (testing "Not found -> nil"
    (is (nil? (u/amb-get {"Accept" "application/json"}
                         "Content-Type"))))
  (testing "Not found -> fallback"
    (is (= "text/html"
           (u/amb-get {"Accept" "application/json"}
                      "Content-Type"
                      "text/html"))))
  (testing "Lower case keyword"
    (is (= "application/hal+json"
           (u/amb-get
             {:accept       "application/json"
              :content-type "application/hal+json"}
             "Content-Type"))))
  (testing "Upper case keyword"
    (is (= "application/hal+json"
           (u/amb-get
             {:ACCEPT       "application/json"
              :CONTENT-TYPE "application/hal+json"}
             "Content-Type"))))
  (testing "Inconsistent case keyword"
    (is (= "application/hal+json"
           (u/amb-get
             {:AcCEpT       "application/json"
              :COnTEnT-TYpE "application/hal+json"}
             "Content-Type"))))
  (testing "As is string"
    (is (= "application/hal+json"
           (u/amb-get
             {"Accept"       "application/json"
              "Content-Type" "application/hal+json"}
             "Content-Type"))))
  (testing "Lower case string"
    (is (= "application/hal+json"
           (u/amb-get
             {"accept"       "application/json"
              "content-type" "application/hal+json"}
             "Content-Type"))))
  (testing "Upper case string"
    (is (= "application/hal+json"
           (u/amb-get
             {"ACCEPT"       "application/json"
              "CONTENT-TYPE" "application/hal+json"}
             "Content-Type"))))
  (testing "Inconsistent case string"
    (is (= "application/hal+json"
           (u/amb-get
             {"AccEpT"       "application/json"
              "conTenT-tYpE" "application/hal+json"}
             "Content-Type"))))
  (testing "Empty map"
    (is (nil? (u/amb-get {} "Content-Type"))))
  (testing "Nil map"
    (is (nil? (u/amb-get nil "Content-Type"))))
  (testing "Invalid key"
    (is (nil? (u/amb-get
                {"Accept"       "application/json"
                 "Content-Type" "application/hal+json"}
                123))))
  (testing "Nil key"
    (is (nil? (u/amb-get
                {"Accept"       "application/json"
                 "Content-Type" "application/hal+json"}
                nil)))))
