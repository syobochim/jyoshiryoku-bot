(ns jyoshiryoku-bot.core
  (:import [twitter4j TwitterFactory Twitter Paging]))

(defn mytwitter []
  (.getInstance (TwitterFactory.)))

(defn tweettimeline [message]
  (let [twitter (mytwitter) , user (.verifyCredentials twitter)]
    (.updateStatus twitter message)
    ))

(defn mymentionlist []
  (.getMentionsTimeline (mytwitter)))

(defn getmytweet []
  (let [twitter (mytwitter)]
    (map #(. %1 getText) (.getUserTimeline twitter (Paging. (Integer. 1) (Integer. 200))))))
