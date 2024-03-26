package kz.sagengaliyev.univerbackend.model

import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "students")
@SQLDelete(sql = "update students set is_deleted = true where id=?")
data class Student(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID? = null,

    @Column(name = "first_name", nullable = false)
    var firstName: String,

    @Column(name = "last_name", nullable = false)
    var lastName: String,

    @Column(name = "age", nullable = false)
    var age: Int,

    @Column(name = "year", nullable = false)
    var year: Int,

    @Column(name = "gpa", nullable = false)
    var gpa: Double,

    @Column(name = "is_enrolled", nullable = false)
    var isEnrolled: Boolean = true,

    @Column(name = "is_expelled", nullable = false)
    var isExpelled: Boolean = false,

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "university_id", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    var university: University,

    @Column(name = "university_id", nullable = false)
    var universityId: Long,

    @Column(name = "enrollment_date", nullable = false)
    var enrollmentDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "deduction_date")
    var deductionDate: LocalDateTime? = null,

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE])
    @JoinTable(
        name = "student_courses",
        joinColumns = [JoinColumn(name = "student_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "course_id", referencedColumnName = "id")]
    )
    val coursesEnrolled: MutableSet<Course> = mutableSetOf(),

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Student

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}