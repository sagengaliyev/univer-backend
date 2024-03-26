package kz.sagengaliyev.univerbackend.exception

import org.springframework.http.HttpStatus

class YouCanNotCreateANewStudentException(
    message: String
) : AbstractApplicationException(message) {
    override fun getStatus(): HttpStatus = HttpStatus.BAD_REQUEST

}