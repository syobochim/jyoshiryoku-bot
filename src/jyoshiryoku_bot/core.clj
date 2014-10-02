(ns jyoshiryoku-bot.core
  (:import [twitter4j TwitterFactory Twitter]))

(defn userinfo []
  (let [twitter (.getInstance (TwitterFactory.)) , user (.verifyCredentials twitter)]
    (.getName user)
    (.getScreenName user)))

(userinfo)
