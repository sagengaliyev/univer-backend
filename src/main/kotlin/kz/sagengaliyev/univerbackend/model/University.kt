package kz.sagengaliyev.univerbackend.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "university")
data class University(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null,
    @Column(name = "name", nullable = false)
    var name: String,
    @Column(name = "address", nullable = false)
    var address: String,
    @Column(name = "rector", nullable = false)
    var rector: String,
    @Column(name = "created_date", nullable = false)
    var createdDate: LocalDateTime = LocalDateTime.now()

)