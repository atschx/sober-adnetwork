(ns sober-adnetwork.views.layout
  (:use [hiccup.page :refer :all])
  (:require [noir.session :as session]
            [hiccup.form :as f]
            [noir.util.anti-forgery :as anti-forgery]
            ))

(defn home-base [title & content]
  (html5
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:http-equiv "X-UA-Compatible", :content "IE=edge"}]
     [:meta {:name "viewport", :content "width=device-width, initial-scale=1"}]
     [:meta {:name "description", :content "a network of publisher and advertiser"}] 
     [:meta {:name "author", :content "albert"}]
     [:title (format "sober-adnetwork:%s" title)]
     (include-css "/css/bootstrap.min.css" "/css/bootstrap-theme.min.css" "/css/sober.css")
     (include-js "/js/json2.js" "/js/xpath.js" "/js/Chart.js")]
    [:body content
     [:div {:class "container"}
      [:hr {}] 
      [:footer {} 
       [:p {} "©2016 cia.im"]]]
     (include-js "/js/jquery.js" "/js/bootstrap.min.js" "/js/sober.js")]))

(defn common [title & content]
  (html5
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:http-equiv "X-UA-Compatible", :content "IE=edge"}]
     [:meta {:name "viewport", :content "width=device-width, initial-scale=1"}]
     [:meta {:name "description", :content "a network of publisher and advertiser"}] 
     [:meta {:name "author", :content "albert"}]
     [:title (format "sober-adnetwork:%s" title)]
     (include-css "/css/bootstrap.min.css" "/css/bootstrap-theme.min.css" "/css/bootstrap-switch.min.css" "/css/sober-admin.css")
     (include-js "/js/json2.js" "/js/xpath.js" "/js/Chart.js")]
    [:body content
     [:div {:class "container"}
      [:hr {}] 
      [:footer {} 
       [:p {} "©2016 cia.im"]]]
     ;; 统一设置审核窗口
     [:div {:class "modal fade" :id "reviewModal" :tabindex "-1" :role "dialog" :aria-labelledby "reviewModalLabel"}
      [:div {:class "modal-dialog" :role "document"} 
       [:div {:class "modal-content"}
        [:div {:class "modal-header"}
         [:button {:type "button" :class "close" :data-dismiss "modal" :aria-label "Close"} [:span {:aria-hidden "true"} "&times;"]]
         [:h4 {:class "modal-title" :id "reviewModalLabel"} "管理员审核"]
         ]
        [:div {:class "modal-body"}
         [:form {:id "review-form" :method "POST"}
	         (anti-forgery/anti-forgery-field)
           [:div {:class "form-group"}
	               (f/label {:class "control-label"} "review-status" "审核结果：")
                 [:div {} 
                   [:lable {:class "radio-inline" :for "review_status_1"} 
                    [:input {:type "radio" :name "status" :value "1" :id "review_status_1" :checked "checked"}] "同意"]
                   [:lable {:class "radio-inline" :for "review_status_2"} 
                    [:input {:type "radio" :name "status" :value "2" :id "review_status_2"}] "驳回"]
                   ]]
           [:div {:class "form-group"}
	               (f/label {:class "control-label" :for "replay-name"} "replay-label" "审核意见:")
	               (f/text-area {:rows 3 :class "form-control" :id "review-textarea" :required "required"} "replay")]
           [:div {:class "modal-footer"}
		          [:button {:type "button" :class "btn btn-default" :data-dismiss "modal"} "关闭"]
		          (f/submit-button {:class "btn btn-primary"} "提 交")]
          ];form end
         ];modal body end
        ]
       ]
      ]
     (include-js "/js/jquery.js" "/js/bootstrap.min.js" "/js/bootstrap-switch.min.js" "/js/sober.js")]))

;;用户登录之后的导航栏
(defn nav-bar 
  "navbar "
  []
  [:nav {:class "navbar  navbar-inverse navbar-fixed-top"}
   [:div {:class "container"}
    [:div {:class "navbar-header"}
     [:button {:type "button", :class "navbar-toggle collapsed", :data-toggle "collapse", :data-target "#navbar" :aria-expanded "false" :aria-controls "navbar"}
      [:span {:class "sr-only"} "Toggle navigation"] 
      [:span {:class "icon-bar"}] 
      [:span {:class "icon-bar"}]
      [:span {:class "icon-bar"}]
      ] 
     [:a {:class "navbar-brand", :href "/"} "Sober"]]
    [:div {:id "navbar" :class "navbar-collapse collapse"}
     [:ul {:class "nav navbar-nav"}
      [:li {} [:a {:href "/dashboard"} "Dashboard"]]
      [:li {} [:a {:href "/advertiser"} "广告主"]]
      [:li {} [:a {:href "/publisher"} "流量主"]]
      [:li {:class "dropdown" } 
       [:a {:class "dropdown-toggle" :shape "rect", :href "#" :data-toggle "dropdown"} 
        "管理后台" [:span {:class "caret"}] ]
       [:ul {:class "dropdown-menu"}
        [:li {} [:a {:href "/users"} "用户管理"]]
        [:li {} [:a {:href "/offers"} "offer审核"]]
        [:li {} [:a {:href "/settings"} "系统设置"]]
        ]]
      ]
     [:ul {:class "nav navbar-nav pull-right"}
      [:li {:class "dropdown" } 
       [:a {:class "dropdown-toggle" :href "#" :data-toggle "dropdown" :role "button" :aria-haspopup "true" :aria-expanded "false"} "atschx@gmail.com" [:span {:class "caret"}]]
       [:ul {:class "dropdown-menu"}
        [:li {} [:a {:href (str "/user/" (session/get :uid) "/edit")} "用户资料"]]
        [:li {} [:a {:href "/logout"} "退出"]]
        ]]
      [:li {} [:a {:href "/help.html"} "帮助"]]
      ]
     ]]]
  )

;;主页显示区域
(defn nav-static-bar 
  "Fixed navbar "
  []
  [:nav {:class "navbar navbar-default navbar-fixed-top"}
   [:div {:class "container"}
    [:div {:class "navbar-header"}
     [:button {:type "button", :class "navbar-toggle collapsed", :data-toggle "collapse", :data-target "#navbar" :aria-expanded "false" :aria-controls "navbar"}
      [:span {:class "sr-only"} "Toggle navigation"] 
      [:span {:class "icon-bar"}] 
      [:span {:class "icon-bar"}]
      [:span {:class "icon-bar"}]
      ] 
     [:a {:class "navbar-brand", :href "/"} "Sober"]]
    [:div {:id "navbar" :class "navbar-collapse collapse"}
     [:ul {:class "nav navbar-nav pull-right"}
      [:li {:class "dropdown" } 
       [:a {:class "dropdown-toggle" :href "#" :data-toggle "dropdown" :role "button" :aria-haspopup "true" :aria-expanded "false"} "产品列表" [:span {:class "caret"}]]
       [:ul {:class "dropdown-menu"}
        [:li {} [:a {:href "/advertiser"} "广告主"]]
        [:li {} [:a {:href "/publisher"} "流量主"]]
        ]]
      [:li {} [:a {:href "/help.html"} "帮助"]]
      [:li {} [:a {:href "/signin"} "登录"]]
      [:li {} [:a {:href "/signup"} "注册"]]]
     ]]]
  )


(defn pagenation []
  [:div {} ])