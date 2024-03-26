package kz.sagengaliyev.univerbackend.univer

import kz.sagengaliyev.univerbackend.facade.StudentFacade
import kz.sagengaliyev.univerbackend.service.StudentService
import kz.sagengaliyev.univerbackend.model.Student
import kz.sagengaliyev.univerbackend.pagination.PageableFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = [
        "spring.datasource.url=jdbc:postgresql://localhost:5432/univerdb"
    ]
)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ExtendWith(MockitoExtension::class)
class StudentControllerUnitTest {
    @Mock
    lateinit var mockStudentService: StudentService

    @Mock
    lateinit var mockStudentFacade: StudentFacade

    @Test
    fun `create() method should call service's create() method only once`() {
        // Given
        val student = Student(
            26L,
            "Alex",
            "Mahone",
            22,
            3,
            3.55,
            true,
            false,
            1,
            LocalDateTime.now(),
            null,
            false
        )

        val mockStudentService = mock(StudentService::class.java)

        //when
        //`when`(mockStudentService.save(student)).thenReturn(student)

        mockStudentService.save(student)

        verify(mockStudentService, times(1)).save(student)
    }

    @Test
    fun `findById() method should return a student by his ID`() {
        // Given
        val student = Student(
            26L,
            "Alex",
            "Mahone",
            22,
            3,
            3.55,
            true,
            false,
            1,
            LocalDateTime.now(),
            null,
            false
        )

        `when`(mockStudentService.findById(26)).thenReturn(student)

        val response = mockStudentService.findById(26)

        assertNotNull(response)
        assertEquals(student, response)
    }

    @Test
    fun `findAll() method should return the list of students`() {
        val pageable: Pageable = PageableFactory.create(1,6)

        val listOfStudents = mutableListOf<Student>(
            Student(
                26L,
                "Alex",
                "Mahone",
                22,
                3,
                3.55,
                true,
                false,
                1,
                LocalDateTime.now(),
                null,
                false
            ),
            Student(
                27L,
                "Amangali",
                "Mahone",
                25,
                4,
                3.55,
                true,
                false,
                1,
                LocalDateTime.now(),
                null,
                false
            ),
            Student(
                27L,
                "Michael",
                "Mahone",
                23,
                3,
                3.66,
                true,
                false,
                1,
                LocalDateTime.now(),
                null,
                false
            ),
            Student(
                28L,
                "Linkoln",
                "Mahone",
                25,
                2,
                3.30,
                true,
                false,
                1,
                LocalDateTime.now(),
                null,
                false
            )
        )

        val pageOfStudents = PageImpl(listOfStudents, pageable, listOfStudents.size.toLong())

        `when`(mockStudentService.findAll(pageable)).thenReturn(pageOfStudents)

        val result = mockStudentService.findAll(pageable)

        assertEquals(pageOfStudents, result)
    }


}


