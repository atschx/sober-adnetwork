(ns sober-adnetwork.routes.auth
  (:require
    [clojure.tools.logging :as log]
    [hiccup.form :refer :all]
    [compojure.core :refer :all]
    [sober-adnetwork.models.users :as u]
    [noir.session :as session]
    [noir.response :as resp]
    [noir.validation :as vali]
    [noir.util.crypt :as crypt]))

(defn handle-registration [user]
;  (if (valid? id pass pass1)
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
;)

(defn handle-login [email pass]
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
  
  (POST "/signup" [& params] 
      (handle-registration params))
  
  (POST "/login" [& params] 
      (handle-login (:email params) (:pass params)))
  
  (GET "/logout" [] 
       (handle-logout)))