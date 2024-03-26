package kz.sagengaliyev.univerbackend.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "FILES")
data class FileEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    val id: UUID? = null,

    @Column(name = "FILE_NAME", nullable = false)
    var name: String,

    @Column(name = "CONTENT_TYPE", nullable = false)
    var contentType: String,

    @Lob
    @Column(name = "FILE_CONTENT", nullable = false)
    var data: ByteArray,

    @Column(name = "FILE_SIZE", nullable = false)
    var size: Long,

    @Column(name = "FILE_UPLOAD_DATE", nullable = false)
    var uploadDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "UPLOADED_BY")
    var uploadedBy: UUID?
)
