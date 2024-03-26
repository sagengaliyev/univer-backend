package kz.sagengaliyev.univerbackend.dto

import java.util.UUID

data class TeacherShortDTO(
    val id: UUID?,
    val fullName: String,
    val grade: String,
)
