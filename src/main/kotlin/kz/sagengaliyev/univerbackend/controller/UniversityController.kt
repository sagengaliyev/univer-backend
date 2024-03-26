package kz.sagengaliyev.univerbackend.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kz.sagengaliyev.univerbackend.dto.UniversityDTO
import kz.sagengaliyev.univerbackend.facade.StudentFacade
import kz.sagengaliyev.univerbackend.facade.UniversityFacade
import kz.sagengaliyev.univerbackend.pagination.PageDTO
import kz.sagengaliyev.univerbackend.request.UniversityCreationRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/university")
@Tag(name = "Университет")
class UniversityController(
    private val universityFacade: UniversityFacade,
    private val studentFacade: StudentFacade
) {
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Посмотреть одну")
    fun findById(@PathVariable id: Long) : ResponseEntity<UniversityDTO> {
        return ResponseEntity.ok(universityFacade.findById(id))
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Посмотреть одну",
        description = "Передайте 2 параметра: pageNumber(номер страницы) pageSize(количество элементов)")
    fun findAll(
        @RequestParam(defaultValue = "1", required = false) pageNumber: Int,
        @RequestParam(defaultValue = "6", required = false) pageSize: Int
    ) : ResponseEntity<PageDTO<UniversityDTO>> {
        return ResponseEntity.ok(universityFacade.findAll(pageNumber, pageSize))
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создать")
    fun create(@RequestBody universityCreationRequest: UniversityCreationRequest) : ResponseEntity<UniversityCreationRequest> {
        universityFacade.create(universityCreationRequest)
        return ResponseEntity.ok().build()
    }

}