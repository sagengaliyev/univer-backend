package kz.sagengaliyev.univerbackend.repository

import kz.sagengaliyev.univerbackend.model.Course
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CourseRepository : JpaRepository<Course, Long> {
}