(ns strcalc.core
  (:require [clojure.string :as str]))

(defn add [s]
  (->> s
       (re-seq #"[-]?\d+")
       (cons "0")
       (map #(Integer/parseInt %))
       (#(if (some neg? %) (throw (Exception. "negatives not allowed")) %))
       (remove (partial < 1000))
       (reduce +)))
