package kz.sagengaliyev.univerbackend.service.impl

import kz.sagengaliyev.univerbackend.service.CourseService
import kz.sagengaliyev.univerbackend.exception.EntityNotFoundException
import kz.sagengaliyev.univerbackend.model.Course
import kz.sagengaliyev.univerbackend.repository.CourseRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class CourseServiceImpl (
    private val courseRepository: CourseRepository,
    private val log: Logger = LoggerFactory.getLogger(CourseServiceImpl::class.java)
) : CourseService {
    override fun findById(id: Long): Course {
        return courseRepository
            .findById(id)
            .orElseThrow{
                log.warn("An exception occurred when trying to search for the Course entity by ID : $id. " +
                        "The reason is a non-existent ID")
                (EntityNotFoundException("Курс по данному $id не найден!"))
            }
    }

    override fun existById(id: Long): Boolean {
        val exists = courseRepository.existsById(id)
        if (!exists) {
            log.warn("Course with ID : $id doesn't exist!")
        }
        return exists
    }

    override fun save(course: Course) : Course{
       return courseRepository.save(course)
    }

    override fun findAll(pageable: Pageable): Page<Course> {
        return courseRepository.findAll(pageable)
    }

    override fun delete(course: Course) {
        courseRepository.delete(course)
    }
}