(defproject java-time-dte "2018-04-18"
  :description "Datomic type extensions for java.time classes"
  :url "https://github.com/magnars/java-time-dte"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies []
  :profiles {:dev {:plugins []
                   :dependencies [[datomic-type-extensions "2018-04-18"]
                                  [org.clojure/clojure "1.8.0"]]
                   :source-paths ["dev"]}})
