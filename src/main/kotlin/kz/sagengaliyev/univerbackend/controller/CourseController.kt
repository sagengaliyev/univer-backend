package kz.sagengaliyev.univerbackend.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kz.sagengaliyev.univerbackend.facade.CourseFacade
import kz.sagengaliyev.univerbackend.request.CourseCreationRequest
import kz.sagengaliyev.univerbackend.dto.CourseDTO
import kz.sagengaliyev.univerbackend.dto.CourseShortDTO
import kz.sagengaliyev.univerbackend.pagination.PageDTO
import kz.sagengaliyev.univerbackend.request.CourseUpdateRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/course")
@Tag(name = "Курсы")
class CourseController(
    private val courseFacade: CourseFacade
) {
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создать")
    fun create(@RequestBody dto: CourseCreationRequest) : ResponseEntity<CourseDTO> {
        return ResponseEntity.ok(courseFacade.create(dto))
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Посмотреть одну")
    fun findById(@PathVariable id: Long) : ResponseEntity<CourseDTO> {
        return ResponseEntity.ok(courseFacade.findById(id))
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Получить список")
    fun findAll(
        @RequestParam(defaultValue = "1", required = false) pageNumber: Int,
        @RequestParam(defaultValue = "6", required = false) pageSize: Int
    ) : ResponseEntity<PageDTO<CourseDTO>> {
        return ResponseEntity.ok(courseFacade.findAll(pageNumber, pageSize))
    }

    @GetMapping("/{id}/activity-status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Поменять статус активности")
    fun findById(@PathVariable id: Long,
                 @RequestParam(required = true) isActive: Boolean
    ) : ResponseEntity<Void> {
        courseFacade.setActivityStatus(id, isActive)
        return ResponseEntity.ok().build()
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновить данные")
    fun update(@PathVariable id: Long,
               @RequestBody dto: CourseUpdateRequest
    ) : ResponseEntity<CourseShortDTO> {
        return ResponseEntity.ok(courseFacade.update(id, dto))
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить")
    fun delete(@PathVariable id: Long) : ResponseEntity<Void> {
        courseFacade.delete(id)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{id}/students")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить всех студентов данного курса")
    fun deleteAllStudentsOfTheCourse(@PathVariable id: Long) : ResponseEntity<Void> {
        courseFacade.deleteAllStudentsOfTheCourse(id)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{id}/teachers")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить всех учителей данного курса")
    fun deleteAllTeachersOfTheCourse(@PathVariable id: Long) : ResponseEntity<Void> {
        courseFacade.deleteAllTeachersOfTheCourse(id)
        return ResponseEntity.ok().build()
    }
}