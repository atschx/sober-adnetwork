(ns sober-adnetwork.views.admin.offers
  (:use [hiccup.page :refer :all])
  (:require 
    [sober-adnetwork.views.layout :as layout]
    [sober-adnetwork.models.offers :as offers]
    ;;处理表单信息
    [hiccup.core :refer (html)]
    [hiccup.form :as f]
    [noir.util.anti-forgery :as anti-forgery]
    [noir.session :as session]
      ;;
    [cheshire.core :refer :all]
    ))

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
          [:td {:class (condp = status 0 "warning" 1 "success" 2 "danger" "info" )}
          (condp = status
				      0 [:span {:class "label label-warning"} (str "等待审核[" status "]")]
				      1 [:span {:class "label label-success"} (str "已审核通过[" status "]")]
				      2 [:span {:class "label label-danger"} (str "已驳回申请[" status "]")])
           ]
          [:td {} replay]
          [:td {} eff_desc]
          [:td {} advertiser]
          [:td {} updated_at]
          [:td {}
           [:button {:type "button" :class "btn btn-primary btn-xs" :data-toggle "modal" :data-target "#reviewModal"  
                     :data-whatever (generate-string 
                                      {:action "/review/offer"
                                       :review_target id
                                       :review_text   (format "%s(%s)" name id)
                                       })  
                     } "审核"]
          ]
          ]))  

(defn offer-list []
  (layout/common "审核offer"
    (layout/nav-bar)
    [:div {:class "container"}
     [:div {:class "row"}
      [:div {:class "span12"} 
       [:div {:class "page-header"}
        [:h2 "Offer 列表" 
         [:small {} " 管理员审核所有创建的offer"]]
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
          [:th {} "审核状态"]
          [:th {} "管理员回复"]
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
;    (include-js "/js/offer.js")
  ))

