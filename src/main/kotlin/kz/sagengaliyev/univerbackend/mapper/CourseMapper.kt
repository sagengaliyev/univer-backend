package kz.sagengaliyev.univerbackend.mapper

import kz.sagengaliyev.univerbackend.dto.*
import kz.sagengaliyev.univerbackend.request.CourseCreationRequest
import kz.sagengaliyev.univerbackend.enums.CourseFormat
import kz.sagengaliyev.univerbackend.model.Course
import kz.sagengaliyev.univerbackend.request.CourseUpdateRequest
import java.time.LocalDateTime

fun CourseCreationRequest.toEntity() : Course {
    return Course(
        name = this.name,
        code = this.code,
        duration = this.duration,
        format = CourseFormat.valueOf(this.format),
        students = mutableSetOf(),
        teachers = mutableSetOf(),
        isActive = true,
        createdDate = LocalDateTime.now(),
        deleted = false
    )
}

fun Course.toDto() : CourseDTO {
    return CourseDTO(
        id = this.id,
        name = this.name,
        duration = this.duration,
        code = this.code,
        format = this.format.toString(),
        teachers = this.teachers.map { TeacherShortDTO(it.id, it.fullName, it.grade) },
        students = this.students.map { StudentShortDTO(it.id, it.firstName, it.lastName, it.age, it.year, it.gpa) },
        createdDate = this.createdDate,
        deleted = this.deleted,
        isActive = this.isActive
    )
}

fun Course.toShortDto() : CourseShortDTO {
    return CourseShortDTO(
        id = this.id,
        name = this.name,
        code = this.code,
        format = this.format.toString(),
        duration = this.duration,
        createdDate = this.createdDate,
        deleted = this.deleted,
        isActive = this.isActive
    )
}

fun CourseUpdateRequest.update(course: Course) : Course {
    this.name?.let { course.name = it }
    this.code?.let { course.code = it }
    this.format?.let { course.format = CourseFormat.valueOf(it) }
    this.duration?.let { course.duration = it }

    return course
}