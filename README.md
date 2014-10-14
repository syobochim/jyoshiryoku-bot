# 女子力bot

[Clojure夜会](http://01e8c979c4e57f83dd63bf3d4a.doorkeeper.jp/events/14626)発表用bot  

# 発表資料はこちら

[女子力のあるsyobochimがClojureはじめてみた](http://syobochim.github.io/LT/20141010_Clojure%E5%A4%9C%E4%BC%9A/#/)

# branch

可読性と引き換えに女子力があがるbranchはこちら

- jyoshiryoku


# 出来る事

- 2分に自分あてのメンションを取得する
- 一番最新のメンションのユーザとメンション内容を取得
- メンション内容から、自分の直近のツイートをもとに文章を作成する(マルコフ連鎖) 
- メンションをくれた人にメンションをかえす


### マルコフ連鎖について

マルコフ連鎖を実装しているkaiseki.cljの内容は、このページからいただいてきました。  

http://takeisamemo.blogspot.jp/2013/12/clojure_13.html
