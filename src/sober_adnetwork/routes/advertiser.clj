(ns sober-adnetwork.routes.advertiser
  (:require 
    [compojure.core :refer :all]
    [ring.util.response :as resp]
    [noir.session :as session]
    [sober-adnetwork.models.offers :as offers]
    [sober-adnetwork.views.advertiser 
     [offer :as offer]]))

;;; 不变性 很重要!!!
;(def current-uid (delay (session/get :uid)))
;;; 读取变量中的值不一定正确
;@current-uid 

(defroutes advertiser-routes
  
  ;; 当前advertiser 已创建的 offer 列表
  (GET "/advertiser" [] 
       (if-let [uid (session/get :uid)]
         (offer/offer-list uid)
          (resp/redirect "/signin")
         ))
  
  ;; 添加新的 offer
  (GET "/offer/add" [] (offer/add-offer))
  
  ;; 移除crsf的token，增加当前操作人
  (POST "/offer/create" [& params]
        (do 
          (offers/create 
            (dissoc 
              (merge params {:created_by (session/get :uid) :updated_by (session/get :uid)}) :__anti-forgery-token))
          (resp/redirect "/advertiser")))
  
  ;; offer的创建者可以删除 offer
  (GET "/offer/:id/delete" [id]
     (do (offers/delete id)
       (resp/redirect "/advertiser"))))

