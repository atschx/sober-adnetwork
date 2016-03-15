(ns sober-adnetwork.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.adapter.jetty :as jetty]
            [clojure.tools.logging :as log]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ring.middleware.basic-authentication :refer :all]
            [hiccup.middleware :refer [wrap-base-url]]
            ;;
            [compojure.handler :as handler]
            [compojure.route :as route]
            ;; routes
            [sober-adnetwork.routes 
             [home :refer [home-routes]]
             [dashboard :refer [dashboard-routes]]
             [advertiser :refer [advertiser-routes]]
             [publisher :refer [publisher-routes]]
             [admin :refer [admin-routes]]
             ]))

(defn init []
  (log/info "sober-adnetwork is starting"))

(defn destroy []
  (log/info "sober-adnetwork is shutting down"))

(defn authenticated? [name pass]
  (and (= name "admin")
       (= pass "admin123")))


(defroutes app-routes
  (route/resources "/")
  home-routes 
  (wrap-basic-authentication dashboard-routes authenticated?)
  (wrap-basic-authentication advertiser-routes authenticated?)
  (wrap-basic-authentication publisher-routes authenticated?)
  (wrap-basic-authentication admin-routes authenticated?)
  (route/not-found "Not Found"))

(def app
  (-> (routes 
        app-routes)
      (handler/site)
      (wrap-base-url)))

(defn -main
  [& [port]]
  (let [port (Integer. (or port
                           (System/getenv "PORT")
                           5000))]
    (jetty/run-jetty #'app {:port  port
                            :join? false})))
