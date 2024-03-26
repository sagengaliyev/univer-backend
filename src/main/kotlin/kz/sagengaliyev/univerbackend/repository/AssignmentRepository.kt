package kz.sagengaliyev.univerbackend.repository

import kz.sagengaliyev.univerbackend.model.Assignment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AssignmentRepository : JpaRepository<Assignment, UUID> {
}