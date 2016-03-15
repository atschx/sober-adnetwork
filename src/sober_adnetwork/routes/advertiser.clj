(ns sober-adnetwork.routes.advertiser
  (:require 
    [compojure.core :refer :all]
    [sober-adnetwork.views.advertiser 
     [offer :as offer]]))

(defroutes advertiser-routes
  (GET "/advertiser" [] (offer/offer-list 10000)))

;; 展示当前advertiser的 offer 列表
;; 添加新的 offer
;; 停用 offer
