package kz.sagengaliyev.univerbackend.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import kz.sagengaliyev.univerbackend.dto.StudentDTO
import kz.sagengaliyev.univerbackend.facade.StudentFacade
import kz.sagengaliyev.univerbackend.pagination.PageDTO
import kz.sagengaliyev.univerbackend.request.EnrollStudentToCourseRequest
import kz.sagengaliyev.univerbackend.request.StudentCreationRequest
import kz.sagengaliyev.univerbackend.request.StudentUpdateRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/student")
@Tag(name = "Студенты")
class StudentController(
    private val studentFacade: StudentFacade
) {

    @Operation(summary = "Создать")
    @PostMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    fun create(
        @RequestBody studentCreationRequest: StudentCreationRequest
    ): ResponseEntity<StudentDTO> {
        return ResponseEntity.ok(studentFacade.create(studentCreationRequest))
    }

    @Operation(summary = "Получить одного студента")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    fun findById(@PathVariable id: Long) : ResponseEntity<Any> {
           return ResponseEntity.ok(studentFacade.findById(id))
    }

    @Operation(summary = "Обновить данные студента")
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    fun update(@PathVariable id: Long,
               @RequestBody studentUpdateRequest: StudentUpdateRequest
    ) : ResponseEntity<StudentDTO> {
        return ResponseEntity.ok(studentFacade.update(id, studentUpdateRequest))
    }

    @Operation(summary = "Список всех студентов",
        description = "Передайте 2 параметра: pageNumber(номер страницы) pageSize(количество элементов)")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    fun findAll(
        @RequestParam(defaultValue = "1", required = false) pageNumber: Int,
        @RequestParam(defaultValue = "6", required = false) pageSize: Int
    ) : ResponseEntity<PageDTO<StudentDTO>> {
        return ResponseEntity.ok(studentFacade.findAll(pageNumber, pageSize))
    }

    @Operation(summary = "Поиск",
        description = "В поиске работает пагинация, можно передать такие параметры как: " +
                "firstName, lastName, age, year, gpa, sortByYear=asc/desc, sortByGpa=asc/desc. " +
                "Для настройки пагинации передайте параметр: pageNumber и pageSize")
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    fun search(
        @RequestParam(required = false, defaultValue = "") firstName: String?,
        @RequestParam(required = false, defaultValue = "") lastName: String?,
        @RequestParam(required = false, defaultValue = "") age: Int?,
        @RequestParam(required = false, defaultValue = "") year: Int?,
        @RequestParam(required = false, defaultValue = "") gpa: Double?,
        @RequestParam(required = false, defaultValue = "") sortByYear: String?,
        @RequestParam(required = false, defaultValue = "") sortByGpa: String?,
        @RequestParam(required = false, defaultValue = "1" ) pageNumber: Int,
        @RequestParam(required = false, defaultValue = "6") pageSize: Int
    ) : ResponseEntity<PageDTO<StudentDTO>> {
        return ResponseEntity.ok(studentFacade.search(
            firstName = firstName,
            lastName = lastName,
            age = age,
            year = year,
            gpa = gpa,
            sortByYear = sortByYear,
            sortByGpa = sortByGpa,
            pageNumber = pageNumber,
            pageSize = pageSize
        ))
    }

    @Operation(summary = "Удалить")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun delete(@PathVariable id: Long) : ResponseEntity<Unit> {
        studentFacade.delete(id)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "Отчислить студента")
    @GetMapping("/{id}/deduct")
    @PreAuthorize("hasRole('ADMIN')")
    fun deduct(@PathVariable id: Long) : ResponseEntity<StudentDTO> {
        return ResponseEntity.ok(studentFacade.deduct(id))
    }

    @PostMapping("/{id}/enroll_to_course")
    @Operation(summary = "Зачислить на курс")
    @PreAuthorize("hasRole('ADMIN')")
    fun enrollToCourse(@PathVariable id: Long,
                       @RequestBody dto: EnrollStudentToCourseRequest
    ) : ResponseEntity<StudentDTO> {
        return ResponseEntity.ok(studentFacade.enrollToCourse(id, dto))
    }

}