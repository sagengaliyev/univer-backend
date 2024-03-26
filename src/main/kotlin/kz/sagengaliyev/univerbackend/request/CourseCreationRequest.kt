package kz.sagengaliyev.univerbackend.request

data class CourseCreationRequest (
    val id: Long?,
    val name: String,
    val duration: Int,
    val code: String,
    val format: String
)