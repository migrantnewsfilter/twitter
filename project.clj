(defproject twitter "0.1.0"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.3.442"]
                 [org.clojure/tools.logging "0.3.1"]
                 [ch.qos.logback/logback-classic "1.2.3"]
                 [com.twitter/hbc-core "2.2.0"]
                 [com.novemberain/monger "3.1.0"]
                 [cheshire "5.7.0"]
                 [environ "1.1.0"]]
  :main ^:skip-aot twitter.core
  :resource-paths ["src/resources"]
  :plugins [[lein-environ "1.1.0"]]
  :profiles {:uberjar {:aot :all} 
             :dev {:dependencies [[midje "1.6.3"]]
                   :resource-paths ["test/resources"]}})
