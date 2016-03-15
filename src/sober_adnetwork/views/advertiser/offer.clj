(ns sober-adnetwork.views.advertiser.offer
  (:use [hiccup.page :refer :all])
  (:require 
    [sober-adnetwork.views.layout :as layout]
    [sober-adnetwork.models.offers :as offers]
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
        updated_at (:updated_at offer)]
     [:tr {} 
          [:td {} 
           [:a {:href (str "/offer/" id "/edit") :class "btn btn-info btn-xs" :role "button"} "id" ]]
          [:td {} name]
          [:td {} cover]
          [:td {} price]
          [:td {} price_model]
          [:td {} clearing_cycle]
          [:td {}
           ;;逻辑 判定
           [:span {:class "label label-warning"} (str "等待审核[" status "]")]
           ]
          [:td {} replay]
          [:td {} eff_desc]
          [:td {} updated_at]
          ]))  

(defn offer-list [advertiser-id]
  (layout/common (str advertiser-id "'s offer")
    (layout/nav-bar)
    [:div {:class "container"}
     [:div {:class "row"}
      [:div {:class "span12"} 
       [:div {:class "page-header"}
        [:h2 "我创建的 offer"]
       ]
      ]]
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
          [:th {} "最后更新"]
          ]
         ]
        [:tbody {} 
         (map #(offer-table-item %) (offers/offer-list advertiser-id))
         ]
        ]]]
     ]
;    (include-js "/js/chartpage.js"))
  ))
