package kz.sagengaliyev.univerbackend.mapper

import kz.sagengaliyev.univerbackend.dto.StudentDTO
import kz.sagengaliyev.univerbackend.request.StudentCreationRequest
import kz.sagengaliyev.univerbackend.request.StudentUpdateRequest
import kz.sagengaliyev.univerbackend.model.Student
import java.time.LocalDateTime

fun StudentCreationRequest.toEntity() : Student {
    return Student(
        firstName = this.firstName,
        lastName = this.lastName,
        age = this.age,
        year = this.year,
        gpa = this.gpa,
        enrollmentDate = LocalDateTime.now(),
        deductionDate = null,
        isExpelled = false,
        isEnrolled = true,
        isDeleted = false,
        universityId = this.universityId
    )
}

fun StudentUpdateRequest.update(
    student: Student
) : Student {

    this.firstName?.let { student.firstName = it }
    this.lastName?.let { student.lastName = it }
    this.age?.let { student.age = it }
    this.gpa?.let { student.gpa = it }
    this.year?.let { student.year = it }
    this.universityId?.let { student.universityId }
    return student

//    return student.copy(
//        firstName = this.firstName?.let { this.firstName } ?: student.firstName,
//        lastName = studentUpdateDTO.lastName?.let({ lastName = studentUpdateDTO.lastName }.toString()),
//        age = studentUpdateDTO.age?.let { age = studentUpdateDTO.age }.toString(),
//        year = studentUpdateDTO.year?.let { year = studentUpdateDTO.year }.toString(),
//        gpa = studentUpdateDTO.gpa?.let { gpa = studentUpdateDTO.gpa }.toString(),,
//        enrollmentDate = LocalDateTime.now()?.let { firstName   }.toString(),,
//        isExpelled = false,
//        isEnrolled = true,
//        isDeleted = false,
//        universityId = studentUpdateDTO.universityId?.let { firstName = studentUpdateDTO.firstName }.toString()
//    )
}

//    return Student(
//        firstName = this.firstName,
//        lastName = this.lastName,
//        age = this.age,
//        year = this.year,
//        gpa = this.gpa,
//        enrollmentDate = LocalDateTime.now(),
//        isExpelled = false,
//        isEnrolled = true,
//        isDeleted = false,
//        universityId = this.universityId
//    )
//}

fun Student.toDto() : StudentDTO {
    return StudentDTO(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        age = this.age,
        year = this.year,
        gpa = this.gpa,
        enrollmentDate = this.enrollmentDate,
        deductionDate = this.deductionDate,
        isExpelled = this.isExpelled,
        isEnrolled = this.isEnrolled,
        isDeleted = this.isDeleted,
        coursesEnrolled = this.coursesEnrolled.map { it.toDto() },
        universityId = this.universityId
    )
}


