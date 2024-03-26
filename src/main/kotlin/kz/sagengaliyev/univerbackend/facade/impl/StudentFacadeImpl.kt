package kz.sagengaliyev.univerbackend.facade.impl

import kz.sagengaliyev.univerbackend.dto.StudentDTO
import kz.sagengaliyev.univerbackend.exception.CanNotDeductNonExistentStudent
import kz.sagengaliyev.univerbackend.exception.CanNotEnrollStudentToCourse
import kz.sagengaliyev.univerbackend.facade.StudentFacade
import kz.sagengaliyev.univerbackend.request.EnrollStudentToCourseRequest
import kz.sagengaliyev.univerbackend.request.StudentCreationRequest
import kz.sagengaliyev.univerbackend.request.StudentUpdateRequest
import kz.sagengaliyev.univerbackend.service.CourseService
import kz.sagengaliyev.univerbackend.service.StudentService
import kz.sagengaliyev.univerbackend.service.UniversityService
import kz.sagengaliyev.univerbackend.exception.YouCanNotCreateANewStudentException
import kz.sagengaliyev.univerbackend.mapper.toDto
import kz.sagengaliyev.univerbackend.mapper.toEntity
import kz.sagengaliyev.univerbackend.mapper.update
import kz.sagengaliyev.univerbackend.pagination.PageDTO
import kz.sagengaliyev.univerbackend.pagination.PageableFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.transaction.annotation.Transactional

open class StudentFacadeImpl(
    private val studentService: StudentService,
    private val universityService: UniversityService,
    private val courseService: CourseService,
    private val log: Logger = LoggerFactory.getLogger(StudentFacadeImpl::class.java)
) : StudentFacade {

    @Transactional
    override fun create(studentCreationRequest: StudentCreationRequest) : StudentDTO {
        val student = studentCreationRequest.toEntity()
        if (universityService.existById(studentCreationRequest.universityId)) {
            log.info("Creating a new student with ID: ${student.id}")
            studentService.save(student)
        } else {
            log.warn("An exception occurred when trying to create a new student" +
                    "The reason is a non-existent ID of university: ${studentCreationRequest.universityId}")
            throw YouCanNotCreateANewStudentException("Нельзя создать студента, так как данного универа с ID: ${studentCreationRequest.universityId} не существует!")
        }
        return student.toDto()

    }

    override fun findById(id: Long) : StudentDTO {
        val student = studentService.findById(id)
        log.info("Extracting student details with ID: $id")
        return student.toDto()
    }

    @Transactional
    override fun update(id: Long, studentUpdateRequest: StudentUpdateRequest) : StudentDTO {
        val student = studentService.findById(id)
        val updatedStudent = studentUpdateRequest.update(student)
        log.info("Updating student details with ID: $id")
        studentService.save(updatedStudent)
        return student.toDto()
    }

    @Transactional
    override fun deleteAllStudentsOfUniversity(universityId: Long) {
        log.info("Deleting students by their university with ID: $universityId")
        studentService.deleteAllStudentsByUniversity(universityId)
    }

    override fun findAll(pageNumber: Int, pageSize: Int): PageDTO<StudentDTO> {
        val pageable: Pageable = PageableFactory.create(pageNumber, pageSize)
        val students: Page<StudentDTO> = studentService
            .findAll(pageable)
            .map { it.toDto() }
        log.info("Extracting the list of all students")
        return PageDTO(students)

    }

    override fun search(firstName: String?,
                        lastName: String?,
                        age: Int?,
                        year: Int?,
                        gpa: Double?,
                        sortByYear: String?,
                        sortByGpa: String?,
                        pageNumber: Int,
                        pageSize: Int): PageDTO<StudentDTO> {
        val pageable: Pageable = PageableFactory.create(pageNumber, pageSize)
        val students: Page<StudentDTO> = studentService
            .search(firstName, lastName, age, year, gpa, sortByYear, sortByGpa, pageable)
            .map { it.toDto() }
        log.info("Extracting the list of all students with search parameters")
        return PageDTO(students)
    }

    @Transactional
    override fun delete(id: Long) {
        log.info("Deleting a student with ID: $id")
        studentService.delete(id)
    }

    @Transactional
    override fun deduct(id: Long) : StudentDTO {
        val student = studentService.findById(id)
        if (!student.equals(null)) {
            student.isExpelled = true
            student.isEnrolled = false
            log.info("Deducting student with ID: $id")
            val expelledStudent = studentService.save(student)
            return expelledStudent.toDto()
        }
        log.warn("An exception occurred when trying to deduct a student" +
                "The reason is a non-existent student with ID: $id")
        throw CanNotDeductNonExistentStudent("Нельзя отчислить студента с ID: $id, так как его не существует!")

    }

    @Transactional
    override fun enrollToCourse(id: Long, dto: EnrollStudentToCourseRequest): StudentDTO {
        val student = studentService.findById(id)
        val course = courseService.findById(dto.courseId)
        if (!student.equals(null) && !course.equals(null)) {
            student.coursesEnrolled.plus(course)
            log.info("Enrolling to course a student with ID: $id")
            return studentService.save(student).toDto()
        } else if (!student.equals(null) && course.equals(null)) {
            log.warn("An exception occurred while trying to enroll a student with ID: $id to course with ID: ${dto.courseId}" +
                    "The reason is a non-existent course with ID: $id")
            throw CanNotEnrollStudentToCourse("Нельзя зачислить студента с ID: $id на курс, так как курс с ID: ${dto.courseId} не существует!")
        } else {
            log.warn("An exception occurred while trying to enroll a student with ID: $id to course with ID: ${dto.courseId}" +
                    "The reason is a non-existent student with ID: $id")
            throw CanNotEnrollStudentToCourse("Нельзя зачислить студента с ID: $id на курс, так как данный студент не существует!")
        }
    }
}