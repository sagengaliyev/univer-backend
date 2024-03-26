package kz.sagengaliyev.univerbackend.request

import kz.sagengaliyev.univerbackend.dto.FileDTO
import kz.sagengaliyev.univerbackend.enums.AssignmentStatus
import java.time.LocalDateTime

data class AssignmentUpdateRequest(
    var title: String? = null,
    var description: String? = null,
    var attachedFiles: List<FileDTO>? = null,
    var deadline: LocalDateTime? = null,
    var status: String?= null
)
