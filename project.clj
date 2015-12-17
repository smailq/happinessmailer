(defproject happinessmailer "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [reagent "0.5.0"]
                 [re-frame "0.4.1"]
                 [re-com "0.6.1"]
                 [secretary "1.2.3"]
                 [garden "1.2.5"]
                 [clj-http "2.0.0"]
                 [clj-time "0.11.0"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [environ "1.0.1"]
                 [hiccup "1.0.5"]
                 [compojure "1.4.0"]
                 [cheshire "5.5.0"]
                 [juxt/iota "0.2.0"]
                 [crypto-password "0.1.3"]
                 [org.clojure/java.jdbc "0.4.1"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]
                 [com.cognitect/transit-clj "0.8.285"]]

  :min-lein-version "2.5.3"

  :figwheel {:ring-handler happinessmailer.core/app}

  :source-paths ["src/clj"]

  :plugins [[lein-figwheel "0.5.0-1" :exclusions [cider/cider-nrepl]]
            [lein-garden "0.2.6"]
            [lein-ring "0.9.7"]
            [environ/environ.lein "0.3.1"]]

  :hooks [environ.leiningen.hooks]

  :ring {:handler happinessmailer.core/app}

  :clean-targets ^{:protect false} ["resources/public/css/compiled"
                                    "target"]

  :uberjar-name "happinessmailer-standalone.jar"

  :profiles {:uberjar {:hooks      [leiningen.garden]
                       :env        {:production true}
                       :aot        :all}
             :test {:source-paths ["test/clj"]}}

  ;:aliases {"test" ["test" "hapinessmailer.something"]}

  :garden {:builds [{:id "screen"
                     :source-paths ["src/clj"]
                     :stylesheet happinessmailer.css/screen
                     :compiler {:output-to "resources/public/css/compiled/screen.css"
                                :pretty-print? false}}]})
