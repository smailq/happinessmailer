(ns happinessmailer.emails
  (:require [hiccup.core :refer [html]]))

(defmulti morning
  "Morning email."
  (fn [options-map property] property))

(defmethod morning :subject [_ _]
  "Good morning! [something nice]")

(defmethod morning :tags [_ _]
  ["morning"])

(defmethod morning :text/html
  [_ _]
  (html
    [:h4 "I am greatful for ..."]
    [:h4 "What would make today great?"]
    [:h4 "Daily affirmations. I am ..."]))

(defmethod morning :text/plain
  [_ _]
  (clojure.string/join "\n"
    ["I am greatful for ..."
     ""
     "What would make today great?"
     ""
     "Daily affirmations. I am ..."]))
