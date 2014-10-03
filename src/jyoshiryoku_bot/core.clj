(ns jyoshiryoku-bot.core
  (:import [twitter4j TwitterFactory Twitter]))

(defn tweettimeline [message]
  (let [twitter (.getInstance (TwitterFactory.)) , user (.verifyCredentials twitter)]
    (.updateStatus twitter message)
    ))

(defn mymentionlist []
  (.getMentionsTimeline (.getInstance (TwitterFactory.))))

(defn getmymention []
  (let [twitter (.getInstance (TwitterFactory.))]
    (mymentionlist)
    ))

