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
        ///
        // IFn bob = Clojure.var("clojure.core", "+");
        // IFn map = Clojure.var("clojure.core", "map");
        // IFn reduce = Clojure.var("clojure.core", "reduce");
        // IFn doall = Clojure.var("clojure.core", "doall");
        // IFn vec = Clojure.var("clojure.core", "vec");
        // bob.invoke(1,99);
        // Clojure.read("clojure.set").getClass()
        // vec.invoke(reduce.invoke(bob,Clojure.read("[1 3 5]")));
        // (nrepl.server/start-server :port port
        //                            :handler (iced.nrepl/wrap-iced (nrepl.server/default-handler)))
        ///
        IFn require = Clojure.var("clojure.core", "require");
        require.invoke(Clojure.read("nrepl.server"));
        require.invoke(Clojure.read("iced.nrepl"));
        IFn wrapIced = Clojure.var("iced.nrepl", "wrap-iced");
        // IFn handler = Clojure.var("nrepl.middleware", "wrap-repl");
        // Object icedMiddleware = wrapIced.invoke(handler);
        IFn defaultHandler = Clojure.var("nrepl.server", "default-handler");
        IFn start = Clojure.var("nrepl.server", "start-server");
        // int port = 7888;
        // start.invoke(Clojure.read(":port"), Clojure.read(Integer.toString(port)));
        // start.invoke(Clojure.read(":port"), Clojure.read(Integer.toString(port)), Clojure.read(":handler"), icedMiddleware);
        // start.invoke(Clojure.read(":port"), Clojure.read(Integer.toString(port)), Clojure.read(":handler"), wrapIced.invoke(defaultHandler.invoke()));
        start.invoke(Clojure.read(":port"), Clojure.read(port), Clojure.read(":handler"), wrapIced.invoke(defaultHandler.invoke()));
        System.out.println("nrepl server started on port " + port);
}
}
