package br.com.rafaelstelles.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class EventRequestDTO @JsonCreator
constructor(@param:JsonProperty(value = "action") val action: String,
            @param:JsonProperty(value = "issue") val issue: IssueDTO)

data class IssueDTO @JsonCreator
constructor(@param:JsonProperty(value = "id") val id: Int,
            @param:JsonProperty(value = "number") val number: String,
            @param:JsonProperty(value = "title") val title: String,
            @param:JsonProperty(value = "created_at") val createdAt: String,
            @param:JsonProperty(value = "updated_at") val updatedAt: String,
            @param:JsonProperty(value = "user") val user: UserDTO)


data class UserDTO @JsonCreator
constructor(@param:JsonProperty(value = "id") val id: Int,
            @param:JsonProperty(value = "login") val login: String,
            @param:JsonProperty(value = "url") val url: String)
