(ns sober-adnetwork.views.admin.users
  (:use [hiccup.page :refer :all])
  (:require 
    [sober-adnetwork.views.layout :as layout]
    [sober-adnetwork.models.users :as users]
    ))

; Post is a map corresponding to a record from the database
(defn user-table-item [user]
  (let [id (:id user)
        name (str (:first_name user) " " (:last_name user))
        email (:email user)
        status (:status user)
        enable (:enable user)
        created_at (:created_at user)]
     [:tr {} 
          [:td {} id]
          [:td {} name]
          [:td {} email]
          [:td {} status]
          [:td {} enable]
          [:td {} created_at]
          [:td {} 
           [:a {:href (str "/admin/" id "/edit")} "编辑>>" ]
           [:a {:href (str "/admin/" id "/delete")} "删除>>" ]
           ]
          ]))

(defn user-list []
  (layout/common "user list"
    (layout/nav-bar)
    [:div {:class "container"}
     [:div {:class "row"}
      [:div {:class "span12"} 
       [:div {:class "page-header"}
        [:h2 "账号列表"]
       ]
      ]]
     [:div {:class "row"}
      [:div {:class "span12"}
       [:table {:class "table table-striped table-hover "}
        [:thead {} 
         [:tr {} 
          [:th {} "ID"]
          [:th {} "用户名"]
          [:th {} "用户邮箱"]
          [:th {} "账号状态"]
          [:th {} "启用状态"]
          [:th {} "创建时间"]
          [:th {} "更多操作"]
          ]
         ]
        [:tbody {} 
         (map #(user-table-item %) (users/user-list))
         ]
        ]]]
     ]
;    (include-js "/js/chartpage.js"))
  ))
