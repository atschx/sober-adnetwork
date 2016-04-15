(ns sober-adnetwork.views.layout
  (:use [hiccup.page :refer :all])
  (:require [noir.session :as session]
            [hiccup.form :as f]
            [noir.util.anti-forgery :as anti-forgery]
            ))

;; 首页使用
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
     (include-js "/js/vendor/jquery.js" "/js/bootstrap.min.js" "/js/sober.js")]))

;; 列表页使用
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
     
     ;; 用户分管
     [:div {:class "modal fade" :id "inchargeModal" :tabindex "-1" :role "dialog" :aria-labelledby "inchargeModalLabel"}
      [:div {:class "modal-dialog" :role "document"} 
       [:div {:class "modal-content"}
        [:div {:class "modal-header"}
         [:button {:type "button" :class "close" :data-dismiss "modal" :aria-label "Close"} [:span {:aria-hidden "true"} "&times;"]]
         [:h4 {:class "modal-title" :id "inchargeModalLabel"} "用户分管"]
         ]
        [:div {:class "modal-body"}
         [:form {:id "incharge-form" :method "POST"}
	         (anti-forgery/anti-forgery-field)
           [:div {:class "form-group"}
           [:div {:class "form-group"}
	               (f/label {:class "control-label" :for "user-charge-label"} "user-charge-label" "指派给（管理员）:")
	               (f/text-field {:class "form-control" :id "incharge-user" :required "required"} "incharge")]
           [:div {:class "modal-footer"}
		          [:button {:type "button" :class "btn btn-default" :data-dismiss "modal"} "关闭"]
		          (f/submit-button {:class "btn btn-primary"} "提 交")]
          ];form end
         ];modal body end
        ]
       ]
      ]]
     
     ;; 审核模态窗口
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
	               (f/label {:class "control-label" :for "review-replay"} "replay-label" "审核意见:")
	               (f/text-area {:rows 3 :class "form-control" :id "review-replay" :required "required"} "replay")]
           [:div {:class "modal-footer"}
		          [:button {:type "button" :class "btn btn-default" :data-dismiss "modal"} "关闭"]
		          (f/submit-button {:class "btn btn-primary"} "提 交")]
          ];form end
         ];modal body end
        ]
       ]
      ]
     
     ;; 申请模态窗口
     [:div {:class "modal fade" :id "applyModal" :tabindex "-1" :role "dialog" :aria-labelledby "applyModalLabel"}
      [:div {:class "modal-dialog" :role "document"} 
       [:div {:class "modal-content"}
        [:div {:class "modal-header"}
         [:button {:type "button" :class "close" :data-dismiss "modal" :aria-label "Close"} [:span {:aria-hidden "true"} "&times;"]]
         [:h4 {:class "modal-title" :id "applyModalLabel"} "申请："]
         ]
        [:div {:class "modal-body"}
         [:form {:id "apply-form" :method "POST"}
	         (anti-forgery/anti-forgery-field)
           [:div {:class "form-group"}
	               (f/label {:class "control-label" :for "apply-remark"} "apply-remark-label" "申请备注:")
	               (f/text-area {:rows 5 :class "form-control" :id "apply-remark" :required "required" :placeholder "此处填写凭啥你来申请"} "remark")]
           [:div {:class "modal-footer"}
		          [:button {:type "button" :class "btn btn-default" :data-dismiss "modal"} "关闭"]
		          (f/submit-button {:class "btn btn-primary"} "提交申请")]
          ];form end
         ];modal body end
        ]
       ]
      ]
     
     (include-js "/js/vendor/jquery.js" "/js/bootstrap.min.js" "/js/bootstrap-switch.min.js" "/js/sober.js")
     ]))

(defn upload-page-common 
  "带上传功能的页面基于此模版构建页面"
  [title & content]
  (html5
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:http-equiv "X-UA-Compatible", :content "IE=edge"}]
     [:meta {:name "viewport", :content "width=device-width, initial-scale=1"}]
     [:meta {:name "description", :content "a network of publisher and advertiser"}] 
     [:meta {:name "author", :content "albert"}]
     [:title (format "sober-adnetwork:%s" title)]
     (include-css "/css/bootstrap.min.css" 
                  "/css/bootstrap-theme.min.css" 
                  "/css/sober.css"
                  "/css/plugins/blueimp-gallery.min.css"
                  "/css/plugins/jquery.fileupload.css"
                  "/css/plugins/jquery.fileupload-ui.css"
                  )]
    [:body content
     [:div {:class "container"}
      [:hr {}] 
      [:footer {} 
       [:p {} "©2016 cia.im"]]]
     (include-js "/js/vendor/jquery.js" 
                 "/js/vendor/jquery.ui.widget.js" 
                 "/js/plugins/tmpl.min.js"
                 "/js/plugins/load-image.all.min.js"
                 "/js/plugins/canvas-to-blob.min.js"
                 "/js/bootstrap.min.js" 
                 "/js/plugins/jquery.blueimp-gallery.min.js"
                 "/js/plugins/jquery.iframe-transport.js"
                 "/js/plugins/jquery.fileupload.js"
                 "/js/plugins/jquery.fileupload-process.js"
                 "/js/plugins/jquery.fileupload-image.js"
                 "/js/plugins/jquery.fileupload-audio.js"
                 "/js/plugins/jquery.fileupload-video.js"
                 "/js/plugins/jquery.fileupload-validate.js"
                 "/js/plugins/jquery.fileupload-ui.js"
                 "/js/main.js"
;                 "/js/sober.js"
                 )]))

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
        [:li {} [:a {:href "/offers"} "审核广告主 Offer"]]
        [:li {} [:a {:href "/apply-list"} "审核流量主 Apply"]]
;        [:li {} [:a {:href "/settings"} "系统设置"]]
        ]]
      ]
     [:ul {:class "nav navbar-nav pull-right"}
      [:li {:class "dropdown" } 
       [:a {:class "dropdown-toggle" :href "#" :data-toggle "dropdown" :role "button" :aria-haspopup "true" :aria-expanded "false"} "atschx@gmail.com" [:span {:class "caret"}]]
       [:ul {:class "dropdown-menu"}
        [:li {} [:a {:href (str "/user/" (session/get :uid) "/edit")} "用户资料"]]
        [:li {} [:a {:href "/logout"} "退出"]]
        ]]
      [:li {} [:a {:href (str "/" (session/get :uid) "/contact/us")} "联系客服"]]
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