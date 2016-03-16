(ns sober-adnetwork.routes.auth
  (:use [hiccup.page :refer :all])
  (:require
    [clojure.tools.logging :as log]
    [hiccup.form :refer :all]
    [compojure.core :refer :all]
    [sober-adnetwork.models.users :as u]
    [sober-adnetwork.views.layout :as layout]
    [noir.util.anti-forgery :as anti-forgery]
    [noir.session :as session]
    [noir.response :as resp]
    [noir.validation :as vali]
    [noir.util.crypt :as crypt]))

(defn signin []
  (layout/home-base "sign-in"
     (layout/nav-static-bar)
     [:div {:class "container"}
      [:form {:class "form-signin" :method "POST" :action "/signin"}
       (anti-forgery/anti-forgery-field)
       [:h2 {:class "form-signin-heading"} " 会员登录 " [:small {} [:a {:href "/signup"} "快速注册 "]]]
;       [:label {:for "inputEmail" :class "sr-only"} "邮箱"]
       [:input {:type "email" :name "email" :id "inputEmail" :class "form-control" :placeholder "Email address" :required "required" :autofocus "autofocus"}]
;       [:label {:for "inputPassword" :class "sr-only"} "密码"]
       [:input {:type "password" :name "pass" :id "inputPassword" :class "form-control" :placeholder "Password" :required "required"}]
       [:div {:class "checkbox"}[:label {} [:input {:type "checkbox" :value "remember-me"} "记住我（一周内可直接登录）"]]]
       [:button {:class "btn btn-lg btn-primary btn-block" :type "submit"} "登录"]
      ]
     ]))

(defn signup []
  (layout/home-base "sign-up"
     (layout/nav-static-bar)
     [:div {:class "container"}
      [:form {:class "form-signin" :method "POST" :action "/signup"}
       (anti-forgery/anti-forgery-field)
       [:h2 {:class "form-signin-heading"} "新用户注册 "  [:small {} "已有账号" [:a {:href "/signin"} "登录"]]]
       [:input {:type "text" :name "first_name" :id "inputFirstName" :class "form-control" :placeholder "Firstname" :autofocus "autofocus"}]
       [:input {:type "text" :name "last_name" :id "inputLastName" :class "form-control" :placeholder "Lastname" }]
;       [:label {:for "inputEmail" :class "sr-only"} "邮箱"]
       [:input {:type "email" :name "email" :id "inputEmail" :class "form-control" :placeholder "Email address" :required "required"}]
;       [:label {:for "inputPassword" :class "sr-only"} "密码"]
       [:input {:type "password" :name "pass" :id "inputPassword" :class "form-control" :placeholder "Password" :required "required"}]
       [:div {:class "checkbox"}[:label {} [:input {:type "checkbox" :value "agreement"} (str "我已阅读并同意<a href='#'>用户须知</a>和<a href="#">隐私政策</a>.")]]]
       [:button {:class "btn btn-lg btn-primary btn-block" :type "submit"} "注册"]
      ]
     ]))

(defn handle-signup [user]
    (try        
      (u/create {:first_name (:first_name user)
                 :last_name (:last_name user)
                 :email (:email user)
                 :password (crypt/encrypt (:pass user))})
      (resp/redirect "/signin")
      (catch Exception ex
        (log/error "注册失败" ex)
        (resp/redirect "/signup")))
    )

(defn handle-signin [email pass]
  (log/info (str email "--" pass))
  (let [user (u/get-user-by-email email)] 
    (if (and user (crypt/compare pass (:password user)))
      (do 
        (session/put! :uid (:id user))
        (resp/redirect "/dashboard"))
      (resp/redirect "/signin")
    )))

(defn handle-logout []
  (session/clear!)
  (resp/redirect "/"))

(defroutes auth-routes 
  
  (GET "/signin" [] (signin))
  (POST "/signin" [& params] 
      (handle-signin (:email params) (:pass params)))
  
  (GET "/signup" [] (signup))
  (POST "/signup" [& params] 
      (handle-signup params))
  
  (GET "/logout" [] 
       (handle-logout)))