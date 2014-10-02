(ns jyoshiryoku-bot.core
  (:import [twitter4j TwitterFactory Twitter]))

(defn userinfo [message]
  (let [twitter (.getInstance (TwitterFactory.)) , user (.verifyCredentials twitter)]
    (.updateStatus twitter message)
    ))
