package kz.sagengaliyev.univerbackend.facade

import kz.sagengaliyev.univerbackend.dto.FileDTO
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.InputStreamSource
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

interface FileStorageFacade {
    fun upload(multipartFile: MultipartFile): ResponseEntity<FileDTO>

    fun upload(multipartFiles: List<MultipartFile>): ResponseEntity<List<FileDTO>>

    fun download(uuid: UUID): ResponseEntity<Resource>

    fun view(uuid: UUID): ResponseEntity<InputStreamResource>

    fun getFile(id: UUID) : ResponseEntity<FileDTO>

    fun findById(id: UUID) : ResponseEntity<FileDTO>
}