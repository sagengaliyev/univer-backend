package kz.sagengaliyev.univerbackend.facade

import kz.sagengaliyev.univerbackend.request.StudentCreationRequest
import kz.sagengaliyev.univerbackend.dto.StudentDTO
import kz.sagengaliyev.univerbackend.pagination.PageDTO
import kz.sagengaliyev.univerbackend.request.EnrollStudentToCourseRequest
import kz.sagengaliyev.univerbackend.request.StudentUpdateRequest


interface StudentFacade {
    fun create(studentCreationRequest: StudentCreationRequest) : StudentDTO

    fun findById(id: Long) : StudentDTO

    fun update(id: Long, studentUpdateRequest: StudentUpdateRequest) : StudentDTO

    fun deleteAllStudentsOfUniversity(universityId: Long)

    fun findAll(pageNumber: Int, pageSize: Int) : PageDTO<StudentDTO>

    fun search(
        firstName: String?,
        lastName: String?,
        age: Int?,
        year: Int?,
        gpa: Double?,
        sortByYear: String?,
        sortByGpa: String?,
        pageNumber: Int,
        pageSize: Int
    ) : PageDTO<StudentDTO>

    fun delete(id: Long)

    fun deduct(id: Long) : StudentDTO

    fun enrollToCourse(id: Long, dto: EnrollStudentToCourseRequest) : StudentDTO
}