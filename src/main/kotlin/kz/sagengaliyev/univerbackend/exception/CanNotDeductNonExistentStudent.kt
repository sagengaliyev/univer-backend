package kz.sagengaliyev.univerbackend.exception

import org.springframework.http.HttpStatus

class CanNotDeductNonExistentStudent(msg: String) : AbstractApplicationException(msg) {
    override fun getStatus(): HttpStatus {
        return HttpStatus.BAD_REQUEST
    }
}