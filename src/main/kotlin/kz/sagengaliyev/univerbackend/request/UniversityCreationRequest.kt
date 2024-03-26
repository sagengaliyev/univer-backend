package kz.sagengaliyev.univerbackend.request

import com.fasterxml.jackson.annotation.JsonCreator

data class UniversityCreationRequest @JsonCreator constructor(
    val name : String,
    val address: String,
    val rector : String
)
