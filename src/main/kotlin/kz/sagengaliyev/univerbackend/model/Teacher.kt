package kz.sagengaliyev.univerbackend.model

import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "TEACHERS")
@SQLDelete(sql = "update teachers set is_deleted = true where id=?")
data class Teacher(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID? = null,

    @Column(name = "FULL_NAME")
    var fullName: String,

    @Column(name = "GRADE")
    var grade: String,

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE])
    @JoinTable(
        name = "teacher_courses",
        joinColumns = [JoinColumn(name = "teacher_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "course_id", referencedColumnName = "id")]
    )
    var coursesTaught: MutableSet<Course> = mutableSetOf(),

    @Column(name = "CREATED_DATE", nullable = false)
    var createdDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "IS_DELETED", nullable = false)
    var isDeleted: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Teacher

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    fun deleteCourse(course: Course) {
        this.coursesTaught.remove(course)
        course.teachers.remove(this)
    }

}