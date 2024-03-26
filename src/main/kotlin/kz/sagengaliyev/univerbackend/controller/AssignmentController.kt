package kz.sagengaliyev.univerbackend.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kz.sagengaliyev.univerbackend.dto.AssignmentDTO
import kz.sagengaliyev.univerbackend.facade.AssignmentFacade
import kz.sagengaliyev.univerbackend.pagination.PageDTO
import kz.sagengaliyev.univerbackend.request.AssignmentCreationRequest
import kz.sagengaliyev.univerbackend.request.AssignmentUpdateRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/assignment")
@Tag(name = "Assignment")
class AssignmentController(
    private val assignmentFacade: AssignmentFacade
) {

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    @Operation(summary = "Создать эсайнмент")
    fun create(@RequestBody dto: AssignmentCreationRequest) : ResponseEntity<AssignmentDTO> {
        return ResponseEntity.ok(assignmentFacade.create(dto))
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "Список всех эсайнментов")
    fun findAll(
        @RequestParam(defaultValue = "1", required = false) pageNumber: Int,
        @RequestParam(defaultValue = "6", required = false) pageSize: Int
    ) : ResponseEntity<PageDTO<AssignmentDTO>>{
        return ResponseEntity.ok(assignmentFacade.findAll(pageNumber, pageSize))
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STUDENT')")
    @Operation(summary = "Посмотреть одну")
    fun findById(
        @PathVariable id: UUID
    ) : ResponseEntity<AssignmentDTO>{
        return ResponseEntity.ok(assignmentFacade.findById(id))
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    @Operation(summary = "Удалить")
    fun delete(@PathVariable id: UUID) : ResponseEntity<Void>{
        assignmentFacade.delete(id)
        return ResponseEntity.ok().build()
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    @Operation(summary = "Обновить данные")
    fun update(
        @PathVariable id: UUID,
        @RequestBody dto: AssignmentUpdateRequest
    ) : ResponseEntity<AssignmentDTO>{
        return ResponseEntity.ok(assignmentFacade.update(id, dto))
    }
}