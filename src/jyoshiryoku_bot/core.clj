(ns jyoshiryoku-bot.core
  (:import [twitter4j TwitterFactory Twitter Paging])
  (:require [clojure.contrib.str-utils :as str-utils]))

(defn mytwitter []
  (.getInstance (TwitterFactory.)))

(defn tweettimeline [message]
  (let [twitter (mytwitter) , user (.verifyCredentials twitter)]
    (.updateStatus twitter message)
    ))

(defn mymentionlist []
  (.getMentionsTimeline (mytwitter)))


(def paging
  (Paging. (Integer. 1) (Integer. 200)))

(defn getmytweet []
  (let [twitter (mytwitter)]
     (map #(. %1 getText) (.getUserTimeline twitter paging))))

