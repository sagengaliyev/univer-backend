package kz.sagengaliyev.univerbackend.service


import kz.sagengaliyev.univerbackend.model.Course
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CourseService {
    fun findById(id: Long) : Course

    fun existById(id: Long) : Boolean

    fun save(course: Course) : Course

    fun findAll(pageable: Pageable) : Page<Course>

    fun delete(course: Course)

}