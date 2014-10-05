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
    (let [mention (resentmention)
          mentionUser (.getScreenName (.getUser mention))
          mentionText (str-utils/re-sub #"@syobochim\s" "" (.getText mention))
          mentionId (.getId mention)]
      {:userName mentionUser :text mentionText :id mentionId}))

(def paging
 (Paging. (Integer. 1) (Integer. 20)))

(defn getmytweet []
  (let [twitter (mytwitter)]
     (map #(. %1 getText) (.getUserTimeline twitter))))

(defn -main []
  (kaiseki/init getmytweet)
  (when true
    (let [info mentionInfo]
      (tweettimeline (str "@" (:userName info) " "
                          (kaiseki/create-sentence @kaiseki/*words* (:text info))))))
    (Thread/sleep (* 1000 60 10)))
