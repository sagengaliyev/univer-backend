package kz.sagengaliyev.univerbackend.service.impl

import kz.sagengaliyev.univerbackend.exception.EntityNotFoundException
import kz.sagengaliyev.univerbackend.model.Assignment
import kz.sagengaliyev.univerbackend.repository.AssignmentRepository
import kz.sagengaliyev.univerbackend.service.AssignmentService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

class AssignmentServiceImpl(
    private val assignmentRepository: AssignmentRepository
) : AssignmentService {
    override fun save(assignment: Assignment): Assignment {
        return assignmentRepository.save(assignment)
    }

    override fun findById(id: UUID): Assignment {
        return assignmentRepository
            .findById(id)
            .orElseThrow { EntityNotFoundException("Assignment with ID: $id doesn't exists!") }
    }

    override fun findAll(pageable: Pageable): Page<Assignment> {
        return assignmentRepository.findAll(pageable)
    }

    override fun existsById(id: UUID): Boolean {
        return assignmentRepository.existsById(id)
    }

    override fun delete(assignment: Assignment) {
        assignmentRepository.delete(assignment)
    }
}