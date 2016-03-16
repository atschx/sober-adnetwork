(ns sober-adnetwork.routes.admin
  (:require 
    [compojure.core :refer :all]
    [ring.util.response :as resp]
    [sober-adnetwork.models.users :as u]
    [sober-adnetwork.views.admin 
     [settings :as settings]
     [users :as users]
     [offers :as offers]
     ]))

(defroutes admin-routes
  
  ;;; 用户管理
  (GET "/users" [] (users/user-list))
  (GET "/user/:id/delete" [id]
       (do (u/delete id)
         (resp/redirect "/users")))
  (GET "/user/:id/edit" [id] (users/user-edit id))
  (POST "/user/:id/save" [& params]
        (do (u/update-user params)
          (resp/redirect "/users")))
  
  ;;; offer 审核管理
  (GET "/offers" [] (offers/offer-list))
  
  ;; 系统设置
  (GET "/settings" [] (settings/default-settings)))
