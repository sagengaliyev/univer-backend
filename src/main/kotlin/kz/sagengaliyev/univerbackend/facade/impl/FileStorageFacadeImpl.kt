package kz.sagengaliyev.univerbackend.facade.impl

import kz.sagengaliyev.univerbackend.dto.FileDTO
import kz.sagengaliyev.univerbackend.exception.CanNotUploadFileException
import kz.sagengaliyev.univerbackend.exception.FileIsEmptyException
import kz.sagengaliyev.univerbackend.facade.FileStorageFacade
import kz.sagengaliyev.univerbackend.mapper.toDto
import kz.sagengaliyev.univerbackend.mapper.toEntity
import kz.sagengaliyev.univerbackend.model.FileEntity
import kz.sagengaliyev.univerbackend.service.FileStorageService
import kz.sagengaliyev.univerbackend.service.impl.FileStorageServiceImpl
import kz.sagengaliyev.univerbackend.util.FileUtils
import kz.sagengaliyev.univerbackend.util.SecurityUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile
import java.util.*

class FileStorageFacadeImpl(
    private val fileStorageService: FileStorageService,
    private val logger: Logger = LoggerFactory.getLogger(FileStorageFacadeImpl::class.java)
) : FileStorageFacade {
    override fun upload(multipartFile: MultipartFile): ResponseEntity<FileDTO> {
        try {
            if (multipartFile.isEmpty) {
                throw FileIsEmptyException("Невозможно загрузить файл, так как он пуст!")
            }
            val fileEntity = multipartFile.toEntity()
            fileStorageService.save(fileEntity)

            return ResponseEntity.ok(fileEntity.toDto())
        } catch (e: Exception) {
            logger.error("Error: $e")
            throw CanNotUploadFileException()
        }
    }

    override fun upload(multipartFiles: List<MultipartFile>): ResponseEntity<List<FileDTO>> {
        try {
            val fileEntities = mutableListOf<FileEntity>()
            for (file in multipartFiles) {
                if (file.isEmpty) {
                    throw FileIsEmptyException("Невозможно загрузить файл, так как он пуст!")
                }
                val authenticatedUserId: String? = SecurityUtils.getAuthenticatedUserId()
                val fileEntity = FileEntity(
                    name = file.originalFilename ?: "",
                    contentType = file.contentType ?: "",
                    data = file.bytes,
                    size = file.size,
                    uploadedBy = UUID.fromString(authenticatedUserId)
                )
                fileEntities.add(fileEntity)
            }
            fileStorageService.saveAll(fileEntities)

            return ResponseEntity.ok(fileEntities.map { it.toDto() })
        } catch (e: Exception) {
            logger.error("Error: $e")
            throw CanNotUploadFileException()
        }
    }

    override fun download(uuid: UUID): ResponseEntity<Resource> {
        val file = fileStorageService.findByUUID(uuid)
        return FileUtils.downloadFile(file)
    }

    override fun view(uuid: UUID): ResponseEntity<InputStreamResource> {
        val file = fileStorageService.findByUUID(uuid)
        return FileUtils.viewFile(file)
    }

    override fun getFile(id: UUID): ResponseEntity<FileDTO> {
        val file = fileStorageService.findByUUID(id)
        return ResponseEntity.ok(file.toDto())
    }

    override fun findById(id: UUID): ResponseEntity<FileDTO> {
        return ResponseEntity.ok(fileStorageService.findByUUID(id).toDto())
    }
}