package kz.sagengaliyev.univerbackend.service


import kz.sagengaliyev.univerbackend.request.UniversityUpdateRequest
import kz.sagengaliyev.univerbackend.model.University
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UniversityService {
    fun findById(id: Long) : University

    fun existById(id: Long) : Boolean

    fun save(university: University)

    fun findAll(pageable: Pageable) : Page<University>

    fun update(id: Long, dto: UniversityUpdateRequest)
}