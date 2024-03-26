package kz.sagengaliyev.univerbackend.univer

import io.mockk.mockk
import kz.sagengaliyev.univerbackend.service.StudentService
import kz.sagengaliyev.univerbackend.repository.StudentRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Pageable

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = [
        "spring.datasource.url=jdbc:postgresql://localhost:5432/univerdb"
    ]
)
@AutoConfigureMockMvc
class StudentServiceTest(
    val studentRepository: StudentRepository = mockk(),

    val studentService : StudentService
) {

    @Test
    fun `findAll() method should return collection of students`(){
        val pageable : Pageable = mockk()
        studentService.findAll(pageable)

        verify(exactly=1) {
            studentService.findAll(pageable)
        }
    }
}