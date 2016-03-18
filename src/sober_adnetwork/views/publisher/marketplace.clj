(ns sober-adnetwork.views.publisher.marketplace
  (:use [hiccup.page :refer :all])
  (:require 
    [sober-adnetwork.views.layout :as layout]
    [sober-adnetwork.models.offers :as offers]
    ;;处理表单信息
    [hiccup.core :refer (html)]
    [hiccup.form :as f]
    [noir.util.anti-forgery :as anti-forgery]
    [noir.session :as session]
    
    ;; 用于用户申请时 将指定申请请求到页面
    [cheshire.core :refer :all]
    
    ))

;; 这里展示所有已经通过申请的offer 可供登录的非管理员用户申请
;; 查看offer详情，申请offer


(defn offer-table-item [offer]
  (let [id (:id offer)
        name (:name offer)
        cover (:cover offer)
        price (:price offer)
        price_model (:price_model offer)
        clearing_cycle (:clearing_cycle offer)
        status (:status offer)
        replay (:replay offer)
        eff_desc (:eff_desc offer)
        advertiser (:advertiser_id offer)
        updated_at (:updated_at offer)]
     [:tr {} 
          [:td {} 
           [:a {:href (str "/offer/" id "/edit") } id ]]
          [:td {} name]
          [:td {} cover]
          [:td {} price]
          [:td {}
           [:span {:class "label label-info"} price_model]
           ]
          [:td {} clearing_cycle]
          [:td {} eff_desc]
          [:td {} advertiser]
          [:td {} updated_at]
          [:td {}
           [:button {:type "button" :class "btn btn-success btn-xs" :data-toggle "modal" :data-target "#applyModal"  
                     :data-whatever (generate-string 
                                      {:action "/apply/offer"
                                       :apply_target id
                                       :apply_text   (format "%s(%s)" name id)
                                       })  
                     } "申请"]
          ]
          ]))  

(defn marketplace []
  (layout/common "offer 市场"
    (layout/nav-bar)
    [:div {:class "container"}
     [:div {:class "row"}
      [:div {:class "span12"} 
       [:div {:class "page-header"}
        [:h2 "Offer Marketplace" 
         [:small {} " find your offer"]]
       ]
      ]]
;     [:div {:class "row"} 
;      [:button {:type "button" :class "btn btn-default btn-sm" :id "addOffer"}
;       [:span {:class "glyphicon glyphicon-plus" :aria-hidden "true"} "新建"]]
;      ]
;     [:br]
     [:div {:class "row"}
      [:div {:class "span12"}
       [:table {:class "table table-striped table-hover table-bordered "}
        [:thead {} 
         [:tr {} 
          [:th {} "ID"]
          [:th {} "名称"]
          [:th {} "封面"]
          [:th {} "价格"]
          [:th {} "计价模式"]
          [:th {} "结算周期"]
          [:th {} "有效定义"]
          [:th {} "广告主"]
          [:th {} "最后更新"]
          [:th {} "更多操作"]
          ]
         ]
        [:tbody {} 
         (map #(offer-table-item %) (offers/admin-offer-list))
         ]
        ]]]
     ]
  ))

