(ns happinessmailer.core
  (:require ; Make http requests
            [clj-http.client :as client]
            ; HTTP
            [compojure.core    :refer [GET POST defroutes]]
            [compojure.handler :refer [site]]
            [compojure.route   :as    route]
            ; Environment variables
            [environ.core :refer [env]]
            ; Templating lib
            [hiccup.element :as e]
            [hiccup.page    :as p]
            ; Web server
            [ring.adapter.jetty :as jetty]
            ; Middleware
            [ring.middleware.content-type   :refer [wrap-content-type]]
            [ring.middleware.not-modified   :refer [wrap-not-modified]]
            [ring.middleware.params         :refer [wrap-params]]
            [ring.middleware.resource       :refer [wrap-resource]]
            [ring.middleware.session        :refer [wrap-session]]
            [ring.middleware.session.cookie :refer [cookie-store]]
            ; Various response types
            [ring.util.response :refer [redirect response content-type]]

            ; happinessmailer modules
            [happinessmailer.emails :as emails]))

(defn mandrill-message-send
  "Sends email using Mandrill's REST API.

   content-fn must provide :subject, :tags, :text/html, :text/plain"
  [email-address content-fn & [message-options]]
  (let [message {:from_email "kyu@revress.com"
                 :from_name  "Kyu Lee"
                 :to         [{:email email-address :type "to"}]
                 :subject    (content-fn :subject)
                 :tags       (content-fn :tags)
                 :html       (content-fn :text/html)
                 :text       (content-fn :text/plain)}]
    (client/post "https://mandrillapp.com/api/1.0/messages/send.json"
      {:content-type :json
       :form-params {:key (System/getenv "MANDRILL_API_KEY")
                     :message (merge message message-options)}})))

(defn landing-page
  "landing page"
  []
  (p/html5
    [:head
     [:meta {:charset "utf-8"}]
     [:title "Happiness Mailer"]
     ;(p/include-css "vendor/bootstrap/css/bootstrap.min.css")
     (p/include-css "css/compiled/screen.css")]
    [:body
     [:div "Welcome! Under construction."]]))

(defroutes handler
  (GET "/" [] (landing-page)))

(def app
  "This app is loaded by figwheel on dev environment."
  (-> handler
    (wrap-resource "public") ; Serve files under /public folder
    (wrap-content-type)      ; TODO desc
    (wrap-not-modified)      ; TODO desc
    (wrap-params)            ; TODO desc
    (wrap-session {:store (cookie-store {:key (System/getenv "COOKIE_KEY")})
                   :cookie-attrs {:max-age (* 60 60 24 7)}}))) ; 7 days in sec

; (defn -main [& [port]]
;   (let [port (Integer. (or port (env :port) 5000))]
;     (jetty/run-jetty (site #'app) {:port port :join? false})))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))
