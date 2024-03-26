package kz.sagengaliyev.univerbackend.repository

import kz.sagengaliyev.univerbackend.model.Course
import kz.sagengaliyev.univerbackend.model.Teacher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface TeacherRepository : JpaRepository<Teacher, Long> {
    @Query("SELECT t FROM Teacher t JOIN FETCH t.coursesTaught WHERE t.id = :teacherId")
    fun findByIdWithCourses(@Param("teacherId") teacherId: Long): Teacher

    fun findTeachersByCoursesTaught(course: Course) : Set<Teacher>


}