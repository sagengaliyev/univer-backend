package kz.sagengaliyev.univerbackend.repository

import kz.sagengaliyev.univerbackend.model.Course
import kz.sagengaliyev.univerbackend.model.Student
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository : JpaRepository<Student, Long> {
    fun deleteAllByUniversityId(id: Long)

    @Query("SELECT s FROM Student s WHERE " +
            "(COALESCE(:firstName, '') = '' OR lower(s.firstName) like lower(concat('%', :firstName, '%'))) AND " +
            "(COALESCE(:lastName, '') = '' OR s.lastName = :lastName) AND " +
            "(COALESCE(:age, -1) = -1 OR s.age = :age) AND " +
            "(COALESCE(:year, -1) = -1 OR s.year = :year)" +
            "AND (:gpa IS NULL OR s.gpa = :gpa) " +
            "ORDER BY " +
            "CASE WHEN :sortByYear = 'desc' THEN s.year END DESC, " +
            "CASE WHEN :sortByYear = 'asc' THEN s.year END ASC, " +
            "CASE WHEN :sortByGpa = 'desc' THEN s.gpa END DESC, " +
            "CASE WHEN :sortByGpa = 'asc' THEN s.gpa END ASC"
    )
    fun search(
        firstName: String?,
        lastName: String?,
        age: Int?,
        year: Int?,
        gpa: Double?,
        sortByYear: String?,
        sortByGpa: String?,
        pageable: Pageable
    ) : Page<Student>

    fun findStudentsByCoursesEnrolled(course: Course) : Set<Student>
}