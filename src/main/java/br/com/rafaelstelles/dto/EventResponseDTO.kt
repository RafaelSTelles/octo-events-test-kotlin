package br.com.rafaelstelles.dto

import com.fasterxml.jackson.annotation.JsonProperty

class EventResponseDTO(val action: String, @get:JsonProperty("created_at") val createdAt: String)
