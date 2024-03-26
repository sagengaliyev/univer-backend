package kz.sagengaliyev.univerbackend.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kz.sagengaliyev.univerbackend.dto.TeacherDTO
import kz.sagengaliyev.univerbackend.facade.TeacherFacade
import kz.sagengaliyev.univerbackend.pagination.PageDTO
import kz.sagengaliyev.univerbackend.request.AppointACourseTeacherRequest
import kz.sagengaliyev.univerbackend.request.TeacherCreationRequest
import kz.sagengaliyev.univerbackend.request.TeacherUpdateRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/teacher")
@Tag(name = "Учитель")
class TeacherController(
    private val teacherFacade: TeacherFacade
) {

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создать")
    fun create(@RequestBody dto: TeacherCreationRequest) : ResponseEntity<TeacherDTO> {
        return ResponseEntity.ok(teacherFacade.create(dto))
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Получить список всех учителей",
        description = "Передайте 2 параметра: pageNumber(номер страницы) pageSize(количество элементов)")
    fun findAll(
        @RequestParam(defaultValue = "1", required = false) pageNumber: Int,
        @RequestParam(defaultValue = "6", required = false) pageSize: Int
    ) : ResponseEntity<PageDTO<TeacherDTO>> {
        return ResponseEntity.ok(teacherFacade.findAll(pageNumber, pageSize))
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Посмотреть одну")
    fun findById(@PathVariable id: Long) : ResponseEntity<TeacherDTO> {
        return ResponseEntity.ok(teacherFacade.findById(id))
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить")
    fun delete(@PathVariable id: Long) : ResponseEntity<Void> {
        teacherFacade.delete(id)
        return ResponseEntity.ok().build()
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновить данные")
    fun update(@PathVariable id: Long,
               @RequestBody dto: TeacherUpdateRequest
    ) : ResponseEntity<TeacherDTO> {
        return ResponseEntity.ok(teacherFacade.update(id, dto))
    }

    @PostMapping("/{id}/appoint-course")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Назначить преподавателем курса")
    fun appointACourse(@PathVariable id: Long, @RequestBody dto: AppointACourseTeacherRequest) : ResponseEntity<TeacherDTO> {
        return ResponseEntity.ok(teacherFacade.appointACourseTeacher(id, dto))
    }
}