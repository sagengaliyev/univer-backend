package kz.sagengaliyev.univerbackend.facade.impl

import kz.sagengaliyev.univerbackend.dto.TeacherDTO
import kz.sagengaliyev.univerbackend.exception.CanNotAssignTeacherToACourse
import kz.sagengaliyev.univerbackend.exception.CanNotEnrollStudentToCourse
import kz.sagengaliyev.univerbackend.facade.TeacherFacade
import kz.sagengaliyev.univerbackend.request.AppointACourseTeacherRequest
import kz.sagengaliyev.univerbackend.request.TeacherCreationRequest
import kz.sagengaliyev.univerbackend.service.CourseService
import kz.sagengaliyev.univerbackend.service.TeacherService
import kz.sagengaliyev.univerbackend.mapper.toDto
import kz.sagengaliyev.univerbackend.mapper.toEntity
import kz.sagengaliyev.univerbackend.mapper.update
import kz.sagengaliyev.univerbackend.model.Course
import kz.sagengaliyev.univerbackend.pagination.PageDTO
import kz.sagengaliyev.univerbackend.pagination.PageableFactory
import kz.sagengaliyev.univerbackend.request.TeacherUpdateRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

open class TeacherFacadeImpl(
    private val teacherService: TeacherService,
    private val courseService: CourseService,
    private val log: Logger = LoggerFactory.getLogger(TeacherFacadeImpl::class.java)
) : TeacherFacade {

    @Transactional
    override fun create(dto: TeacherCreationRequest): TeacherDTO {
        val teacher = dto.toEntity()
        teacherService.save(teacher)
        log.info("Creating a new teacher with ID: ${teacher.id}")
        return teacher.toDto()
    }

    override fun findById(id: Long): TeacherDTO {
        log.info("Extracting teacher details with ID: $id")
        return teacherService.findById(id).toDto()
    }

    @Transactional(readOnly = true)
    override fun findAll(pageNumber: Int, pageSize: Int): PageDTO<TeacherDTO> {
        val pageable = PageableFactory.create(pageNumber, pageSize)
        val teachers: Page<TeacherDTO> = teacherService
            .findAll(pageable)
            .map { it.toDto() }
        log.info("Extracting the list of all teachers")
        return PageDTO(teachers)
    }

    @Transactional
    override fun appointACourseTeacher(id: Long, dto: AppointACourseTeacherRequest): TeacherDTO {
        val teacher = teacherService.findById(id)
        val course = courseService.findById(dto.courseId)
        if (!teacher.equals(null) && !course.equals(null)) {
            teacher.coursesTaught.plus(course)
            log.info("Assigning teacher with ID: $id to course with ID: ${dto.courseId}")
            return teacherService.save(teacher).toDto()
        } else if (!teacher.equals(null) && course.equals(null)) {
            log.warn("An exception occurred while trying to assign a teacher with ID: $id to course with ID: ${dto.courseId}" +
                    "The reason is a non-existent course with ID: $id")
            throw CanNotAssignTeacherToACourse("Нельзя зачислить студента с ID: $id на курс, так как курс с ID: ${dto.courseId} не существует!")
        } else {
            log.warn("An exception occurred while trying to assign a teacher with ID: $id to course with ID: ${dto.courseId}" +
                    "The reason is a non-existent teacher with ID: $id")
            throw CanNotAssignTeacherToACourse("Нельзя зачислить студента с ID: $id на курс, так как данный студент не существует!")
        }
    }

    @Transactional
    override fun delete(id: Long) {
        log.info("Deleting teacher with ID: $id")
        teacherService.delete(id)
    }

    @Transactional
    override fun update(id: Long, dto: TeacherUpdateRequest): TeacherDTO {
        val teacher = teacherService.findById(id)
        val updatedTeacher = dto.update(teacher)
        val newCourses = mutableListOf<Course>()
        if (dto.listOfCourses?.isNotEmpty() == true) {
            for (i in dto.listOfCourses!!) {
                val course =courseService.findById(i)
                newCourses.add(course)
            }
            teacher.coursesTaught.clear()
        }
        updatedTeacher.coursesTaught.addAll(newCourses)
        log.info("Updating a teacher with ID: $id")
        teacherService.save(updatedTeacher)
        return updatedTeacher.toDto()
    }
}