(ns enlive-scrape.core
  (:require [net.cgrand.enlive-html :as html]))

(def url "https://news.ycombinator.com/")

(defn fetch-url [url]
  (html/html-resource (java.net.URL. url)))

(defn get-titles [nodes]
  (map html/text (html/select nodes [:td.title :a.storylink])))

(defn -main []
  (doseq [line (get-titles (fetch-url url))]
    (println line)))
