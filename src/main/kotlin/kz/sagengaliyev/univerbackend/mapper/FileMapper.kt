package kz.sagengaliyev.univerbackend.mapper

import kz.sagengaliyev.univerbackend.dto.FileDTO
import kz.sagengaliyev.univerbackend.model.FileEntity
import kz.sagengaliyev.univerbackend.util.SecurityUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.multipart.MultipartFile
import java.util.*

fun FileEntity.toDto() : FileDTO {
    return FileDTO(
        id = this.id,
        name = this.name,
        contentType = this.contentType,
        size = this.size
    )
}

fun MultipartFile.toEntity() : FileEntity {
    return FileEntity(
        name = this.name,
        contentType = this.contentType.toString(),
        size = this.size,
        data = this.bytes,
        uploadedBy = UUID.fromString(SecurityUtils.getAuthenticatedUserId())
    )
}
