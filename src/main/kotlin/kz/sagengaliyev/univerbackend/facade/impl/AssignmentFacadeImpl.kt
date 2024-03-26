package kz.sagengaliyev.univerbackend.facade.impl

import kz.sagengaliyev.univerbackend.dto.AssignmentDTO
import kz.sagengaliyev.univerbackend.facade.AssignmentFacade
import kz.sagengaliyev.univerbackend.mapper.toDTO
import kz.sagengaliyev.univerbackend.mapper.toDto
import kz.sagengaliyev.univerbackend.mapper.toEntity
import kz.sagengaliyev.univerbackend.mapper.update
import kz.sagengaliyev.univerbackend.model.FileEntity
import kz.sagengaliyev.univerbackend.pagination.PageDTO
import kz.sagengaliyev.univerbackend.pagination.PageableFactory
import kz.sagengaliyev.univerbackend.request.AssignmentCreationRequest
import kz.sagengaliyev.univerbackend.request.AssignmentUpdateRequest
import kz.sagengaliyev.univerbackend.service.AssignmentService
import kz.sagengaliyev.univerbackend.service.FileStorageService
import org.springframework.data.domain.Page
import org.springframework.transaction.annotation.Transactional
import java.util.*

open class AssignmentFacadeImpl(
    private val assignmentService: AssignmentService,
    private val fileStorageService: FileStorageService
) : AssignmentFacade {

    @Transactional
    override fun create(dto: AssignmentCreationRequest): AssignmentDTO {
        var assignment = dto.toEntity()
        var files: MutableList<FileEntity> = mutableListOf()
        if (dto.attachedFiles.isNotEmpty()) {
            for (file in dto.attachedFiles) {
                val fileEntity = file.id?.let { fileStorageService.findByUUID(it) }
                if (fileEntity != null) {
                    files.add(fileEntity)
                }
            }
        }
        assignment.attachedFiles.addAll(files)
        assignmentService.save(assignment)
        return assignment.toDTO()

    }

    override fun findById(id: UUID): AssignmentDTO {
        return assignmentService.findById(id).toDTO()
    }

    override fun findAll(pageNumber: Int, pageSize: Int): PageDTO<AssignmentDTO> {
        val pageable = PageableFactory.create(pageNumber, pageSize)
        val assignments : Page<AssignmentDTO> = assignmentService.findAll(pageable).map { it.toDTO() }
        return PageDTO(assignments)
    }

    @Transactional
    override fun delete(id: UUID) {
        val deletedAssignment = assignmentService.findById(id)
        assignmentService.delete(deletedAssignment)
    }

    @Transactional
    override fun update(id: UUID, dto: AssignmentUpdateRequest) : AssignmentDTO{
        val assignmentToUpdate = assignmentService.findById(id)
        val updatedAssignment = dto.update(assignmentToUpdate)
        val files : MutableList<FileEntity> = mutableListOf()
        if (dto.attachedFiles?.isNotEmpty() == true) {
            assignmentToUpdate.attachedFiles.clear()
            for (fileDTO in dto.attachedFiles?.toList() ?: emptyList()) {
                val fileEntity = fileDTO.id?.let { fileStorageService.findByUUID(it) }
                if (fileEntity != null) {
                    files.add(fileEntity)
                }
            }
        }
        updatedAssignment.attachedFiles.addAll(files)
        val savedAssignment = assignmentService.save(updatedAssignment)
        return savedAssignment.toDTO()
    }
}