(ns sober-adnetwork.models.users
  (:require
    [clojure.tools.logging :as log]
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

(defn get-user-by-email [email]
  (first (jdbc/query (db/db-connection) ["select * from users where email = ?" email])))

;(defn reset-password [db reset_token password]
;  (let [account (get-account-by-reset-token db reset_token)]
;    (if-not account
;      {:errors {:password "Can not reset password by current token"}}
;      (let [now (now)
;            res (jdbc/update! db :accounts {:password (credentials/hash-bcrypt password)
;                                            :reset_token nil
;                                            :expire -1
;                                            :updated_at now}
;                              ["slug = ?" (:slug account)])]
;        (when-not (= res [1])
;          (throw (Exception.  "DB Update has not succeeded")))
;        account))))

(defn update-user [user]
  (log/info user)
  (let [res (jdbc/update! (db/db-connection) 
                          :users user
                          ["id = ?" (:id user)])]
    (when-not (= res [1])
      (throw (Exception.  "DB Update has not succeeded")))))


