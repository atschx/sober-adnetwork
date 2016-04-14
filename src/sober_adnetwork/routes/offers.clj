(ns sober-adnetwork.routes.offers
  (:require 
    [compojure.core :refer :all]
    [ring.util.response :as resp]
    [ring.middleware.multipart-params :as mp]
    [noir.session :as session]
    [sober-adnetwork.models.offers :as offers]
    [sober-adnetwork.models.offer-attachments :as offers-attch]
    [sober-adnetwork.views.advertiser 
     [offer :as offer]]
    [sober-adnetwork.views.offer
     [attchment :as offer-attchment]]
    ))


(defroutes offer-routes 
  
  ; 跳转至 offer 的资源列表页面
  (GET "/offer/:offer_id/attchments" [offer_id] 
       (offer-attchment/offer-attchment-list-with-offer-id offer_id))
  
  )
   