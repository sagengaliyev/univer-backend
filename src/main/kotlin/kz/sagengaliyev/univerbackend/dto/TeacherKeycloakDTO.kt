package kz.sagengaliyev.univerbackend.dto

import java.util.UUID

data class TeacherKeycloakDTO(
    val id: UUID,
    val fullName: String? = null
)
