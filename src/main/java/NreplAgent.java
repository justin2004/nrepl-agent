package com.example ;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import java.lang.instrument.Instrumentation;
import java.util.Collections;

public class NreplAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        String port = System.getenv("AGENT_PORT");
        if(port == null){
            port = "7888";
        }
        IFn require = Clojure.var("clojure.core", "require");
        require.invoke(Clojure.read("nrepl.server"));
        require.invoke(Clojure.read("iced.nrepl"));
        IFn wrapIced = Clojure.var("iced.nrepl", "wrap-iced");
        IFn defaultHandler = Clojure.var("nrepl.server", "default-handler");
        IFn start = Clojure.var("nrepl.server", "start-server");
        start.invoke(
                Clojure.read(":bind"), "0.0.0.0", // this is needed if you are running the jvm in a docker container and you want to expose the nREPL port outside the container
                                                  // TODO this is not safe for deployments in general
                Clojure.read(":port"), Clojure.read(port), 
                Clojure.read(":handler"), wrapIced.invoke(defaultHandler.invoke()));
        System.out.println("nrepl server started on port " + port);
    }
}
