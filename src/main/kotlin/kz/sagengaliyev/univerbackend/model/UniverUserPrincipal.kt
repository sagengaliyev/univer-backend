package kz.sagengaliyev.univerbackend.model

import kz.sagengaliyev.univerbackend.enums.UserRole
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

data class UniverUserPrincipal(
    val userID: UUID,
    val login: String,
    val userRole: UserRole,
    val name: String,
    val email: String?
) : UserDetails {
    val roles: MutableCollection<GrantedAuthority> = mutableSetOf(SimpleGrantedAuthority(userRole.name))
    val accountNonExpired: Boolean = true
    val accountNonLocked: Boolean = true
    val credentialsNonExpired: Boolean = true
    val enabled: Boolean = true
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return roles
    }

    override fun getPassword(): String? {
        return null
    }

    override fun getUsername(): String {
        return login
    }

    override fun isAccountNonExpired(): Boolean {
        return accountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return accountNonLocked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return credentialsNonExpired
    }

    override fun isEnabled(): Boolean {
        return enabled
    }
}

