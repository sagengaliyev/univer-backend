package kz.sagengaliyev.univerbackend.service.impl

import kz.sagengaliyev.univerbackend.mapper.update
import kz.sagengaliyev.univerbackend.request.UniversityUpdateRequest
import kz.sagengaliyev.univerbackend.service.UniversityService
import kz.sagengaliyev.univerbackend.exception.EntityNotFoundException
import kz.sagengaliyev.univerbackend.model.University
import kz.sagengaliyev.univerbackend.repository.UniversityRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class UniversityServiceImpl(
    private val universityRepository: UniversityRepository
) : UniversityService {
    override fun findById(id: Long): University {
        return universityRepository
            .findById(id)
            .orElseThrow{(EntityNotFoundException("Универ с данным ID: $id не найден!"))}
    }

    override fun existById(id: Long): Boolean = universityRepository.existsById(id)

    override fun save(university: University){
        universityRepository.save(university)
    }

    override fun findAll(pageable: Pageable): Page<University> {
        return universityRepository.findAll(pageable)
    }

    override fun update(id: Long, dto: UniversityUpdateRequest) {
        val university: University = findById(id)
        val updatedUniversity = dto.update(university)
        save(updatedUniversity)
    }
}
