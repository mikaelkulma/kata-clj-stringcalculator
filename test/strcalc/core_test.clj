(ns strcalc.core-test
  (:require [midje.sweet :refer :all]
            [strcalc.core :refer :all]))

(facts "about string calculator"
       (fact "empty string returns zero"
             (add "") => 0)
       (fact "single number returns the number"
             (add "1") => 1)
       (fact "two comma separated numbers return their sum"
             (add "1, 2") => 3
             (add "2, 2") => 4
             (add "243, 244") => 487
             (add "138, 10") => 148)
       (fact "any amount of comma separated numbers return their sum"
             (add "1, 2, 3, 4, 5") => 15)
       (fact "newlines can be used as separators, in addition to commas"
             (add "1, 2, 3\n 4, 5") => 15)
       (fact "an extra separator can be configured on the first line"
             (add "//[;]\n1; 2; 3\n 4, 5") => 15)
       (fact "an exception 'negatives not allowed' is thrown for negative numbers"
             (add "-100, 2") => (throws Exception))
       (fact "numbers larger than 1000 are ignored"
             (add "1001, 2") => 2)
       (fact "separators can be of any length"
             (add "//[###]\n1### 2### 3\n 4, 5") => 15)
       (fact "multiple separators are allowed"
             (add "//[###][;;]\n1### 2### 3;; 4, 5") => 15)
       )
