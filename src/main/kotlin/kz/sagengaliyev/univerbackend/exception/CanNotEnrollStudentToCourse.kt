package kz.sagengaliyev.univerbackend.exception

import org.springframework.http.HttpStatus

class CanNotEnrollStudentToCourse(msg: String) : AbstractApplicationException(msg) {
    override fun getStatus(): HttpStatus {
        return HttpStatus.BAD_REQUEST
    }
}