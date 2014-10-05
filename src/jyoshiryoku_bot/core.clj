(ns jyoshiryoku-bot.core
  (:import [twitter4j TwitterFactory Twitter Paging])
  (:require [clojure.contrib.str-utils :as str-utils]
            [jyoshiryoku-bot.kaiseki :as kaiseki])
  (:gen-class))

(defn mytwitter []
  (.getInstance (TwitterFactory.)))

(defn tweettimeline [message]
  (let [twitter (mytwitter) , user (.verifyCredentials twitter)]
    (.updateStatus twitter message)))

(defn mymention []
  (lazy-seq (.getMentionsTimeline (mytwitter))))

(defn resentmention [] (first (mymention)))

(defn mentionInfo []
    (let [mentionUser (.getScreenName (.getUser (resentmention)))
          mentionText (str-utils/re-sub #"@syobochim\s" "" (.getText (resentmention)))]
      {:userName mentionUser :text mentionText}))

(def paging
 (Paging. (Integer. 1) (Integer. 5)))

(defn getmytweet []
  (let [twitter (mytwitter)]
     (map #(. %1 getText) (.getUserTimeline twitter))))

(kaiseki/init getmytweet)

(println (kaiseki/create-sentence @kaiseki/*words* (:text (mentionInfo))))
