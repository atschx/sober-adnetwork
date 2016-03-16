(ns sober-adnetwork.routes.publisher  
  (:require 
    [compojure.core :refer :all]
    [ring.util.response :as resp]
    [noir.session :as session]
    [sober-adnetwork.views.publisher 
     [apply :as apply]]))

(defroutes publisher-routes
 
  ;; 流量主 首页看到自己申请的 offer列表
  (GET "/publisher" [] 
    (if-let [uid (session/get :uid)]
      (apply/offer-apply-list)
      (resp/redirect "/signin")))
  
  )