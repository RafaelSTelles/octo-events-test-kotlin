package br.com.rafaelstelles.service

import br.com.rafaelstelles.dto.EventRequest
import br.com.rafaelstelles.dto.EventResponse

interface EventService {

    fun create(eventRequest: EventRequest) : Int

    fun findAllEventDTOByNumber(number: String): List<EventResponse>
}