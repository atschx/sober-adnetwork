(ns sober-adnetwork.routes.home
  (:use [hiccup.page :refer :all])
  (:require [compojure.core :refer :all]
            [ring.util.response :as resp]
            [hiccup.form :refer :all]
            [noir.util.anti-forgery :as anti-forgery]
            [sober-adnetwork.models.users :as u]
            [sober-adnetwork.views.layout :as layout]))

(defn home []
  (layout/home-base "home"
     (layout/nav-static-bar)
     [:div {:class "container"}
      [:div {:class "jumbotron"}
          [:h1 {} "Hello, Sober!"]
          [:p {} "Advertisers use keywords to target the consumers they want while publishers earn more money by selling their traffic to the highest bidders.."]
          [:p {} [:a {:class "btn btn-primary btn-lg" :href "#" :role "button"} "Learn more"]]
          ]
      ]))

(defn signin []
  (layout/home-base "sign-in"
     (layout/nav-static-bar)
     [:div {:class "container"}
      [:form {:class "form-signin" :method "POST" :action "/login"}
       (anti-forgery/anti-forgery-field)
       [:h2 {:class "form-signin-heading"} "会员登录"]
       [:label {:for "inputEmail" :class "sr-only"} "邮箱"]
       [:input {:type "email" :name "email" :id "inputEmail" :class "form-control" :placeholder "Email address" :required "required" :autofocus "autofocus"}]
       [:label {:for "inputPassword" :class "sr-only"} "密码"]
       [:input {:type "password" :name "pass" :id "inputPassword" :class "form-control" :placeholder "Password" :required "required"}]
       [:div {:class "checkbox"}[:label {} [:input {:type "checkbox" :value "remember-me"} "Remember me"]]]
       [:button {:class "btn btn-lg btn-primary btn-block" :type "submit"} "登录"]
      ]
     ]))

(defn signup []
  (layout/home-base "sign-up"
     (layout/nav-static-bar)
     [:div {:class "container"}
      [:form {:class "form-signin" :method "POST" :action "/signup"}
       (anti-forgery/anti-forgery-field)
       [:h2 {:class "form-signin-heading"} "新用户注册"]
       [:input {:type "text" :name "first_name" :id "inputFirstName" :class "form-control" :placeholder "Firstname" :autofocus "autofocus"}]
;       [:label {:for "inputEmail" :class "sr-only"} "邮箱"]
       [:input {:type "text" :name "last_name" :id "inputLastName" :class "form-control" :placeholder "Lastname" }]
       [:label {:for "inputEmail" :class "sr-only"} "邮箱"]
       [:input {:type "email" :name "email" :id "inputEmail" :class "form-control" :placeholder "Email address" :required "required"}]
       [:label {:for "inputPassword" :class "sr-only"} "密码"]
       [:input {:type "password" :name "pass" :id "inputPassword" :class "form-control" :placeholder "Password" :required "required"}]
       [:div {:class "checkbox"}[:label {} [:input {:type "checkbox" :value "agreement"} "I agree to the User Agreement and Privacy Policy."]]]
       [:button {:class "btn btn-lg btn-primary btn-block" :type "submit"} "注册"]
      ]
     ]))

(defroutes home-routes
  (GET "/" [] (home))
  (GET "/signin" [] (signin))
  (GET "/signup" [] (signup))
  (POST "/user/signup" [& params]
        (do (u/create params)
          (resp/redirect "/signin")))
  )




