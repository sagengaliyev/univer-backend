package kz.sagengaliyev.univerbackend.model

import jakarta.persistence.*
import kz.sagengaliyev.univerbackend.enums.AssignmentStatus
import org.hibernate.annotations.SQLDelete
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "ASSIGNMENTS")
@SQLDelete(sql = "update assignments set is_deleted=true where id=?")
data class Assignment(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    val id: UUID? = null,

    @Column(name = "TITLE", nullable = false, length = 500)
    var title: String,

    @Column(name = "DESCRIPTION", length = 5000)
    var description: String,

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinTable(name = "ASSIGNMENT_ATTACHED_FILES")
    var attachedFiles: MutableSet<FileEntity> = mutableSetOf(),

    @Column(name = "USER_ID", nullable = false)
    var userID: UUID,

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinTable(name = "STUDENTS_PASSED_ASSIGNMENT")
    var studentsWhoPassed: MutableSet<Student> = mutableSetOf(),

    @Column(name = "CREATED_DATE", nullable = false)
    var createdDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "DEADLINE", nullable = false)
    var deadline: LocalDateTime,

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    var status: AssignmentStatus = AssignmentStatus.AWAITING_ANSWER,

    @Column(name = "IS_DELETED", nullable = false)
    var isDeleted: Boolean = false

)
