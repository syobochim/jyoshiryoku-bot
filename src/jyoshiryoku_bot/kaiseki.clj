(ns jyoshiryoku-bot.kaiseki
  (:import (org.atilika.kuromoji Token Tokenizer)))

(defn tokenize [text]
  (let [tokenizer (. (Tokenizer/builder) build)]
    (. tokenizer tokenize text)))

(defn token-word [token]
  (.trim (.getSurfaceForm token)))


(def ^:dynamic *words*
  "マルコフ連鎖のモデル
次のキーと値のマップ
キー: 形態素
値: キーを次の形態素、値を出現数とするマップ"
  (ref {}))

(defn inc-map-value [m k]
  (if (get m k)
    (update-in m [k] inc)
    (assoc m k 1)))

(defn register-word [m word1 word2]
  (let [word2-map (get m word1 {})]
    (assoc m word1 (inc-map-value word2-map word2))))

(defn load-text [text]
  (let [text text
        tokens (tokenize text)]
    (reduce (fn [m [token1 token2]]
              (let [word1 (token-word token1)
                    word2 (token-word token2)]
                (if (or (= word1 ""))
                  m
                  (register-word m word1 word2))))
            {} (partition 2 1 tokens))))

(defn select-word [word-map]
  (first (rand-nth (seq word-map))))

(defn select-next-word [word-map word]
  (let [next-word-map (get word-map word)]
    (select-word next-word-map)))

(defn create-sentence [word-map word]
  (loop [sentence ""
         word word]
;    (println (str "*" word))
    (if word
      (if (= word "\"")
        sentence
        (recur (str sentence word) (select-next-word word-map word)))
      sentence)))

(defn init [tweetmap]
  (dosync
   (ref-set *words*
            (load-text (apply pr-str (tweetmap))))))
