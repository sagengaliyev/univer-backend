package kz.sagengaliyev.univerbackend.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class TeacherCreationRequest @JsonCreator constructor (
    @JsonProperty("fullName") val fullName: String,
    @JsonProperty("grade") val grade: String
)