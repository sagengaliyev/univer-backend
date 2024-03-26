package kz.sagengaliyev.univerbackend.facade.impl

import kz.sagengaliyev.univerbackend.dto.UniversityDTO
import kz.sagengaliyev.univerbackend.facade.UniversityFacade
import kz.sagengaliyev.univerbackend.request.UniversityCreationRequest
import kz.sagengaliyev.univerbackend.request.UniversityUpdateRequest
import kz.sagengaliyev.univerbackend.service.UniversityService
import kz.sagengaliyev.univerbackend.exception.EntityNotFoundException
import kz.sagengaliyev.univerbackend.mapper.toDto
import kz.sagengaliyev.univerbackend.mapper.toEntity
import kz.sagengaliyev.univerbackend.mapper.update
import kz.sagengaliyev.univerbackend.pagination.PageDTO
import kz.sagengaliyev.univerbackend.pagination.PageableFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.transaction.annotation.Transactional


open class UniversityFacadeImpl(
    private val universityService: UniversityService,
    private val log: Logger = LoggerFactory.getLogger(UniversityFacadeImpl::class.java)
)  : UniversityFacade {

    @Transactional
    override fun create(universityCreationRequest: UniversityCreationRequest) {
        val university = universityCreationRequest.toEntity()
        log.info("Creating a new university with ID: ${university.id}")
        universityService.save(university)
    }

    @Transactional
    override fun update(id: Long, universityUpdateRequest: UniversityUpdateRequest) {
//        val toUpdate = UniversityUpdateDTO
//        val update = UniversityUpdateDTO.update()
//        universityService.save(toUpdate)
        val university = universityService.findById(id)
        if (!universityService.existById(id)) {
            log.warn("An exception occurred while trying to update university with ID: $id")
            throw EntityNotFoundException("Универ с данным $id не найден!")
        }
            val updated = universityUpdateRequest.update(university)
            log.info("Updating university with ID: $id")
            universityService.save(updated)
    }

    override fun findById(id: Long): UniversityDTO {
        val university = universityService.findById(id)
        log.info("Extracting university details with ID: $id")
        return university.toDto()
    }

    override fun findAll(pageNumber: Int, pageSize: Int): PageDTO<UniversityDTO> {
        val pageable: Pageable = PageableFactory.create(pageNumber, pageSize)
        val universities: Page<UniversityDTO> = universityService
            .findAll(pageable)
            .map { it.toDto() }
        log.info("Extracting the list of all universities")
        return PageDTO(universities)
    }
}