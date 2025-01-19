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
        start.invoke(Clojure.read(":port"), Clojure.read(port), Clojure.read(":handler"), wrapIced.invoke(defaultHandler.invoke()));
        System.out.println("nrepl server started on port " + port);
    }
}
