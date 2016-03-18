(ns sober-adnetwork.views.admin.apply-list
  (:use [hiccup.page :refer :all])
  (:require 
    [sober-adnetwork.views.layout :as layout]
    [sober-adnetwork.models.apply-list  :as apply-list ]
    [noir.session :as session]
        ;;
    [cheshire.core :refer :all]
    ))

(defn apply-table-item [apply-details]
  (let [id (:id apply-details)
        desc (format "%s-%s-%s-%s" 
                     (:name apply-details) 
                     (:price apply-details) 
                     (:price_model apply-details) 
                     (:clearing_cycle apply-details))
        status (:status apply-details)
        remark (:remark apply-details)
        replay (:replay apply-details)
        updated_at (:updated_at apply-details)]
     [:tr {} 
          [:td {} 
           [:a {:href (str "/apply-list/" id "/detail") } id ]]
          [:td {} desc]
          [:td {:class (condp = status 0 "warning" 1 "success" 2 "danger" "info" )}
          (condp = status
				      0 [:span {:class "label label-warning"} (str "等待审核[" status "]")]
				      1 [:span {:class "label label-success"} (str "已审核通过[" status "]")]
				      2 [:span {:class "label label-danger"} (str "已驳回申请[" status "]")])
           ]
          [:td {} remark]
          [:td {} replay]
          [:td {} updated_at]
          [:td {} 
           [:button {:type "button" :class "btn btn-primary btn-xs" :data-toggle "modal" :data-target "#reviewModal"  
                     :data-whatever (generate-string 
                                      {:action "/review/apply-list"
                                       :review_target id
                                       :review_text   (format "%s(%s)" desc id)
                                       })  
                     } "审核"]
          ]
          ]))  

(defn apply-list []
  (layout/common "我申请的 offer "
    (layout/nav-bar)
    [:div {:class "container"}
     [:div {:class "row"}
       [:div {:class "span12"} 
        [:div {:class "page-header"}
         [:h2 (str "我申请的 Offer ") 
          [:small {} [:a {:href "/marketplaces"} "去市场看看"]]
          ]
        ]
       ]]
     [:div {:class "row"}
      [:div {:class "span12"}
       [:table {:class "table table-striped table-hover table-bordered "}
        [:thead {} 
         [:tr {} 
          [:th {} "申请序号"]
          [:th {} "Offer 简述"]
          [:th {} "审核状态"]
          [:th {} "附加信息"]
          [:th {} "管理员回复"]
          [:th {} "最后更新"]
          [:th {} "更多操作"]
          ]
         ]
        [:tbody {} 
         (map #(apply-table-item %) (apply-list/list))
         ]
        ]]]
     ]
    ))
