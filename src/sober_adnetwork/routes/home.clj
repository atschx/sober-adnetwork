(ns sober-adnetwork.routes.home
  (:use [hiccup.page :refer :all])
  (:require [compojure.core :refer :all]
            [sober-adnetwork.views.layout :as layout]))

(defn home []
  (layout/common "home"
    (layout/nav-bar)
          (layout/nav-bar)
          [:div {:class "container"}
           [:div {:class "jumbotron"}
               [:h1 {} "Sober,Adnetwork"]
               [:p {} "Advertisers use keywords to target the consumers they want while publishers earn more money by selling their traffic to the highest bidders.."]
               [:p {} [:a {:class "btn btn-primary btn-lg" :href "#" :role "button"} "Learn more"]]
               ]]
        ))

(defroutes home-routes
  (GET "/" [] (home)))
