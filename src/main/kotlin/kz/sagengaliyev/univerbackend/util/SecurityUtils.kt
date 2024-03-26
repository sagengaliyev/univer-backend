package kz.sagengaliyev.univerbackend.util

import kz.sagengaliyev.univerbackend.enums.UserRole
import kz.sagengaliyev.univerbackend.exception.AuthorizationRequiredException
import kz.sagengaliyev.univerbackend.model.UniverUserPrincipal
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import java.util.*

class SecurityUtils {
    companion object{
        fun getAuthenticatedUserId(): String? {
            val authentication = SecurityContextHolder.getContext().authentication
            val principal = authentication.principal as Jwt
            return principal.getClaimAsString("sub")
        }

        fun getAuthenticatedUserDetailsById(uuid: UUID) : UniverUserPrincipal? {
            if (!isAuthenticated()) {
                throw AuthorizationRequiredException()
            }
            val authentication = getAuthentication()
            val principal = authentication.principal
            return if (principal is Jwt) {
                val jwt = authentication.principal as Jwt
                val userId = uuid
                val login = jwt.getClaimAsString("preferred_username")
                val springSecRoles = jwt.getClaimAsStringList("spring_sec_roles")
                val filteredRole = springSecRoles
                    .filter { it.startsWith("ROLE_") }
                    .map { role ->
                        when(role) {
                            "ROLE_ADMIN" -> UserRole.ROLE_ADMIN
                            "ROLE_STUDENT" -> UserRole.ROLE_STUDENT
                            "ROLE_TEACHER" -> UserRole.ROLE_TEACHER
                            else -> throw IllegalArgumentException("Unknown role: $role")
                        }
                    }
                val role = filteredRole.firstOrNull() ?: throw IllegalArgumentException("No valid role found")
                val name = jwt.getClaimAsString("name")
                val email = jwt.getClaimAsString("email") ?: null
                UniverUserPrincipal(userId, login, role, name, email)
            } else {
                return null
            }
        }

        fun getUniverUserPrincipal() : UniverUserPrincipal? {
            if (!isAuthenticated()) {
                throw AuthorizationRequiredException()
            }
            val authentication = getAuthentication()
            val principal = authentication.principal
            return if (principal is Jwt) {
                val jwt = authentication.principal as Jwt
                val userId = getAuthenticatedUserId()
                val login = jwt.getClaimAsString("preferred_username")
                val springSecRoles = jwt.getClaimAsStringList("spring_sec_roles")
                val filteredRole = springSecRoles
                    .filter { it.startsWith("ROLE_") }
                    .map { role ->
                        when(role) {
                            "ROLE_ADMIN" -> UserRole.ROLE_ADMIN
                            "ROLE_STUDENT" -> UserRole.ROLE_STUDENT
                            "ROLE_TEACHER" -> UserRole.ROLE_TEACHER
                            else -> throw IllegalArgumentException("Unknown role: $role")
                        }
                    }
                val role = filteredRole.firstOrNull() ?: throw IllegalArgumentException("No valid role found")
                val name = jwt.getClaimAsString("name")
                val email = jwt.getClaimAsString("email") ?: null
                UniverUserPrincipal(UUID.fromString(userId), login, role, name, email)
            } else {
                return null
            }
        }

        private fun getAuthentication() : Authentication {
            return SecurityContextHolder.getContext().authentication
        }

        private fun isAuthenticated() : Boolean {
            val authentication = SecurityContextHolder.getContext().authentication

            if (authentication.equals(null)) {
                return false
            }

            if (authentication is AnonymousAuthenticationToken) {
                return false
            }
            return true
        }
    }
}