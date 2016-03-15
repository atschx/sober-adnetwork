(ns sober-adnetwork.models.users
  (:require
    [sober-adnetwork.models.db :as db] 
    [clojure.java.jdbc :as jdbc]))

(defn now []
  (.getTime (java.util.Date.)))

(defn user-list []
  (jdbc/query (db/db-connection) ["select * from users order by id "]))

(defn create [params]
  (jdbc/insert! (db/db-connection) :users params))

(defn delete [id]
  (jdbc/delete! (db/db-connection) :users ["id = ?" id]))

(defn get-user-by-id [id]
  (first (jdbc/query (db/db-connection) ["select * from users where id = ?" id])))

(defn update-user [user]
  (let [res (jdbc/update! (db/db-connection) 
                          :users user
                          ["id = ?" (:id user)])]
    (when-not (= res [1])
      (throw (Exception.  "DB Update has not succeeded")))))


