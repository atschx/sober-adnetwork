(ns sober-adnetwork.routes.home
  (:use [hiccup.page :refer :all])
  (:require [compojure.core :refer :all]
            [sober-adnetwork.views.layout :as layout]))

(defn home []
  (layout/common "home"
    (layout/nav-bar)
          (layout/nav-bar)
          [:div {:class "container"}]
          [:div {:class "container"}
           [:div {:class "starter-template"}
            [:h1 {} "Sober Adnetwork"]
            [:p {:class "lead"} "Advertisers use keywords to target the consumers they want while publishers earn more money by selling their traffic to the highest bidders.."
             [:br {:clear "none"}] "Sober Adnetwork is the worldwide leader in targeted advertising for the market."]]]
          [:div {:class "container"}
           [:div {:class "row"}
;            (map #(post-summary-show %) (posts/all))
            ]]))

(defroutes home-routes
  (GET "/" [] (home)))
