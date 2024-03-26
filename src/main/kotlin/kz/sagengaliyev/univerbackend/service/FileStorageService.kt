package kz.sagengaliyev.univerbackend.service

import kz.sagengaliyev.univerbackend.model.FileEntity
import java.util.UUID

interface FileStorageService{
    fun save(fileEntity: FileEntity): FileEntity

    fun saveAll(files: List<FileEntity>): List<FileEntity>

    fun findByUUID(uuid: UUID): FileEntity

    fun existsByUUID(uuid: UUID): Boolean

    fun deleteByUUID(uuid: UUID)
}