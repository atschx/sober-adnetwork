(ns sober-adnetwork.views.advertiser.offer
  (:use [hiccup.page :refer :all])
  (:require 
    [sober-adnetwork.views.layout :as layout]
    [sober-adnetwork.models.offers :as offers]
    ;;处理表单信息
    [hiccup.core :refer (html)]
    [hiccup.form :as f]
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
           [:a {:href (str "/offer/" id "/edit") } id ]]
          [:td {} name]
          [:td {} cover]
          [:td {} price]
          [:td {}
           [:span {:class "label label-info"} price_model]
           ]
          [:td {} clearing_cycle]
          [:td {}
           ;;逻辑 判定
           [:span {:class "label label-warning"} (str "等待审核[" status "]")]
           ]
          [:td {} replay]
          [:td {} eff_desc]
          [:td {} updated_at]
          [:td {} 
           [:a {:href (str "/offer/" id "/delete") :class "btn btn-danger btn-xs" :role "button"} "删除" ]
          ]
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
      [:button {:type "button" :class "btn btn-default btn-sm" :id "addOffer"}
       [:span {:class "glyphicon glyphicon-plus" :aria-hidden "true"} "新建"]]
      ]
     [:br]
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
          [:th {} "更多操作"]
          ]
         ]
        [:tbody {} 
         (map #(offer-table-item %) (offers/offer-list advertiser-id))
         ]
        ]]]
     ]
;    (include-js "/js/offer.js")
  ))

(defn add-offer [] 
  (layout/common  "create new offer"
    (layout/nav-bar)
    [:div {:class "container"}
     [:div {:class "row"}
      [:div {:class "span12"} 
			    [:div {:class "page-header"}
				    [:h2 "创建新 offer"]
			    ]
		    ]]
     [:div {:class "row"}
       [:div {:class "col-lg-6"}
       (list
           (f/form-to {:class "form-horizontal" :role "form"} 
                      [:post "/offer/create"]
                      (f/hidden-field "advertiser_id" "10000")
                      [:div {:class "form-group"}
                       (f/label {:class "col-sm-2 control-label"} "name" "名称")
                       [:div {:class "col-sm-10"} (f/text-field {:class "form-control" :placeholder "请输入 offer 名称" :autofocus "autofocus"} "name")]]
                      [:div {:class "form-group"}
                       (f/label {:class "col-sm-2 control-label"} "price" "价格")
                       [:div {:class "col-sm-10"} [:input {:class "form-control" :name "price" :type "number" :placeholder "人民币计价"}]]]
                      [:div {:class "form-group"}
                       (f/label {:class "col-sm-2 control-label"} "price_model" "计价模式")
                       [:div {:class "col-sm-10"} 
                        [:lable {:class "radio-inline" :for "price_model_1"} 
                         [:input {:type "radio" :name "price_model" :value "CPA" :id "price_model_1" :checked "checked"}]"CPA"
                         ]
                        [:lable {:class "radio-inline" :for "price_model_2"} 
                         [:input {:type "radio" :name "price_model" :value "CPS" :id "price_model_2"}]"CPS"
                         ]
                        ]]
                      [:div {:class "form-group"}
                       (f/label {:class "col-sm-2 control-label"} "clearing_cycle" "结算周期")
                       [:div {:class "col-sm-10"} 
                        [:lable {:class "radio-inline" :for "clearing_cycle_1"} 
                         [:input {:type "radio" :name "clearing_cycle" :value "WEEK" :id "clearing_cycle_1" :checked "checked"}]"周结"
                         ]
                        [:lable {:class "radio-inline" :for "clearing_cycle_2"} 
                         [:input {:type "radio" :name "clearing_cycle" :value "CPS" :id "clearing_cycle_2"}]"月结"
                         ]
                        ]]
                      [:div {:class "form-group"}
                       (f/label {:class "col-sm-2 control-label"} "eff_desc" "有效定义")
                       [:div {:class "col-sm-10"} (f/text-area {:rows 3 :class "form-control"} "eff_desc")]]
                      [:div {:class "form-group"}
                       [:div {:class "col-sm-offset-2 col-sm-10"}
                      (f/submit-button {:class "btn btn-primary"} "保 存")]]
                      )
           )]]
     ])
  )