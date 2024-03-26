package kz.sagengaliyev.univerbackend.service

import kz.sagengaliyev.univerbackend.model.Course
import kz.sagengaliyev.univerbackend.model.Student
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface StudentService {
    fun save(student: Student) : Student

    fun findById(id: Long) : Student

    fun findAll(pageable: Pageable) : Page<Student>

    fun deleteAllStudentsByUniversity(id: Long)

    fun search(firstName: String?,
               lastName: String?,
               age: Int?,
               year: Int?,
               gpa: Double?,
               sortByYear: String?,
               sortByGpa: String?,
               pageable: Pageable
    ) : Page<Student>

    fun delete(id: Long)

    fun existsById(id: Long) : Boolean

    fun findStudentsByCourseId(course: Course) : Set<Student>

    fun saveAll(students: Collection<Student>)

}