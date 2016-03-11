(ns sober-adnetwork.models.schema
  (:require [clojure.java.jdbc :as jdbc]))


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

(defn create-offers
  "Create a table to store offer of advertiser submited."
  []
  (jdbc/create-table-ddl
    :offers
    [:id  "bigint(21)" "PRIMARY KEY AUTO_INCREMENT"]
    [:name "varchar(255) COMMENT '界面显示名称'"]
    [:cover "varchar(32) COMMENT '封面 推荐256*256 或 512*512'"]
    [:price :real "COMMENT '人民币定价'"]
    [:price_model "varchar(3) COMMENT '计价模型：CPA、CPC'"]
    [:clearing_cycle "varchar(5) COMMENT '结算周期：WEEK、DAY、MONTH'"]
    [:eff_desc :text "NOT NULL COMMENT '广告主自行添加的 offer 有效定义'"]
    [:status "tinyint" "NOT NULL DEFAULT 0 COMMENT '当前offer状态 0.apending 1.approved 2.rejected'"]
    [:enable "tinyint(1) NOT NULL DEFAULT 1 COMMENT '可用状态 1.可用 0.不可用'"]
    [:advertiser_id "bigint(21)" "NOT NULL" "COMMENT '创建该 offer 的广告主'"]
    [:replay :text "COMMENT '管理员回复信息，多用于禁用 offer 时添加说明'"]
    [:created_by "int"]
    [:created_at "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP"]
    [:updated_by "int"]
    [:updated_at "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"]
    :table-spec "ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8"
    ))

(defn create-offer-apply-list
  "Create a table to save offer apply list."
  []
  (jdbc/create-table-ddl 
    :offer_apply_list
    [:id  "bigint(21) PRIMARY KEY AUTO_INCREMENT"]
    [:offer_id "bigint(21) NOT NULL"]
    [:publisher_id "int NOT NULL" "COMMENT '申请该 offer 的流量主'"]
    [:status "tinyint" "NOT NULL DEFAULT 0 COMMENT '当前offer状态 0.apending 1.approved 2.rejected'"]
    [:replay :text "COMMENT '驳回 offer申请时添加说明'"]
    [:enable "tinyint(1) NOT NULL DEFAULT 1 COMMENT '可用状态 1.可用 0.不可用'"]
    [:created_by "int"]
    [:created_at "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP"]
    [:updated_by "int"]
    [:updated_at "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"]
    :table-spec "ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8"
    ))

(defn create-offer-attchments
  "Create a table to store offer attchments."
  []
  (jdbc/create-table-ddl 
    :offer_attchments
    [:id  "bigint(21) PRIMARY KEY AUTO_INCREMENT"]
    [:name "varchar(255)"]
    [:store_path "varchar(255)"]
    [:short_url "varchar(50)"]
    [:checksum "varchar(100)"]
    [:offer_id "bigint(21) NOT NULL"]
    :table-spec "ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8"
    ))

(defn create-schema-adnetwork
  "make adnertwork schema"
  [db]
  (jdbc/db-do-commands 
        db
        (create-users)
        (create-offers)
        (create-offer-apply-list)))

(defn drop-schema-adnetwork
  "Drop adnetwork schema"
  [db-spec]
  (jdbc/db-do-commands 
	  (db-connection)
	  (jdbc/drop-table-ddl :users)
	  (jdbc/drop-table-ddl :offers)
	  (jdbc/drop-table-ddl :offer_apply_list)
	  (jdbc/drop-table-ddl :offer_attchments)
	  ))

(defn make-demo-data
  "make demo data for adnetwork"
  [db]
  (jdbc/insert! 
     db
     :users
     [:first_name :last_name :email :mobile :incharge :created_by :updated_by]
     ["admin" "admin" "admin@atschx.com" "13800000000" nil 10000 10000]
     ["albert" "sun" "atschx@gmail.com" "1580000000" 10000 10001 10001]))
