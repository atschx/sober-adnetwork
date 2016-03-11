(ns sober-adnetwork.models.db
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
