(ns sober-adnetwork.routes.home
  (:use [hiccup.page :refer :all])
  (:require [compojure.core :refer :all]
;            [ring.util.response :as resp]
            [sober-adnetwork.views.layout :as layout]))

(defn home []
  (layout/home-base "home"
     (layout/nav-static-bar)
     [:div {:class "container"}
      [:div {:class "jumbotron"}
          [:h1 {} "Hello, Sober!"]
          [:p {} "Advertisers use keywords to target the consumers they want while publishers earn more money by selling their traffic to the highest bidders.."]
          [:p {} [:a {:class "btn btn-primary btn-lg" :href "/help.html" :role "button"} "Learn more"]]
          ]
      ]))

(defroutes home-routes
  (GET "/" [] (home)))




