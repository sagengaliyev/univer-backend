package kz.sagengaliyev.univerbackend.model

import jakarta.persistence.*
import kz.sagengaliyev.univerbackend.enums.CourseFormat
import org.hibernate.annotations.SQLDelete
import java.time.LocalDateTime

@Entity
@Table(name = "COURSES")
@SQLDelete(sql = "update courses set deleted = true where id=?")
data class Course (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "NAME", nullable = false)
    var name: String,

    @Column(name = "DURATION", nullable = false)
    var duration: Int,

    @Column(name = "CODE", nullable = false)
    var code: String,

    @Column(name = "FORMAT", nullable = false)
    @Enumerated(EnumType.STRING)
    var format: CourseFormat,

    @ManyToMany(mappedBy = "coursesTaught", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    var teachers: MutableSet<Teacher> = mutableSetOf(),


    @ManyToMany(mappedBy = "coursesEnrolled", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    var students: MutableSet<Student> = mutableSetOf(),

    @Column(name = "IS_ACTIVE", nullable = false)
    var isActive: Boolean = true,

    @Column(name = "CREATED_DATE", nullable = false)
    var createdDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "DELETED", nullable = false)
    var deleted: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Course

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}