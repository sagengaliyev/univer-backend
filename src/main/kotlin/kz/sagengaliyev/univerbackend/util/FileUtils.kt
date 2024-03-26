package kz.sagengaliyev.univerbackend.util

import kz.sagengaliyev.univerbackend.model.FileEntity
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.InputStreamSource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.UUID

class FileUtils {
    companion object{
        fun downloadFile(file: FileEntity) : ResponseEntity<Resource> {
            return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(file.contentType!!))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${file.name}")
                .body(ByteArrayResource(file.data))
        }

        fun downloadFile(contentType: String, fileName: String, bytes: ByteArray) : ResponseEntity<Resource> {
            return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=$fileName")
                .body(ByteArrayResource(bytes))
        }

        fun viewFile(file: FileEntity) : ResponseEntity<InputStreamResource>{
            val targetStream: InputStream = ByteArrayInputStream(file.data)
            return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(file.contentType!!))
                .body(InputStreamResource(targetStream))
        }
    }
}