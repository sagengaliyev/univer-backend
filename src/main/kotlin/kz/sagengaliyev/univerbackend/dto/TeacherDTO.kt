package kz.sagengaliyev.univerbackend.dto

import kz.sagengaliyev.univerbackend.model.Course
import java.time.LocalDateTime
import java.util.UUID

data class TeacherDTO(
    val id: UUID?,
    val fullName: String? = null,
    val grade: String? = null,
    val coursesTaught: List<CourseShortDTO>,
    val createdDate: LocalDateTime? = null,
    val isDeleted: Boolean? = null
)
