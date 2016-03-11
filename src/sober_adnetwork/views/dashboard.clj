(ns sober-adnetwork.views.dashboard
  (:use [hiccup.page :refer :all])
  (:require 
    [sober-adnetwork.views.layout :as layout]))

(defn chart-page []
  (layout/common "dashboard (chartpage.js)"
    (layout/nav-bar)
    [:div {:class "container"}
     [:div {:class "content"}
      [:h1 "chart (chartjs)"]
      [:div {:class "col-6 col-sm-6 col-lg-4"} [:canvas {:id "myChart1"}] [:hr]]
      [:div {:class "col-6 col-sm-6 col-lg-4"} [:canvas {:id "myChart2"}] [:hr]]
      [:div {:class "col-6 col-sm-6 col-lg-4"} [:canvas {:id "myChart3"}] [:hr]]
      ]]
    (include-js "/js/chartpage.js")))