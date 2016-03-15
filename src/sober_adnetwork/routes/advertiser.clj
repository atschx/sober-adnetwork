(ns sober-adnetwork.routes.advertiser
  (:require 
    [compojure.core :refer :all]
    [ring.util.response :as resp]
    [sober-adnetwork.models.offers :as offers]
    [sober-adnetwork.views.advertiser 
     [offer :as offer]]))

(defroutes advertiser-routes
  (GET "/offer/add" [] (offer/add-offer))
  (POST "/offer/create" [& params]
        (do (offers/create params)
          (resp/redirect "/advertiser")))
  (GET "/offer/:id/delete" [id]
     (do (offers/delete id)
       (resp/redirect "/advertiser")))
  (GET "/advertiser" [] (offer/offer-list 10000)))

;; 展示当前advertiser的 offer 列表
;; 添加新的 offer
;; 停用 offer
