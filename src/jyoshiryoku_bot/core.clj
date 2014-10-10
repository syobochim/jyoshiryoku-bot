(ns jyoshiryoku-bot.core
  (:import [twitter4j TwitterFactory Twitter Paging])
  (:require [clojure.contrib.str-utils :as str-utils]
            [clojure.java.io :as io]
            [jyoshiryoku-bot.kaiseki :as kaiseki])
  (:gen-class))

(defn •ॢ◡-ॢ []
  (.getInstance (TwitterFactory.)))

(defn tweettimeline [message]
  (let [twitter (•ॢ◡-ॢ)]
    (.updateStatus twitter message)))

(defn ∩ˇωˇ∩ []
  (lazy-seq (.getMentionsTimeline (•ॢ◡-ॢ))))

(defn ฅ'ω'ฅ [] (first (∩ˇωˇ∩)))

(defn ╹◡╹ []
  (let [mention (ฅ'ω'ฅ)
        mentionUser (.getScreenName (.getUser mention))
        mentionText (str-utils/re-sub #"(@.*?\s)+" "" (.getText mention))
        mentionId (.getId mention)]
    {:userName mentionUser :text mentionText :id mentionId}))

(defn ºωº []
  (kaiseki/token-word
   (first (kaiseki/tokenize (:text (╹◡╹))))))

(def paging
 (Paging. (int 1) (int 50)))

(defn ⑅ˊᵕˋ⑅ []
  (let [twitter (•ॢ◡-ॢ)]
     (map #(. %1 getText) (.getUserTimeline twitter paging))))

(defn -main []
  (with-open [fout (io/writer "tweet.txt" :append true)]
    (.write fout (apply pr-str (⑅ˊᵕˋ⑅))))
  (kaiseki/init "tweet.txt")
  (let [info (atom (╹◡╹))]
    (while true
      (if-not (= @info (╹◡╹))
        ((reset! info (╹◡╹))
         (tweettimeline (str ".@" (:userName @info) " "
                            (kaiseki/create-sentence @kaiseki/*words* (ºωº))))))
      (Thread/sleep (* 1000 60 1)))))
