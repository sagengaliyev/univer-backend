package kz.sagengaliyev.univerbackend.exception.handler

import org.springframework.http.HttpStatus

data class ExceptionDTO(
    var error: HttpStatus? = null,
    var exception: String? = null,
    var message: String? = null,
    var path: String? = null,
    var status: Int? = null,
    var timestamp: Long? = null,
    var details: Any? = null
)
