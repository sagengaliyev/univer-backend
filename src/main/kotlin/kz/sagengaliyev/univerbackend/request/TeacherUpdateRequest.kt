package kz.sagengaliyev.univerbackend.request

data class TeacherUpdateRequest(
    var fullName: String? = null,
    var grade: String? = null,
    var listOfCourses : Set<Long>? = null
)
