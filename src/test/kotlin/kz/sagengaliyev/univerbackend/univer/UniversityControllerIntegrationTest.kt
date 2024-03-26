package kz.sagengaliyev.univerbackend.univer

import com.fasterxml.jackson.databind.ObjectMapper
import kz.sagengaliyev.univerbackend.exception.EntityNotFoundException
import kz.sagengaliyev.univerbackend.request.UniversityCreationRequest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = [
        "spring.datasource.url=jdbc:postgresql://localhost:5432/univerdb"
    ]
)
@AutoConfigureMockMvc
class UniversityControllerIntegrationTest(
    @Autowired()
    val mockMvc: MockMvc,

    @Autowired
    val objectMapper: ObjectMapper,

    @Value("\${base-url}")
    val baseUrl : String
) {

    @Test
    fun `should create a new university`() {
        val university = UniversityCreationRequest(
            name = "KBTU",
            address = "Almaty, Satpaev st",
            rector = "Nonamov Noname Nonameuly"
        )

        mockMvc.post("/api/v1/university") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(university)
        }
            .andDo { print() }
            .andExpect {
                status { isOk() }
            }
    }

    @Test
    fun `should return list of universities`() {
        mockMvc.get("/api/v1/university") {
            contentType = MediaType.APPLICATION_JSON
        }
            .andDo { print() }
            .andExpect {
                status { isOk() }
            }
    }

    @Test
    fun `should return university by id otherwise BAD_REQUEST exception`() {
        mockMvc.get("/api/v1/university/1") {
            contentType = MediaType.APPLICATION_JSON
        }
            .andDo { print() }
            .andExpect {
                status { isOk() }
            }
    }

    @Test
    fun `should BAD_REQUEST exception`() {
            assertThrows<EntityNotFoundException> {
                mockMvc.get("/api/v1/university/10") {
                    contentType = MediaType.APPLICATION_JSON
                }
                    .andDo { print() }
                    .andExpect {
                        status { isBadRequest() }
                    }
            }
    }


}