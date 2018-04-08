(ns enlive-scrape.core
  (:require [net.cgrand.enlive-html :as html])
  (:gen-class))

(def url "https://news.ycombinator.com/")

(defn fetch-url [url]
  (html/html-resource (java.net.URL. url)))

(defn get-titles [nodes]
  (map html/text (html/select nodes [:td.title :a.storylink])))

;; IO
(defn print-stories []
  (def i (atom 0))
  (doseq [story (map #(str (swap! i inc) "\t" %)
                     (-> url fetch-url get-titles))]
    (println story)))

(defn prompt []
  (newline)
  (println "Enter r to refresh or q to quit:")
  (let [cmd (read-line)]
    (cond (= 0 (compare "r" cmd)) (do (newline) (print-stories) (prompt))
          (= 0 (compare "q" cmd)) (do (newline) (println "Bye!"))
          :else (do (prompt)))))

(defn -main []
  (print-stories)
  (prompt))
