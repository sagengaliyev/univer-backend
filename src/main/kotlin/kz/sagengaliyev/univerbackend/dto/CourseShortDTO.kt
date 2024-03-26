package kz.sagengaliyev.univerbackend.dto

import java.time.LocalDateTime

data class CourseShortDTO(
    val id: Long?,
    val name: String,
    val duration: Int,
    val code: String,
    val format: String,
    val createdDate: LocalDateTime,
    val isActive: Boolean,
    val deleted: Boolean,
)