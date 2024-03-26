package kz.sagengaliyev.univerbackend.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kz.sagengaliyev.univerbackend.dto.FileDTO
import kz.sagengaliyev.univerbackend.facade.FileStorageFacade
import kz.sagengaliyev.univerbackend.util.SecurityUtils
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.FileInputStream
import java.util.UUID

@RestController
@RequestMapping("/api/v1/files")
@Tag(name = "Файлы")
class FileController(
    private val fileStorageFacade: FileStorageFacade
) {

    @PostMapping("/upload")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT','TEACHER')")
    @Operation(summary = "Загрузить файл")
    fun upload(@RequestParam("file") file: MultipartFile) : ResponseEntity<FileDTO>{
        return fileStorageFacade.upload(file)
    }

    @PostMapping("/multi/upload")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT','TEACHER')")
    @Operation(summary = "Загрузить несколько файлов")
    fun upload(@RequestParam("files") files: List<MultipartFile>) : ResponseEntity<List<FileDTO>> {
        return fileStorageFacade.upload(files)
    }

    @GetMapping("/{id}/view")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT','TEACHER')")
    @Operation(summary = "Посмотреть файл")
    fun view(@PathVariable id: UUID) : ResponseEntity<InputStreamResource> {
        return fileStorageFacade.view(id)
    }

    @GetMapping("/{id}/download")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STUDENT')")
    @Operation(summary = "Скачать файл")
    fun download(@PathVariable id: UUID) : ResponseEntity<Resource> {
        return fileStorageFacade.download(id)
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT','TEACHER')")
    @Operation(summary = "Получить файл")
    fun findById(@PathVariable id: UUID) : ResponseEntity<FileDTO> {
        return fileStorageFacade.findById(id)
    }
}