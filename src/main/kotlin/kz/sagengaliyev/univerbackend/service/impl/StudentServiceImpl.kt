package kz.sagengaliyev.univerbackend.service.impl

import kz.sagengaliyev.univerbackend.service.StudentService
import kz.sagengaliyev.univerbackend.exception.EntityNotFoundException
import kz.sagengaliyev.univerbackend.model.Course
import kz.sagengaliyev.univerbackend.model.Student
import kz.sagengaliyev.univerbackend.repository.StudentRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


class StudentServiceImpl(
    private val studentRepository: StudentRepository,
    private val log: Logger = LoggerFactory.getLogger(StudentServiceImpl::class.java)
) : StudentService {
    override fun save(student: Student) : Student {
        return studentRepository.save(student)
    }

    override fun findById(id: Long): Student {
        return studentRepository
            .findById(id)
            .orElseThrow{
                log.warn("An exception occurred when trying to search for the Student entity by ID : $id. " +
                        "The reason is a non-existent ID")
                EntityNotFoundException("Студент с данным ID: ${id} не найден!")
            }
    }

    override fun findAll(pageable: Pageable): Page<Student> {
        return studentRepository.findAll(pageable)
    }

    override fun deleteAllStudentsByUniversity(id: Long) {
        log.info("Deleting all students by their university...")
        studentRepository.deleteAllByUniversityId(id)
    }

    override fun search(firstName: String?,
                        lastName: String?,
                        age: Int?,
                        year: Int?,
                        gpa: Double?,
                        sortByYear: String?,
                        sortByGpa: String?,
                        pageable: Pageable): Page<Student> {
        return studentRepository.search(firstName, lastName, age, year, gpa, sortByYear, sortByGpa, pageable)
    }

    override fun delete(id: Long) {
        val student = findById(id)
        return studentRepository.delete(student)
    }

    override fun existsById(id: Long) : Boolean {
        return studentRepository.existsById(id)
    }

    override fun findStudentsByCourseId(course: Course): Set<Student> {
        return studentRepository.findStudentsByCoursesEnrolled(course)
    }

    override fun saveAll(students: Collection<Student>) {
        studentRepository.saveAll(students)
    }


}