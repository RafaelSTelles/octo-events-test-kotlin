package br.com.rafaelstelles

import br.com.rafaelstelles.controller.OctoEndpoints
import br.com.rafaelstelles.factory.DatabaseFactory
import br.com.rafaelstelles.factory.JacksonFactory
import br.com.rafaelstelles.service.EventServiceImpl
import io.javalin.Javalin

class OctoEventsApplication {

    fun startJavalin(port : Int, database: String): Javalin {
        val app = Javalin.create().start(port)
        app.routes {
            OctoEndpoints(EventServiceImpl()).addEndpoints();
        }

        DatabaseFactory.init(database)
        JacksonFactory.init()
        return app;
    }

}
