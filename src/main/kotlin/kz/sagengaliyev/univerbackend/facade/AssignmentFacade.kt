package kz.sagengaliyev.univerbackend.facade

import kz.sagengaliyev.univerbackend.dto.AssignmentDTO
import kz.sagengaliyev.univerbackend.model.Assignment
import kz.sagengaliyev.univerbackend.pagination.PageDTO
import kz.sagengaliyev.univerbackend.request.AssignmentCreationRequest
import kz.sagengaliyev.univerbackend.request.AssignmentUpdateRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface AssignmentFacade {
    fun create(dto: AssignmentCreationRequest) : AssignmentDTO

    fun findById(id: UUID) : AssignmentDTO

    fun findAll(pageNumber: Int, pageSize: Int) : PageDTO<AssignmentDTO>

    fun delete(id: UUID)

    fun update(id: UUID, dto: AssignmentUpdateRequest) : AssignmentDTO
}