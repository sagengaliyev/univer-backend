package kz.sagengaliyev.univerbackend.mapper

import kz.sagengaliyev.univerbackend.dto.TeacherDTO
import kz.sagengaliyev.univerbackend.request.TeacherCreationRequest
import kz.sagengaliyev.univerbackend.model.Teacher
import kz.sagengaliyev.univerbackend.request.TeacherUpdateRequest
import kz.sagengaliyev.univerbackend.service.CourseService
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime

fun TeacherCreationRequest.toEntity() : Teacher {
    return Teacher(
        grade = this.grade,
        fullName = this.fullName,
        coursesTaught = mutableSetOf(),
        createdDate = LocalDateTime.now(),
        isDeleted = false
    )
}

fun Teacher.toDto() : TeacherDTO {
    return TeacherDTO(
        id = this.id,
        grade = this.grade,
        fullName = this.fullName,
        createdDate = this.createdDate,
        isDeleted = this.isDeleted,
        coursesTaught = this.coursesTaught.map { it.toShortDto() }.toList()
    )
}

fun TeacherUpdateRequest.update(
    teacher: Teacher
) : Teacher {
    this.fullName?.let { teacher.fullName = it }
    this.grade?.let { teacher.grade = it }
    return teacher
}