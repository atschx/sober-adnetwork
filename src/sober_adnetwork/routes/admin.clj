(ns sober-adnetwork.routes.admin
  (:require 
    [compojure.core :refer :all]
    [sober-adnetwork.views.admin 
     [settings :as settings]]))

(defroutes admin-routes
  (GET "/admin" [] (settings/default-settings)))
  
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