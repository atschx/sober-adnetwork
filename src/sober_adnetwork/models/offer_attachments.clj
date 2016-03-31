(ns sober-adnetwork.models.offer-attachments
  (:require
    [clojure.tools.logging :as log]
    [sober-adnetwork.models.db :as db] 
    [clojure.java.jdbc :as jdbc]
    [pandect.algo.sha1 :refer :all]
    [clojure.java.io :as io]
    ))

(defn create 
  "创建 offer-attachements "
  [params]
  (log/info (str params))
  (jdbc/insert! (db/db-connection) :offer_attchments (dissoc params :__anti-forgery-token)))

(defn restore-attachements
  [attch]
  (let [
        offer_id (:offer_id attch)
        tempfile (:tempfile attch)
        ;sha1 生成校验码文件
        checksum (sha1 (:tempfile attch))
        filename (str "/data/upload/" (:offer_id attch) "_"  (:filename attch))
        ]
    (println checksum)
    (create  {:name filename 
              :store_path filename
              :short_url 
              :offer_id offer_id
              :checksum checksum
                })
    (io/copy 
      (io/file tempfile) 
      (io/file filename))
    )
  )
