package kz.sagengaliyev.univerbackend.repository

import kz.sagengaliyev.univerbackend.model.University
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UniversityRepository : JpaRepository<University, Long> {
    override fun existsById(id: Long) : Boolean
}