(ns sober-adnetwork.routes.admin
  (:require 
    [compojure.core :refer :all]
    [ring.util.response :as resp]
    [sober-adnetwork.models.users :as u]
    [sober-adnetwork.views.admin 
     [settings :as settings]
     [users :as users]
     [offers :as offers]
     ]))

(defroutes admin-routes
  (GET "/admin" [] (settings/default-settings))
  (GET "/users" [] (users/user-list))
  (GET "/admin/:id/delete" [id]
       (do (u/delete id)
         (resp/redirect "/users")))
  (GET "/user/:id/edit" [id] (users/user-edit id))
  (POST "/user/:id/save" [& params]
        (do (u/update-user params)
          (resp/redirect "/users")))
  (GET "/admin/offers" [] (offers/offer-list)))
  
;  (GET "/admin" [] (views/admin-blog-page))
;  (GET "/admin/add" [] (views/add-post))
;  (POST "/admin/create" [& params]
;    (do (posts/create params)
;        (resp/redirect "/admin")))
;  (GET "/admin/:id/edit" [id] (views/edit-post id))
;  (POST "/admin/:id/save" [& params]
;    (do (posts/save (:id params) params)
;        (resp/redirect "/admin")))
;  (GET "/admin/:id/delete" [id]
;    (do (posts/delete id)
;        (resp/redirect "/admin"))))