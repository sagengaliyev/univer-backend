package kz.sagengaliyev.univerbackend.dto

import java.time.LocalDateTime
import java.util.UUID

data class AssignmentDTO(
    val id: UUID? = null,
    val title: String? = null,
    val description: String? = null,
    val attachedFiles: List<FileDTO>? = null,
    val teacherID: String,
    val createdDate: LocalDateTime,
    val deadline: LocalDateTime,
    val status: String,
    val isDeleted: Boolean
)
