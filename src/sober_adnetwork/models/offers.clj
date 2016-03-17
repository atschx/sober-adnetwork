(ns sober-adnetwork.models.offers
  (:require
    [clojure.tools.logging :as log]
    [sober-adnetwork.models.db :as db] 
    [clojure.java.jdbc :as jdbc]))

(defn create [params]
  (log/info (str params))
  (jdbc/insert! (db/db-connection) :offers (dissoc params :__anti-forgery-token)))

(defn delete [id]
  (jdbc/delete! (db/db-connection) :offers ["id = ?" id]))

(defn offer-list [advertiser_id]
  (jdbc/query (db/db-connection) 
              ["select * from offers where advertiser_id = ? order by id " advertiser_id]))

(defn admin-offer-list []
  (jdbc/query (db/db-connection) 
              ["select * from offers order by id "]))


