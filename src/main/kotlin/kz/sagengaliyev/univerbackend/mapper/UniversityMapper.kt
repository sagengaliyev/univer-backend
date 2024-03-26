package kz.sagengaliyev.univerbackend.mapper

import kz.sagengaliyev.univerbackend.dto.UniversityDTO
import kz.sagengaliyev.univerbackend.request.UniversityCreationRequest
import kz.sagengaliyev.univerbackend.request.UniversityUpdateRequest
import kz.sagengaliyev.univerbackend.model.University
import java.time.LocalDateTime

fun UniversityCreationRequest.toEntity() : University {
    return University(
        name = this.name,
        address = this.address,
        rector = this.rector,
        createdDate = LocalDateTime.now()
    )
}

fun University.toDto() : UniversityDTO {
    return UniversityDTO(
        id = this.id,
        name = this.name,
        address = this.address,
        rector = this.rector,
        createdDate = this.createdDate
    )
}

fun UniversityUpdateRequest.update(university: University): University {
    this.name?.let { university.name = it }
    this.rector?.let { university.rector = it }
    this.address?.let { university.address = it }
    return university
}