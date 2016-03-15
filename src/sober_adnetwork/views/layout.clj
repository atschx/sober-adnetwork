(ns sober-adnetwork.views.layout
  (:use [hiccup.page :refer :all]))

(defn common [title & content]
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
       [:p {} "©2016 atschx.com"]]]
     (include-js "/js/jquery.js" "/js/bootstrap.min.js" "/js/sober.js")]))

;; 用于登录之后展示
(defn nav-bar []
  [:div
   [:br] [:br] [:br]
   [:div {:class "navbar navbar-inverse navbar-fixed-top", :role "navigation"}
    [:div {:class "container"}
     [:div {:class "navbar-header"}
      [:button {:type "button", :class "navbar-toggle", :data-toggle "collapse", :data-target ".navbar-collapse"}
       [:span {:class "sr-only"} "Toggle navigation"] [:span {:class "icon-bar"}] [:span {:class "icon-bar"}]
       [:span {:class "icon-bar"}]] [:a {:shape "rect", :class "navbar-brand", :href "/"} "Sober Adnetwork"]]
     [:div {:class "collapse navbar-collapse"}
      [:ul {:class "nav navbar-nav"}
       [:li {} [:a {:shape "rect", :href "/dashboard"} "Dashboard"]]
       [:li {} [:a {:shape "rect", :href "/advertiser"} "Advertiser"]]
       [:li {} [:a {:shape "rect", :href "/publisher"} "Publisher"]]
       [:li {:class "dropdown" } 
        [:a {:class "dropdown-toggle" :shape "rect", :href "#" :data-toggle "dropdown"} 
         "Admin" [:span {:class "caret"}] ]
        [:ul {:class "dropdown-menu"}
         [:li {} [:a {:shape "rect", :href "/admin/users"} "用户管理"]]
         [:li {} [:a {:shape "rect", :href "/admin/offers"} "offer审核"]]
         [:li {} [:a {:shape "rect", :href "/admin"} "系统设置"]]
         ]]
       ]
      [:ul {:class "nav navbar-nav pull-right"}
       [:li {} [:a {:shape "rect", :href "/dashboard"} "atschx@gmail.com"]]
       [:li {:class "divider-vertical"}]
       [:li {} [:a {:shape "rect", :href "/"} "退出"]]]
      ]]]]
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
     [:a {:class "navbar-brand", :href "/"} "友加"]]
    [:div {:id "navbar" :class "navbar-collapse collapse"}
     [:ul {:class "nav navbar-nav pull-right"}
      [:li {:class "dropdown" } 
       [:a {:class "dropdown-toggle" :href "#" :data-toggle "dropdown" :role "button" :aria-haspopup "true" :aria-expanded "false"} "产品列表" [:span {:class "caret"}]]
       [:ul {:class "dropdown-menu"}
        [:li {} [:a {:href "/admin/users"} "广告主"]]
        [:li {} [:a {:href "/admin/offers"} "流量主"]]
        ]]
      [:li {} [:a {:href "/help"} "帮助"]]
      [:li {} [:a {:href "/signin"} "登录"]]
      [:li {} [:a {:href "/signup"} "注册"]]]
     ]]]
  )



(defn pagenation []
  [:div {} ])