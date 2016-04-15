(ns sober-adnetwork.routes.admin
  (:require 
    [compojure.core :refer :all]
    [ring.util.response :as resp]
    [sober-adnetwork.models.users :as u]
    [sober-adnetwork.models.offers :as offer]
    [sober-adnetwork.models.apply-list :as apply-list]
    [sober-adnetwork.views.admin 
     [settings :as settings]
     [users :as users]
     [offers :as offers]
     [apply-list :as apply]
     ]
    [noir.session :as session]
    ))

(defn handle-review-user
  [params]
  (do (u/update-user (merge params {:updated_by (session/get :uid)}))
      (resp/redirect "/users")))

(defn handle-review-offer
  [params]
  (do (offer/update-offer (merge params {:updated_by (session/get :uid)}))
      (resp/redirect "/offers")))

(defn handle-review-apply-list
  [params]
  (do (apply-list/update-apply-list (merge params {:updated_by (session/get :uid)}))
      ; 审核通过 
      (resp/redirect "/apply-list")))

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
  
  ;; 流量主申请页面
  (GET "/apply-list" [] (apply/apply-list))
  
  ;; 系统设置
  (GET "/settings" [] (settings/default-settings))
  
  ;;; user 审核
  (POST "/review/user" [& params] 
        (handle-review-user 
          (dissoc params :__anti-forgery-token)))
  
  ;;; offer 审核
  (POST "/review/offer" [& params] 
        (handle-review-offer 
          (dissoc params :__anti-forgery-token)))
  
  ;;; 流量主申请 审核
  (POST "/review/apply-list" [& params] 
        (handle-review-apply-list
          (dissoc params :__anti-forgery-token)))
  
  )
