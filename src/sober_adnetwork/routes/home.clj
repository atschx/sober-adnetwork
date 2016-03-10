(ns sober-adnetwork.routes.home
  (:require [compojure.core :refer :all]
            [sober-adnetwork.views.layout :as layout]))

(defn home []
  (layout/common [:h1 "Hello World!"] [:p "I'm from Shandong Provenice"]))

(defroutes home-routes
  (GET "/" [] (home)))
