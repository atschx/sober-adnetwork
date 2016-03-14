(ns sober-adnetwork.handler
  
  (:require [compojure.core :refer [defroutes routes]]
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

;(defroutes public-routes
;           (GET "/" [] (views/main-page))
;           (GET "/show/:id" [id] (views/show-post id))
;           (route/resources "/"))
;
;(defroutes protected-routes
;           (GET "/dashboard" [] (views/chart-page))
;           (GET "/admin" [] (views/admin-blog-page))
;           (GET "/admin/add" [] (views/add-post))
;           (POST "/admin/create" [& params]
;             (do (posts/create params)
;                 (resp/redirect "/admin")))
;           (GET "/admin/:id/edit" [id] (views/edit-post id))
;           (POST "/admin/:id/save" [& params]
;             (do (posts/save (:id params) params)
;                 (resp/redirect "/admin")))
;           (GET "/admin/:id/delete" [id]
;             (do (posts/delete id)
;                 (resp/redirect "/admin"))))


(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes 
        home-routes 
        dashboard-routes  
        advertiser-routes 
        publisher-routes
        (wrap-basic-authentication admin-routes authenticated?)
        app-routes)
      (handler/site)
      (wrap-base-url)))

;(defroutes app-routes
;           public-routes
;           (wrap-basic-authentication protected-routes authenticated?)
;           (route/not-found "Not Found"))
;
;(def app
;  (-> app-routes
;      ;      (middleware/wrap-json-body)
;      ;      (middleware/wrap-json-response)
;      (wrap-defaults api-defaults)))
;
;(defn -main
;  [& [port]]
;  (let [port (Integer. (or port
;                           (System/getenv "PORT")
;                           5000))]
;    (jetty/run-jetty #'app {:port  port
;                            :join? false})))
