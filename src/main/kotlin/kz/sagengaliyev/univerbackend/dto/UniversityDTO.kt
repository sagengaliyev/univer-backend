package kz.sagengaliyev.univerbackend.dto

import java.time.LocalDateTime

data class UniversityDTO(
    val id: Long?,
    val name: String,
    val address: String,
    val rector: String,
    val createdDate: LocalDateTime
)
