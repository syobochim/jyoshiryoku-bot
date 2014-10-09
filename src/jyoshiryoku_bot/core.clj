(ns jyoshiryoku-bot.core
  (:import [twitter4j TwitterFactory Twitter Paging])
  (:require [clojure.contrib.str-utils :as str-utils]
            [clojure.java.io :as io]
            [jyoshiryoku-bot.kaiseki :as kaiseki])
  (:gen-class))

(defn mytwitter []
  (.getInstance (TwitterFactory.)))

(defn tweettimeline [message]
  (let [twitter (mytwitter)]
    (.updateStatus twitter message)))

(defn mymention []
  (lazy-seq (.getMentionsTimeline (mytwitter))))

(defn resentmention [] (first (mymention)))

(defn mentionInfo []
  (let [mention (resentmention)
        mentionUser (.getScreenName (.getUser mention))
        mentionText (str-utils/re-sub #"(@.*?\s)+" "" (.getText mention))
        mentionId (.getId mention)]
    {:userName mentionUser :text mentionText :id mentionId}))

(defn searchword []
  (kaiseki/token-word
   (first (kaiseki/tokenize (:text (mentionInfo))))))

(def paging
 (Paging. (int 1) (int 50)))

(defn getmytweet []
  (let [twitter (mytwitter)]
     (map #(. %1 getText) (.getUserTimeline twitter paging))))

(defn -main []
  (with-open [fout (io/writer "tweet.txt" :append true)]
    (.write fout (apply pr-str (getmytweet))))
  (kaiseki/init "tweet.txt")
  (let [info (atom (mentionInfo))]
    (while true
      (if-not (= @info (mentionInfo))
        ((reset! info (mentionInfo))
         (tweettimeline (str ".@" (:userName @info) " "
                            (kaiseki/create-sentence @kaiseki/*words* (searchword))))))
      (Thread/sleep (* 1000 60 2)))))
