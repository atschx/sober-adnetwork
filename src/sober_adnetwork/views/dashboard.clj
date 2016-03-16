(ns sober-adnetwork.views.dashboard
  (:use [hiccup.page :refer :all])
  (:require 
    [sober-adnetwork.views.layout :as layout]))

(defn chart-page []
  (layout/common "dashboard (chartpage.js)"
    (layout/nav-bar)
    [:div {:class "container"}
     [:div {:class "row"}
      [:div {:class "span12"} 
			    [:div {:class "page-header"}
				    [:h2 "仪表盘"]
			    ]
		    ]]
     [:div {:class "row"}
     [:div {:class "content"}
      [:div {:class "col-6 col-sm-6 col-lg-4"} [:canvas {:id "myChart1"}]]
      [:div {:class "col-6 col-sm-6 col-lg-4"} [:canvas {:id "myChart2"}]]
      [:div {:class "col-6 col-sm-6 col-lg-4"} [:canvas {:id "myChart3"}]]
      ]]
     ]
    (include-js "/js/chartpage.js")))