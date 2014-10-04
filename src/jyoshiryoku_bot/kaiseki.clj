(ns jyoshiryoku-bot.kaiseki
  (:require [jyoshiryoku-bot.core :as core])
  (:import (org.atilika.kuromoji Token Tokenizer)))

(defn tokenize [text]
  (let [tokenizer (. (Tokenizer/builder) build)]
    (. tokenizer tokenize text)))
