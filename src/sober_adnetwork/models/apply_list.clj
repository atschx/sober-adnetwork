(ns sober-adnetwork.models.apply-list 
  (:require
    [clojure.tools.logging :as log]
    [sober-adnetwork.models.db :as db] 
    [clojure.java.jdbc :as jdbc]))

;; 创建申请列表
(defn create [ params ]
  (jdbc/insert! (db/db-connection) :offer_apply_list params))

;; 显示当前流量主申请的广告列表
(defn my-apply-list [publisher_id]
  (jdbc/query (db/db-connection) 
              ["select a.*,b.name, b.price, b.price_model, b.clearing_cycle 
								from offer_apply_list a,offers b 
								where 
								 a.offer_id = b.id 
								and 
								a.publisher_id = ? 
								order by id " publisher_id]))

(defn list []
  (jdbc/query (db/db-connection) 
              ["select a.*,b.name, b.price, b.price_model, b.clearing_cycle 
								from offer_apply_list a,offers b 
								where 
								 a.offer_id = b.id 
								order by a.id "]))

;; 更新申请数据
(defn update-apply-list [apply-list]
  (log/info apply-list)
  (let [res (jdbc/update! (db/db-connection) 
                          :offer_apply_list apply-list
                          ["id = ?" (:id apply-list)])]
    (when-not (= res [1])
      (throw (Exception.  "DB Update has not succeeded")))))

;; 申请人取消申请
(defn delete [id]
  (jdbc/delete! (db/db-connection) :offer_apply_list ["id = ?" id]))