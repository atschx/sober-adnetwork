(ns sober-adnetwork.routes.admin
  (:require 
    [compojure.core :refer :all]
    [ring.util.response :as resp]
    [sober-adnetwork.models.users :as u]
    [sober-adnetwork.views.admin 
     [settings :as settings]
     [users :as users]
     [offers :as offers]
     ]
    [noir.session :as session]
    ))

(defn handle-review-user
  [params]
  (do (u/update-user (merge params {:updated_by (session/get :uid)}))
      (resp/redirect "/users")))

(defroutes admin-routes
  
  ;;; 用户管理
  (GET "/users" [] (users/user-list))
  (GET "/user/:id/delete" [id]
       (do (u/delete id)
         (resp/redirect "/users")))
  (GET "/user/:id/edit" [id] (users/user-edit id))
  (POST "/user/:id/save" [& params]
        (do (u/update-user (dissoc params :__anti-forgery-token))
          (resp/redirect "/users")))
  
  ;;; offer 审核管理
  (GET "/offers" [] (offers/offer-list))
  
  ;; 系统设置
  (GET "/settings" [] (settings/default-settings))
  
  ;;; user 审核
  (POST "/review/user" [& params] 
        (handle-review-user 
          (dissoc params :__anti-forgery-token)))
  
  )
