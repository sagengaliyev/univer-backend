package kz.sagengaliyev.univerbackend.repository

import kz.sagengaliyev.univerbackend.model.FileEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface FileEntityRepository : JpaRepository<FileEntity, UUID> {
}