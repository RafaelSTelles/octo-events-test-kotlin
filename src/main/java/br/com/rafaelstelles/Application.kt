package br.com.rafaelstelles

import br.com.rafaelstelles.controller.OctoEndpoints
import br.com.rafaelstelles.factory.DatabaseFactory
import br.com.rafaelstelles.factory.JacksonFactory
import br.com.rafaelstelles.service.EventServiceImpl
import io.javalin.Javalin

fun main(args: Array<String>) {

    val app = Javalin.create().start(7000)
    app.routes {
        OctoEndpoints(EventServiceImpl()).addEndpoints();
    }

    DatabaseFactory.init()
    JacksonFactory.init()

}

