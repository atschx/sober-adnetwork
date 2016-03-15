(ns sober-adnetwork.models.offers
  (:require
    [sober-adnetwork.models.db :as db] 
    [clojure.java.jdbc :as jdbc]))

(defn offer-list [advertiser_id]
  (jdbc/query (db/db-connection) 
              ["select * from offers where advertiser_id = ? order by id " advertiser_id]))
