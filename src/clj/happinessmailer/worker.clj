(ns happinessmailer.worker
  (:require [happinessmailer.core   :as core]
            [happinessmailer.emails :as emails]))

(defn -main []
  (core/mandrill-message-send
    "smailq@gmail.com"
    (partial emails/morning {})))
