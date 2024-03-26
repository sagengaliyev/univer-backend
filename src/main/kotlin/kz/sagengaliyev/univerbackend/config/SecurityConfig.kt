package kz.sagengaliyev.univerbackend.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.web.SecurityFilterChain
import java.util.stream.Stream

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val BASE_API_ENDPOINT: String = "/api/**",
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .oauth2ResourceServer {
                oauth2 -> oauth2
                    .jwt{ it.jwtAuthenticationConverter(jwtAuthenticationConverterForKeycloak()) }
            }

        http
            .csrf { csrf -> csrf.disable() }
            .cors { cors -> cors.disable() }
            .securityMatcher(BASE_API_ENDPOINT)
            .authorizeHttpRequests{
                r -> r.anyRequest().authenticated()
            }


        return http.build()
    }

    @Bean
    fun jwtAuthenticationConverterForKeycloak(): JwtAuthenticationConverter {
        val converter = JwtAuthenticationConverter()
        val jwtGrantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()
        converter.setPrincipalClaimName("preferred_username")
        converter.setJwtGrantedAuthoritiesConverter { jwt ->
            val authorities = jwtGrantedAuthoritiesConverter.convert(jwt) ?: emptyList()
            val roles = jwt.getClaimAsStringList("spring_sec_roles") ?: emptyList()

            val roleAuthorities = roles
                .filter { it.startsWith("ROLE_") }
                .map { SimpleGrantedAuthority(it) }

            authorities + roleAuthorities
        }
        return converter
    }

}