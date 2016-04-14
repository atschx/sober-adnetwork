(ns sober-adnetwork.views.offer.attchment
  (:use [hiccup.page :refer :all])
  (:require 
    [sober-adnetwork.views.layout :as layout]
    [sober-adnetwork.models.offer-attachments :as offer-attch]
    ;;处理表单信息
    [hiccup.core :refer (html)]
    [hiccup.form :as f]
    [noir.util.anti-forgery :as anti-forgery]
    [noir.session :as session]
    ))
; offer 相关的页面

;; 资源页面
(defn attchment-table-item [offer-attchment]
  (let [id (:id offer-attchment)
        store_path (:store_path offer-attchment)
        short_url (:short_url offer-attchment)]
     [:tr {} 
          [:td {}  id ]
          [:td {}  store_path ]
          [:td {} 
           [:a {:href short_url :class "btn btn-danger btn-xs" :role "button"} "download" ]
      ]])) 


(defn offer-attchment-list-with-offer-id
  ; 基于 offer_id 单独展示 offer 资源
  [offer_id]
  (layout/common (str "Offer[" offer_id "]'s attchments")
    (layout/nav-bar)
    [:div {:class "container"}
     [:div {:class "row"}
      [:div {:class "span12"} 
       [:div {:class "page-header"}
        [:h2 (str "Offer[" offer_id "]'s attchments") 
         [:small {} " 以下为流量主可选的 offer 资源"]]
       ]
      ]]
;     [:div {:class "row"} 
;      [:button {:type "button" :class "btn btn-default btn-sm" :id "addOffer"}
;       [:span {:class "glyphicon glyphicon-plus" :aria-hidden "true"} "新建"]]
;      ]
     [:div {:class "row"}
      [:div {:class "span12"}
       [:table {:class "table table-striped table-hover table-bordered "}
        [:thead {} 
         [:tr {} 
          [:th {} "ID"]
          [:th {} "存储路径"]
          [:th {} "资源连接"]
          ]
         ]
        [:tbody {} 
         (map #(attchment-table-item %) (offer-attch/offer-attachements-list offer_id))
         ]
        ]]]
     ]
  ))