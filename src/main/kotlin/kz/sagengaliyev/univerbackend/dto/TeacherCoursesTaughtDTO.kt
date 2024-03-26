package kz.sagengaliyev.univerbackend.dto

import java.time.LocalDateTime
import java.util.UUID

data class TeacherCoursesTaughtDTO(
    val id: UUID?,
    val name: String,
    val duration: Int,
    val code: String,
    val format: String,
    val createdDate: LocalDateTime,
    val isActive: Boolean,
    val deleted: Boolean,
)
