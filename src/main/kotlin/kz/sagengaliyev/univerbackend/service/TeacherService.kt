package kz.sagengaliyev.univerbackend.service


import kz.sagengaliyev.univerbackend.model.Course
import kz.sagengaliyev.univerbackend.model.Teacher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface TeacherService {
    fun findById(id: Long) : Teacher

    fun existById(id: Long) : Boolean

    fun save(teacher: Teacher) : Teacher

    fun findAll(pageable: Pageable) : Page<Teacher>

    fun delete(id: Long)

    fun findAllByCourse(course: Course) : Set<Teacher>

    fun saveAll(teachers: Collection<Teacher>)
}