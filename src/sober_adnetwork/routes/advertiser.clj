(ns sober-adnetwork.routes.advertiser
  (:require 
    [compojure.core :refer :all]
    [ring.util.response :as resp]
    [ring.middleware.multipart-params :as mp]
    [noir.session :as session]
    [sober-adnetwork.models.offers :as offers]
    [sober-adnetwork.models.offer-attachments :as offers-attch]
    [sober-adnetwork.views.advertiser 
     [offer :as offer]]
    ))

;;; 不变性 很重要!!!
;(def current-uid (delay (session/get :uid)))
;;; 读取变量中的值不一定正确
;@current-uid 

(defroutes advertiser-routes
  
  ;; 当前advertiser 已创建的 offer 列表
  (GET "/advertiser" [] 
       (if-let [uid (session/get :uid)]
         (offer/offer-list uid)
          (resp/redirect "/signin")
         ))
  
  ;; 添加新的 offer
  (GET "/offer/add" [] (offer/add-offer))
  
  ;; 移除crsf的token，增加当前操作人
  (POST "/offer/create" [& params]
        (do 
          (offers/create 
            (dissoc 
              (merge params {:created_by (session/get :uid) :updated_by (session/get :uid)}) :__anti-forgery-token))
          (resp/redirect "/advertiser")))
  
  ;; offer的创建者可以删除 offer
  (GET "/offer/:id/delete" [id]
     (do (offers/delete id)
       (resp/redirect "/advertiser")))
  
  ;; offer 上传相关附件资源
  (GET "/offer/:id/upload" [id] (offer/upload-attch id))
  
  (mp/wrap-multipart-params 
    (POST "/offer/:id/upload" {params :params multi-params :multipart-params} 
         (println (str "原生请求参数：" params))
         (println (str "offer_id:" (:id params)))
         (println (str "offer_attch:" (dissoc params :__anti-forgery-token)))
;         (apply map vector (get-in params [:files]))
         (println (count  (:files params)))
         ;; 此处需要进行类型判断
         (println (type  (:files params)))

;         (println (str (get-in params [:files])))
         ; clojure.lang.PersistentArrayMap
;         (map )
          (offers-attch/restore-attachements (assoc (first (:files params)) :offer_id (:id params)))
          
;         (map #(offers-attch/restore-attachements %) (apply map vector (get-in params [:files])))
;         (map #(str "Hello " % "!" ) ["Ford" "Arthur" "Tricia"])
;         (map #(str "Hello " % "!" ) (apply map vector (:files params)))
;         (map #(offers-attch/restore-attachements %) (:files params))
;         (map #(offer-table-item %) (offers/offer-list advertiser-id))
;         (offers-attch/restore-attachements (first  (map #(merge % :offer_id (:id params)) (:files params))))
;         (map #(offers-attch/restore-attachements %) (map #(merge % {:offer_id (:id params)}) (:files params)))
         
         ; 文件重命名
         (resp/redirect "/advertiser"))
    )
  
  
  )

