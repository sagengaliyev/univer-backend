package kz.sagengaliyev.univerbackend.service.impl

import kz.sagengaliyev.univerbackend.service.TeacherService
import kz.sagengaliyev.univerbackend.exception.EntityNotFoundException
import kz.sagengaliyev.univerbackend.model.Course
import kz.sagengaliyev.univerbackend.model.Teacher
import kz.sagengaliyev.univerbackend.repository.TeacherRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.transaction.annotation.Transactional

open class TeacherServiceImpl (
    private val teacherRepository: TeacherRepository
) : TeacherService {

    @Transactional(readOnly = true)
    override fun findById(id: Long): Teacher {
        return teacherRepository
            .findByIdWithCourses(id)
//            .orElseThrow { EntityNotFoundException("Учитель с данным $id не найден!") }
    }

    override fun existById(id: Long): Boolean {
        return teacherRepository.existsById(id)
    }

    override fun save(teacher: Teacher) : Teacher {
        return teacherRepository.save(teacher)
    }

    override fun findAll(pageable: Pageable): Page<Teacher> {
        return teacherRepository.findAll(pageable)
    }

    override fun delete(id: Long) {
        teacherRepository.deleteById(id)
    }

    override fun findAllByCourse(course: Course) : Set<Teacher> {
        return teacherRepository.findTeachersByCoursesTaught(course)
    }

    override fun saveAll(teachers: Collection<Teacher>) {
        teacherRepository.saveAll(teachers)
    }
}