package kz.sagengaliyev.univerbackend.univer

import kz.sagengaliyev.univerbackend.dto.StudentDTO
import kz.sagengaliyev.univerbackend.request.StudentCreationRequest
import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.*
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import kotlin.random.Random

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = [
        "spring.datasource.url=jdbc:postgresql://localhost:5432/univerdb"
    ]
)
class StudentControllerIntegrationTest(
    @Autowired
    val client: TestRestTemplate,

    @Value("\${base-url}")
    val baseUrl : String
) {
    @Test
    fun `should create a new student`() {
        val id = Random.nextLong()
        val student = StudentCreationRequest(
            firstName = "Balenshe",
            lastName = "Balensheev",
            age = 21,
            gpa = 3.35,
            year = 3,
            universityId = 1
        )

        val createdEntity : StudentDTO? = client.postForObject("/api/v1/student", student, StudentDTO::class)

        val retrievedEntity = client.getForObject<StudentDTO>("/api/v1/student/${createdEntity?.id}", StudentDTO::class)
        AssertionsForClassTypes.assertThat(retrievedEntity).usingRecursiveComparison().ignoringFields("enrollmentDate")
    }

    @Test
    fun `should return a founded student`() {
        val id = 3
        val studentEntity = client.getForEntity<StudentDTO>("/api/v1/student/$id", StudentDTO::class)
        AssertionsForClassTypes.assertThat(studentEntity.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `should not found a student`() {
        val id = 1
        val studentEntity = client.getForEntity<StudentDTO>("/api/v1/student/$id", StudentDTO::class)
        AssertionsForClassTypes.assertThat(studentEntity.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `should delete student`() {
        client.delete("/api/v1/student/1")
    }
}