package kz.sagengaliyev.univerbackend.exception

import org.springframework.http.HttpStatus

abstract class AbstractApplicationException(message: String) : RuntimeException(message) {
    abstract fun getStatus() : HttpStatus

    fun getDetails() : Any? {
        return null
    }


}