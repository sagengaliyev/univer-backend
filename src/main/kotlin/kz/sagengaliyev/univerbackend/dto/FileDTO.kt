package kz.sagengaliyev.univerbackend.dto

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.UUID

data class FileDTO(
    val id: UUID?,
    val name: String,
    val contentType: String,
    val size: Long,

) {
    fun getDownloadLink() : String {
        return "/api/v1/files/$id/download"
    }

    fun getViewLink() : String {
        return "/api/v1/files/$id/view"
    }
}
