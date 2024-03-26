package kz.sagengaliyev.univerbackend.request

import kz.sagengaliyev.univerbackend.dto.FileDTO
import java.time.LocalDateTime

data class AssignmentCreationRequest(
    val title: String,
    var description: String,
    var deadline: LocalDateTime,
    var attachedFiles: List<FileDTO>
)