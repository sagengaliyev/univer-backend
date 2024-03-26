package kz.sagengaliyev.univerbackend.dto

import java.time.LocalDateTime


data class CourseDTO(
    val id: Long?,
    val name: String,
    val duration: Int,
    val code: String,
    val format: String,
    val teachers: List<TeacherShortDTO>,
    val students: List<StudentShortDTO>,
    val createdDate: LocalDateTime,
    val isActive: Boolean,
    val deleted: Boolean,

)
