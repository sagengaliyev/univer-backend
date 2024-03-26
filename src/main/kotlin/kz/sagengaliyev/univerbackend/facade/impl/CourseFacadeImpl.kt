package kz.sagengaliyev.univerbackend.facade.impl

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import kz.sagengaliyev.univerbackend.facade.CourseFacade
import kz.sagengaliyev.univerbackend.mapper.toDto
import kz.sagengaliyev.univerbackend.mapper.toEntity
import kz.sagengaliyev.univerbackend.request.CourseCreationRequest
import kz.sagengaliyev.univerbackend.service.CourseService
import kz.sagengaliyev.univerbackend.dto.CourseDTO
import kz.sagengaliyev.univerbackend.dto.CourseShortDTO
import kz.sagengaliyev.univerbackend.exception.CanNotDeleteStudentsOfCourse
import kz.sagengaliyev.univerbackend.exception.CanNotDeleteTeachersOfCourse
import kz.sagengaliyev.univerbackend.mapper.toShortDto
import kz.sagengaliyev.univerbackend.mapper.update
import kz.sagengaliyev.univerbackend.model.Student
import kz.sagengaliyev.univerbackend.pagination.PageDTO
import kz.sagengaliyev.univerbackend.pagination.PageableFactory
import kz.sagengaliyev.univerbackend.request.CourseUpdateRequest
import kz.sagengaliyev.univerbackend.service.StudentService
import kz.sagengaliyev.univerbackend.service.TeacherService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.annotation.Transactional

open class CourseFacadeImpl (
    private val courseService: CourseService,
    private val log: Logger = LoggerFactory.getLogger(CourseFacadeImpl::class.java),
    private val studentService: StudentService,
    private val teacherService: TeacherService
) : CourseFacade {

    @Transactional
    override fun create(dto: CourseCreationRequest): CourseDTO {
        log.info("Creating new course with ID: ${dto.id} and name: ${dto.name}")
        return courseService.save(dto.toEntity()).toDto()
    }

    override fun findById(id: Long): CourseDTO {
        log.info("Extracting course details with id: $id")
        return courseService.findById(id).toDto()
    }

    override fun findAll(pageNumber: Int, pageSize: Int): PageDTO<CourseDTO> {
        val pageable = PageableFactory.create(pageNumber, pageSize)
        val courses: Page<CourseDTO> = courseService.findAll(pageable).map { it.toDto() }
        log.info("Extracting the list of all courses")
        return PageDTO(courses)
    }

    @Transactional
    override fun setActivityStatus(id: Long, isActive: Boolean) {
        val course = courseService.findById(id)
        course.isActive = isActive
        log.info("Changing activity status of course with ID: $id")
        courseService.save(course)
    }

    @Transactional
    override fun update(id: Long, dto: CourseUpdateRequest) : CourseShortDTO {
        val course = courseService.findById(id)
        val updatedCourse = dto.update(course)
        courseService.save(updatedCourse)
        log.info("Updating course details with ID: $id")
        return updatedCourse.toShortDto()
    }

    @Transactional
    override fun delete(id: Long) {
        val course = courseService.findById(id)
        log.info("Deleting course with ID: $id")
        courseService.delete(course)
    }

    @Transactional
    override fun deleteAllStudentsOfTheCourse(id: Long) {
        val course = courseService.findById(id)
        val students: Collection<Student> = studentService.findStudentsByCourseId(course)
        if (!course.equals(null)) {
            students.forEach { student -> student.coursesEnrolled.removeIf { course -> course.id == id } }
            log.info("Deleting all students of course with ID: $id")
            studentService.saveAll(students)
            courseService.save(course)
        } else {
            log.warn("An exception occurred while deleting all students of course with ID: $id." +
                    "The reason is: non existent course")
            throw CanNotDeleteStudentsOfCourse("Невозможно удалить студентов курса, так как данный курс с ID: $id не существует!")
        }
    }

    @Transactional
    override fun deleteAllTeachersOfTheCourse(id: Long) {
        val course = courseService.findById(id)
        val teachers = teacherService.findAllByCourse(course)
        if (!course.equals(null)) {
            teachers.forEach { teacher -> teacher.coursesTaught.removeIf { course -> course.id == id } }
            log.info("Deleting all teachers of course with ID: $id")
            teacherService.saveAll(teachers)
            courseService.save(course)
        } else {
            log.warn("An exception occurred while deleting all teachers of course with ID: $id." +
                    "The reason is: non existent course")
            throw CanNotDeleteTeachersOfCourse("Невозможно удалить учителей курса, так как данный курс с ID: $id не существует!")
        }
    }

}