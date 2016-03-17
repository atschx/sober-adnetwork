(ns sober-adnetwork.views.admin.users
  (:use [hiccup.page :refer :all])
  (:require 
    [sober-adnetwork.views.layout :as layout]
    [sober-adnetwork.models.users :as users]
    ;;处理表单信息
    [hiccup.core :refer (html)]
    [hiccup.form :as f]
    [noir.util.anti-forgery :as anti-forgery]
    
    ;;
    [cheshire.core :refer :all]
    ))

(defn user-table-item [user]
  (let [id (:id user)
        name (str (:first_name user) " " (:last_name user))
        slug (:slug user)
        email (:email user)
        qq (:qq user)
        status (:status user)
        enable (:enable user)
        updated_at (:updated_at user)]
     [:tr {}
          [:td {} id]
          [:td {} name]
          [:td {} slug]
          [:td {} email]
          [:td {} qq]
          [:td {:class (condp = status "0" "warning" "1" "success" "2" "danger" "info" )}
          (condp = status
				      "0" [:span {:class "label label-warning"} (str "等待审核[" status "]")]
				      "1" [:span {:class "label label-success"} (str "已审核通过[" status "]")]
				      "2" [:span {:class "label label-danger"} (str "已驳回申请[" status "]")])
           ]
          [:td {} 
           [:input {:class "" :type "checkbox" :name "user-enable-checkbox" :checked "checked"}]
           ]
          [:td {} updated_at]
          [:td {} 
           [:a {:href (str "/user/" id "/edit") :class "btn btn-info btn-xs" :role "button"} "编辑" ]
           [:button {:type "button" :class "btn btn-primary btn-xs" :data-toggle "modal" :data-target "#reviewModal"  
                     :data-whatever (generate-string 
                                      {:action "/review/user"
                                       :review_target id
                                       :review_text   (format "%s(%s)" name id)
                                       })  
                     } "审核"]
           [:a {:href (str "/user/" id "/delete") :class "btn btn-danger btn-xs" :role "button"} "删除" ]
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
       [:table {:class "table table-striped table-hover table-bordered "}
        [:thead {} 
         [:tr {} 
          [:th {} "ID"]
          [:th {} "姓名"]
          [:th {} "昵称"]
          [:th {} "邮箱"]
          [:th {} "QQ"]
          [:th {} "审核状态"]
          [:th {} "账号状态"]
          [:th {} "最后更新"]
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


;;"修改用户资料"
(defn user-edit [id]
  (layout/common (str "user[" id "]edit")
   (layout/nav-bar)
   [:div {:class "container"}
    [:div {:class "row"}
      [:div {:class "span12"} 
       [:div {:class "page-header"}
        [:h2 (str "修改用户信息：[" id "]")]
       ]
      ]]
    [:div {:class "row"}
      [:div {:class "col-lg-6"}
      (list
        (let [user (users/get-user-by-id id)]
          (f/form-to {:class "form-horizontal" :role "form"} 
                     [:post "save"]
                     (anti-forgery/anti-forgery-field)
                     [:div {:class "form-group"}
                      (f/label {:class "col-sm-2 control-label"} "email" "E-mail")
                      [:div {:class "col-sm-10"} (f/email-field {:class "form-control"} "email" (:email user))]]
                     [:div {:class "form-group"}
                      (f/label {:class "col-sm-2 control-label"} "qq" "QQ 号")
                      [:div {:class "col-sm-10"} (f/text-field {:class "form-control"} "qq" (:qq user))]]
                     [:div {:class "form-group"}
                      (f/label {:class "col-sm-2 control-label"} "slug" "Slug")
                      [:div {:class "col-sm-10"} (f/text-field {:class "form-control"} "slug" (:slug user))]]
                     [:div {:class "form-group"}
                      (f/label {:class "col-sm-2 control-label"} "mobile" "手机号")
                      [:div {:class "col-sm-10"} (f/text-field {:class "form-control"} "mobile" (:mobile user))]]
                     [:div {:class "form-group"}
                      (f/label {:class "col-sm-2 control-label"} "first_name" "Firstname")
                      [:div {:class "col-sm-10"} (f/text-field {:class "form-control"} "first_name" (:first_name user))]]
                     [:div {:class "form-group"}
                      (f/label {:class "col-sm-2 control-label"} "last_name" "Lastname")
                      [:div {:class "col-sm-10"} (f/text-field {:class "form-control"} "last_name" (:last_name user))]]
                     [:div {:class "form-group"}
                      [:div {:class "col-sm-offset-2 col-sm-10"}
                     (f/submit-button {:class "btn btn-primary"} "保 存")]]
                     )
          ))]]]
   ))
