(ns socketspam.core
    (:use lamina.core aleph.tcp aleph.http aleph.formats))

;Create the channel all the clients will listen on
(def broadcast (channel))

;Everything on tcp is trusted, send it to be broadcast
(defn tcp-client-handler [ch client-info]
    (siphon ch broadcast))

;Websockets clients get all broadcasts
(defn websockets-client-handler [ch handshake]
    (siphon broadcast ch))

(defn -main [] 
    (println "Starting server")
    (start-tcp-server tcp-client-handler {:port 8000})
    (start-http-server websockets-client-handler {:port 8001 :websocket true})
    (println "Server started"))
