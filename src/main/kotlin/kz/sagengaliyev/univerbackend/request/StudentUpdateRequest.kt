package kz.sagengaliyev.univerbackend.request

data class StudentUpdateRequest(
    var firstName: String? = null,
    var lastName: String? = null,
    var age: Int? = null,
    var year: Int? = null,
    var gpa: Double? = null,
    var universityId: Long? = null
)
