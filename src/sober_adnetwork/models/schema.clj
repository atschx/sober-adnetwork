(ns sober-adnetwork.models.schema
  (:require [clojure.java.jdbc :as jdbc])
  (:import com.jolbox.bonecp.BoneCPDataSource))


(let [db-host "localhost"
      db-port 3306
      db-name "adnetwork"]
  (def db-spec {:classname "com.mysql.jdbc.Driver"
		         :subprotocol "mysql"
		         :subname (str "//" db-host ":" db-port "/" db-name)
		         :user "sober"
		         :password "sober"}))

(def bonecp-params {:min-pool 6 :max-pool 18})

(defn pool
  [spec params]
  (let [partitions 3
        cpds (doto (BoneCPDataSource.)
               (.setJdbcUrl (str "jdbc:" (:subprotocol spec) ":" (:subname spec)))
               (.setUsername (:user spec))
               (.setPassword (:password spec))
               (.setMinConnectionsPerPartition (inc (int (/ (:min-pool params) partitions))))
               (.setMaxConnectionsPerPartition (inc (int (/ (:max-pool params) partitions))))
               (.setPartitionCount partitions)
               (.setStatisticsEnabled true)
               ;; test connections every 25 mins (default is 240):
               (.setIdleConnectionTestPeriodInMinutes 25)
               ;; allow connections to be idle for 3 hours (default is 60 minutes):
               (.setIdleMaxAgeInMinutes (* 3 60))
               ;; consult the BoneCP documentation for your database:
               (.setConnectionTestStatement "/* ping *\\/ SELECT 1"))] 
    {:datasource cpds}))

(def pooled-db (delay (pool db-spec bonecp-params)))

(defn db-connection [] @pooled-db)


(defn create-users
  "create a user table to store advertiser/publisher of the adnetwork "
  []
  (jdbc/create-table-ddl
    :users
    [:id  "int(10) zerofill unsigned PRIMARY KEY AUTO_INCREMENT COMMENT '用户id'"]
    [:first_name "varchar(50) COMMENT '名'"]
    [:last_name "varchar(50) COMMENT '姓'"]
    [:slug "varchar(100) COMMENT '个性化显示：默认为姓＋名'"]
    [:email "varchar(50)  COMMENT '注册邮箱用于接收相关邮件信息'"]
    [:password "varchar(100)"]
    [:mobile "varchar(18)"]
    [:qq "varchar(15)"]
    [:status "varchar(20) NOT NULL DEFAULT 0 COMMENT '申请状态：0.apending 1.approved 2.rejected'"]
    [:enable "tinyint(1) NOT NULL DEFAULT 0 COMMENT '账号可用状态 1.可用 0.不可用'"]
    [:fiscal_status "tinyint NOT NULL DEFAULT 1 COMMENT '账务属性：0.公司，1.个人'"]
    [:incharge "int COMMENT '负责人(用户账号分管)' "]
    [:created_by "int"]
    [:created_at "timestamp" "NOT NULL DEFAULT CURRENT_TIMESTAMP"]
    [:updated_by "int"]
    [:updated_at "timestamp" "NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"]
    :table-spec "ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8"
    ))

(defn create-schema-adnetwork
  "make adnertwork schema"
  []
  (jdbc/db-do-commands 
        (db-connection)
        (create-users)))

(defn drop-schema-adnetwork
  "Drop adnetwork schema"
  []
  (jdbc/db-do-commands 
	  (db-connection)
	  (jdbc/drop-table-ddl :users)
	  ))

(defn make-demo-data
  "make demo data for adnetwork"
  []
  (jdbc/insert! 
     (db-connection)
     :users
     [:first_name :last_name :email :mobile :incharge :created_by :updated_by]
     ["admin" "admin" "admin@atschx.com" "13800000000" nil 10000 10000]
     ["albert" "sun" "atschx@gmail.com" "1580000000" 10000 10001 10001]))
