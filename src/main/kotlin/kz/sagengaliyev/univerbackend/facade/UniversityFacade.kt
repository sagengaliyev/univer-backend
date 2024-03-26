package kz.sagengaliyev.univerbackend.facade

import kz.sagengaliyev.univerbackend.dto.UniversityDTO
import kz.sagengaliyev.univerbackend.pagination.PageDTO
import kz.sagengaliyev.univerbackend.request.UniversityCreationRequest
import kz.sagengaliyev.univerbackend.request.UniversityUpdateRequest

interface UniversityFacade{
    fun create(universityCreationRequest: UniversityCreationRequest)

    fun update(id: Long, universityUpdateRequest: UniversityUpdateRequest)

    fun findById(id: Long): UniversityDTO

    fun findAll(pageNumber: Int, pageSize: Int): PageDTO<UniversityDTO>
}