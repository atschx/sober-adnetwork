(ns sober-adnetwork.routes.advertiser
  (:require 
    [compojure.core :refer :all]
    [sober-adnetwork.views.advertiser 
     [offer :as offer]]))

(defroutes advertiser-routes
  (GET "/advertiser" [] (offer/offer-list)))
