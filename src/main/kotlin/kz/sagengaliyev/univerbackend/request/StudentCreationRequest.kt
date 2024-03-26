package kz.sagengaliyev.univerbackend.request

data class StudentCreationRequest(
    val firstName: String,
    val lastName: String,
    var age: Int,
    var year: Int,
    var gpa: Double,
    var universityId: Long
)
