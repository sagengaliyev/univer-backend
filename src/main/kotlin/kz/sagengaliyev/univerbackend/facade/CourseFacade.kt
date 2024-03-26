package kz.sagengaliyev.univerbackend.facade

import kz.sagengaliyev.univerbackend.pagination.PageDTO
import kz.sagengaliyev.univerbackend.request.CourseCreationRequest
import kz.sagengaliyev.univerbackend.dto.CourseDTO
import kz.sagengaliyev.univerbackend.dto.CourseShortDTO
import kz.sagengaliyev.univerbackend.request.CourseUpdateRequest
import kz.sagengaliyev.univerbackend.service.CourseService

interface CourseFacade {
    fun create(dto: CourseCreationRequest) : CourseDTO

    fun findById(id: Long) : CourseDTO

    fun findAll(pageNumber: Int, pageSize: Int) : PageDTO<CourseDTO>

    fun setActivityStatus(id:Long, isActive: Boolean)

    fun update(id: Long, dto: CourseUpdateRequest) : CourseShortDTO

    fun delete(id: Long)

    fun deleteAllStudentsOfTheCourse(id: Long)

    fun deleteAllTeachersOfTheCourse(id: Long)
}