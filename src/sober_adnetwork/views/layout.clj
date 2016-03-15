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
     (include-css "/css/bootstrap.min.css" "/css/sober.css")
     (include-js "/js/json2.js" "/js/xpath.js" "/js/Chart.js")]
    [:body content
     [:div {:class "container"}
      [:hr {}] 
      [:footer {} 
       [:p {} "© atschx.com 2016"]]]
     (include-js "/js/jquery.js" "/js/bootstrap.min.js" "/js/sober.js")]))

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
       [:li {} [:a {:shape "rect", :href "/admin"} "Sign out"]]]
      ]]]]
  )


(defn pagenation []
  [:div {} ])