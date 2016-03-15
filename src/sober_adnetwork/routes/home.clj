(ns sober-adnetwork.routes.home
  (:use [hiccup.page :refer :all])
  (:require [compojure.core :refer :all]
            [sober-adnetwork.views.layout :as layout]))

(defn home []
  (layout/common "home"
     (layout/nav-static-bar)
     [:div {:class "container"}
      [:div {:class "jumbotron"}
          [:h1 {} "Sober Adnetwork"]
          [:p {} "Advertisers use keywords to target the consumers they want while publishers earn more money by selling their traffic to the highest bidders.."]
          [:p {} [:a {:class "btn btn-primary btn-lg" :href "#" :role "button"} "Learn more"]]
          ]
      ]))

(defn signin []
  (layout/common "sign-in"
     (layout/nav-static-bar)
     [:div {:class "container"}
      [:form {:class "form-signin"}
       [:h2 {:class "form-signin-heading"} "联署会员登录"]
       [:label {:for "inputEmail" :class "sr-only"} "邮箱"]
       [:input {:type "email" :id "inputEmail" :class "form-control" :placeholder "Email address" :required "required" :autofocus "autofocus"}]
       [:label {:for "inputPassword" :class "sr-only"} "密码"]
       [:input {:type "password" :id "inputPassword" :class "form-control" :placeholder "Password" :required "required"}]
       [:div {:class "checkbox"}[:label {} [:input {:type "checkbox" :value "remember-me"} "Remember me"]]]
       [:button {:class "btn btn-lg btn-primary btn-block" :type "submit"} "登录"]
      ]
     ]))

(defn signup []
  (layout/common "sign-up"
     (layout/nav-static-bar)
     [:div {:class "container theme-showcase"}
      [:div {:class "jumbotron"}
          [:h1 {} "Sober Adnetwork"]
          [:p {} "Advertisers use keywords to target the consumers they want while publishers earn more money by selling their traffic to the highest bidders.."]
          [:p {} [:a {:class "btn btn-primary btn-lg" :href "#" :role "button"} "Learn more"]]
          ]
      ]
     ))

(defroutes home-routes
  (GET "/" [] (home))
  (GET "/signin" [] (signin))
  (GET "/signup" [] (signup))
  )
