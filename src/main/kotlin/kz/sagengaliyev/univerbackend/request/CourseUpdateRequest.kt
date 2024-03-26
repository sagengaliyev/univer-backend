package kz.sagengaliyev.univerbackend.request

data class CourseUpdateRequest(
    var name: String? = null,
    var duration: Int? = null,
    var code: String? = null,
    var format: String? = null
)
