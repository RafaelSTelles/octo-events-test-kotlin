package br.com.rafaelstelles.dto

import com.fasterxml.jackson.annotation.JsonProperty

class EventResponse(val action: String, @get:JsonProperty("created_at") val createdAt: String)
