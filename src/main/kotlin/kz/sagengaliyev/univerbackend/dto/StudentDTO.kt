package kz.sagengaliyev.univerbackend.dto

import java.time.LocalDateTime
import java.util.UUID

data class StudentDTO(
    val id: UUID?,
    var firstName: String,
    val lastName: String,
    var age: Int,
    var year: Int,
    var gpa: Double,
    var isEnrolled: Boolean,
    var isExpelled: Boolean,
    var universityId: Long? = null,
    var enrollmentDate: LocalDateTime,
    var deductionDate: LocalDateTime? = null,
    var coursesEnrolled: List<CourseDTO>,
    var isDeleted: Boolean,

    )
