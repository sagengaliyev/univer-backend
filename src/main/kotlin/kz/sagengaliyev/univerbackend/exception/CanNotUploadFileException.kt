package kz.sagengaliyev.univerbackend.exception

import org.springframework.http.HttpStatus

class CanNotUploadFileException : AbstractApplicationException("Не получилось загрузить файл") {
    override fun getStatus(): HttpStatus {
        return HttpStatus.BAD_REQUEST
    }
}