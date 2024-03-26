package kz.sagengaliyev.univerbackend.mapper

import kz.sagengaliyev.univerbackend.dto.AssignmentDTO
import kz.sagengaliyev.univerbackend.enums.AssignmentStatus
import kz.sagengaliyev.univerbackend.model.Assignment
import kz.sagengaliyev.univerbackend.request.AssignmentCreationRequest
import kz.sagengaliyev.univerbackend.request.AssignmentUpdateRequest
import kz.sagengaliyev.univerbackend.util.SecurityUtils
import java.time.LocalDateTime
import java.util.*

fun AssignmentCreationRequest.toEntity() : Assignment {

    return Assignment(
        title = this.title,
        description = this.description,
        deadline = this.deadline,
        createdDate = LocalDateTime.now(),
        userID = UUID.fromString(SecurityUtils.getAuthenticatedUserId()),
        status = AssignmentStatus.AWAITING_ANSWER
    )
}

fun Assignment.toDTO() : AssignmentDTO {
    return AssignmentDTO(
        id = this.id,
        title = this.title,
        description = this.description,
        deadline = this.deadline,
        createdDate = this.createdDate,
        teacherID = this.userID.toString(),
        status = this.status.toString(),
        isDeleted = this.isDeleted,
        attachedFiles = this.attachedFiles.toList().map { it.toDto() }
    )
}

fun AssignmentUpdateRequest.update(
    assignment: Assignment
) : Assignment {
    this.title?.let { assignment.title = it }
    this.description?.let { assignment.description = it }
    this.deadline?.let { assignment.deadline = it }
    this.status?.let { assignment.status = AssignmentStatus.valueOf(it) }
    return assignment
}