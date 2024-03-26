package kz.sagengaliyev.univerbackend.dto

import java.util.UUID

data class StudentShortDTO(
    val id: UUID?,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val year: Int,
    val gpa: Double
)
