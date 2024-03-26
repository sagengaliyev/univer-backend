package kz.sagengaliyev.univerbackend.service

import kz.sagengaliyev.univerbackend.model.Assignment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.UUID

interface AssignmentService {
    fun save(assignment: Assignment) : Assignment

    fun findById(id: UUID) : Assignment

    fun findAll(pageable: Pageable) : Page<Assignment>

    fun existsById(id: UUID) : Boolean

    fun delete(assignment: Assignment)
}