(ns sober-adnetwork.views.publisher.my-apply-list
  (:use [hiccup.page :refer :all])
  (:require 
    [sober-adnetwork.views.layout :as layout]
    [sober-adnetwork.models.apply-list  :as apply-list ]
    [noir.session :as session]
        ;;
    [cheshire.core :refer :all]
    ))

;; 当前用户的申请列表 按照申请度排名（后期可以选择性催促管理员审核）

;; 展示字段（
;; 1. 申请序号（apply_id） 
;; 2. offer名称（超链接到 offer 详情）
;; 3. 审核状态
;; 4. 管理员回复
;; 5. 获取附加资源（下载资源包）

;    "remark": "qqqqq",
;    "price_model": "CPA",
;    "enable": true,
;    "offer_id": 1002,
;    "clearing_cycle": "WEEK",
;    "name": "映客",
;    "replay": null,
;    "updated_at": "2016-03-18T07:29:03Z",
;    "publisher_id": 10001,
;    "status": 0,
;    "id": 1,
;    "updated_by": 10001,
;    "price": 3.0,
;    "created_by": 10001,
;    "created_at": "2016-03-18T07:29:03Z"

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
           [:a {:href (str "/apply-list/" id "/delete") :class "btn btn-danger btn-xs" :role "button"} "取消申请" ]
          ]
          ]))  

(defn my-apply-list []
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
         (map #(apply-table-item %) (apply-list/my-apply-list (session/get :uid)))
         ]
        ]]]
     ]
    ))

