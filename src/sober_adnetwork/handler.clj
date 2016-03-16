(ns sober-adnetwork.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.adapter.jetty :as jetty]
            [clojure.tools.logging :as log]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ring.middleware.basic-authentication :refer :all]
            [hiccup.middleware :refer [wrap-base-url]]
            [noir.util.middleware :as noir-middleware]
            ;;
            [compojure.handler :as handler]
            [compojure.route :as route]
            ;; routes
            [sober-adnetwork.routes 
             [auth :refer [auth-routes]]
             [home :refer [home-routes]]
             [dashboard :refer [dashboard-routes]]
             [advertiser :refer [advertiser-routes]]
             [publisher :refer [publisher-routes]]
             [admin :refer [admin-routes]]
             ]))

(defn init []
  (log/info "sober-adnetwork is starting ..."))

(defn destroy []
  (log/info "sober-adnetwork is shutting down.."))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app (noir-middleware/app-handler
           [auth-routes
            home-routes 
					  dashboard-routes
					  advertiser-routes
					  publisher-routes
					  admin-routes
            app-routes]
           ))

;(def app
;  (-> (routes 
;        auth-routes
;        home-routes 
;        dashboard-routes
;        app-routes)
;      (handler/site)
;      (wrap-base-url)))

(defn -main
  [& [port]]
  (let [port (Integer. (or port
                           (System/getenv "PORT")
                           5000))]
    (jetty/run-jetty #'app {:port  port
                            :join? false})))
