(ns sober-adnetwork.routes.publisher  
  (:require 
    [compojure.core :refer :all]
    [sober-adnetwork.views.publisher 
     [apply :as apply]]))

(defroutes publisher-routes
  (GET "/publisher" [] (apply/offer-apply-list)))