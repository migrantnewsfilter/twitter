(ns twitter.db
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [cheshire.core :refer [parse-string]]
            [environ.core :refer [env]]))

(defn connect-to-db [path] (mg/connect path))
(defn disconnect-db [conn] (mg/disconnect conn))

(defn get-terms [conn]
  (let [db (mg/get-db conn "newsfilter")]
    (get-in (mc/find-by-id db "terms" "twitter") ["keywords"])))

(defn format-entry [body user id date]
  {:_id id
   :published date
   :added (java.util.Date.)
   :content {:link (str "https://twitter.com/" user "/status/" id)
             :author user
             :body body }})

(defn get-status [json] (parse-string json true))
(defn get-user [status] (get-in status [:user :screen_name]))
(defn get-id [status] (str "tw:" (:id status)))
(defn get-body [status] (:text status))

(defn get-date [status]
  (let [date-format (java.text.SimpleDateFormat. "EEE MMM dd HH:mm:ss Z yyyy")]
    (.parse  date-format (:created_at status))))

(defn prepare-entry [json]
  (let [status (get-status json)]
    (try
      (format-entry (get-body status) (get-user status) (get-id status) (get-date status))
      (catch Exception e (throw (ex-info (.getMessage e) {:cause :parsing}))))))

(defn write-to-db
  [conn status]
  (let [db (mg/get-db conn "newsfilter")]
    (try
      (mc/insert db "news" (prepare-entry status))
      (catch com.mongodb.DuplicateKeyException e)
      (catch clojure.lang.ExceptionInfo e))))