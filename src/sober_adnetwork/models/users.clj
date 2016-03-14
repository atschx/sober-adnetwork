(ns sober-adnetwork.models.users
  (:require
    [sober-adnetwork.models.db :as db] 
    [clojure.java.jdbc :as jdbc]))

(defn user-list 
  "all user list"
  []
  (jdbc/query (db/db-connection) ["SELECT * FROM users order by id "])
  )

(defn delete 
  ""
  [id]
  (jdbc/delete! 
    (db/db-connection)
    :users ["id = ?" id]
    ))
