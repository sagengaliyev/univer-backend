package kz.sagengaliyev.univerbackend.service.impl

import kz.sagengaliyev.univerbackend.exception.EntityNotFoundException
import kz.sagengaliyev.univerbackend.model.FileEntity
import kz.sagengaliyev.univerbackend.repository.FileEntityRepository
import kz.sagengaliyev.univerbackend.service.FileStorageService
import java.util.*

class FileStorageServiceImpl(
    private val fileEntityRepository: FileEntityRepository
) : FileStorageService {
    override fun save(fileEntity: FileEntity): FileEntity {
        return fileEntityRepository.save(fileEntity)
    }

    override fun saveAll(files: List<FileEntity>): List<FileEntity> {
        return fileEntityRepository.saveAll(files)
    }

    override fun findByUUID(uuid: UUID): FileEntity {
        return fileEntityRepository
            .findById(uuid)
            .orElseThrow { EntityNotFoundException("Файл с ID: $uuid не найден!") }
    }

    override fun existsByUUID(uuid: UUID): Boolean {
        return fileEntityRepository.existsById(uuid)
    }

    override fun deleteByUUID(uuid: UUID) {
        fileEntityRepository.deleteById(uuid)
    }
}