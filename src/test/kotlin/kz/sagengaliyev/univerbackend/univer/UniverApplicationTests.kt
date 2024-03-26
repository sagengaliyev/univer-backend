package kz.sagengaliyev.univerbackend.univer

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
	properties = [
		"spring.datasource.url=jdbc:postgresql://localhost:5432/univerdb"
	]
)
class UniverApplicationTests() {

	@Test
	fun contextLoads() {
	}


}
