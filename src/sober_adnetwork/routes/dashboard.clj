(ns sober-adnetwork.routes.dashboard
  (:require 
    [compojure.core :refer :all]
    [sober-adnetwork.views.dashboard :as dash]))

(defroutes dashboard-routes
  (GET "/dashboard" [] (dash/chart-page)))
