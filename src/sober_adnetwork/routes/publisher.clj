(ns sober-adnetwork.routes.publisher  
  (:require 
    [compojure.core :refer :all]
    [ring.util.response :as resp]
    [noir.session :as session]
    [sober-adnetwork.models.apply-list :as apply-list]
    [sober-adnetwork.views.publisher 
     [my-apply-list :as my-apply]
     [marketplace :as market]
     ]
    ))

(defn handle-apply-offer
  [params]
  (do (apply-list/create (dissoc (merge params {
                                             :offer_id (:id params)
                                             :publisher_id (session/get :uid)
                                             :created_by (session/get :uid) 
                                             :updated_by (session/get :uid)}) :id))
      (resp/redirect "/publisher")))

(defroutes publisher-routes
 
  ;; 流量主 首页看到自己申请的 offer列表
  (GET "/publisher" [] 
    (if-let [uid (session/get :uid)]
;      (apply/offer-apply-list)
      (my-apply/my-apply-list)
      (resp/redirect "/signin")))
  
  ;; offer 市场 流量主可以申请
  (GET "/marketplaces"  
       [] (market/marketplace))
  
  ;; 流量主申请 Offer
  (POST "/apply/offer" [& params] 
        (handle-apply-offer 
          (dissoc params :__anti-forgery-token)))
  
  ;; 流量主取消申请
  (GET "/apply-list/:id/delete" [id]
     (do (apply-list/delete id)
       (resp/redirect "/publisher")))
   
  )