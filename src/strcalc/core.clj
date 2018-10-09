(ns strcalc.core
  (:require [clojure.string :as str]))

(defn cast-to-int [s] (Integer/parseInt (re-find #"[+|-]?\d+" s)))

(defn begins-with-2slashes [s] (re-find #"^\/\/.*\n" s))

(defn fail-on-negative [num] (if (< num 0) (throw (Exception. "negatives not allowed")) num))

(defn ignore>1k [n] (if (> n 1000) 0 n))

(defn strip-starting-slashes [s] (second (re-find #"^\/\/(.*)" s)))

(defn drop-first-and-last-chars [s] (str/join "" (drop-last (subs s 1))))

(defn parse-pattern
  [s] (str/replace (drop-first-and-last-chars (strip-starting-slashes s)) #"\]\[" "|"))

(defn take-first-line [s] (first (str/split s #"\n")))

(defn to-regex [s] (re-pattern (str ",|\n|" (parse-pattern s))))

(defn drop-first-line [s] (second (re-find #".*\n(?s)(.*)" s)))

(defn tokenize [s, regex] (str/split s regex))

(defn to-number [n] (if (= 0 (count n)) 0 (ignore>1k (fail-on-negative (cast-to-int n)))))

(defn add [s]
  (if (begins-with-2slashes s)
    (reduce + (map to-number (tokenize (drop-first-line s) (to-regex (take-first-line s)))))
    (reduce + (map to-number (tokenize s #",|\n")))
    )
  )
