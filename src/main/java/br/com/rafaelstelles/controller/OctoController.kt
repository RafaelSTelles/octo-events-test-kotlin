package br.com.rafaelstelles.controller

import br.com.rafaelstelles.dto.EventRequest
import br.com.rafaelstelles.service.EventService
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.apibuilder.EndpointGroup

class OctoEndpoints(val service: EventService): EndpointGroup {

    override fun addEndpoints() {
        post("/events") { ctx ->
            val request = ctx.bodyAsClass(EventRequest::class.java)
            ctx.json(service.create(request))
        }
        get("/issues/:number/events") { ctx ->
            val number = ctx.pathParam("number")
            ctx.json(service.findAllEventDTOByNumber(number));
        }
    }
}