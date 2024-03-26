package kz.sagengaliyev.univerbackend.facade

import kz.sagengaliyev.univerbackend.dto.TeacherDTO
import kz.sagengaliyev.univerbackend.pagination.PageDTO
import kz.sagengaliyev.univerbackend.request.AppointACourseTeacherRequest
import kz.sagengaliyev.univerbackend.request.TeacherCreationRequest
import kz.sagengaliyev.univerbackend.request.TeacherUpdateRequest

interface TeacherFacade {
    fun create(dto: TeacherCreationRequest) : TeacherDTO

    fun findById(id: Long) : TeacherDTO

    fun findAll(pageNumber: Int, pageSize: Int) : PageDTO<TeacherDTO>

    fun appointACourseTeacher(id: Long, dto: AppointACourseTeacherRequest) : TeacherDTO

    fun delete(id: Long)

    fun update(id: Long, dto: TeacherUpdateRequest) : TeacherDTO
}