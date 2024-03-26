package kz.sagengaliyev.univerbackend.exception

import org.springframework.http.HttpStatus

class AuthorizationRequiredException : AbstractApplicationException("Требуется авторизация! Зайдите на портал или зарегистрируйтесь!") {
    override fun getStatus(): HttpStatus {
        return HttpStatus.BAD_REQUEST
    }
}