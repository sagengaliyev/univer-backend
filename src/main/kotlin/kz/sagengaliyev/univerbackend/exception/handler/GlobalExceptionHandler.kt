package kz.sagengaliyev.univerbackend.exception.handler

import kz.sagengaliyev.univerbackend.exception.AbstractApplicationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [MissingServletRequestParameterException::class])
    protected fun handleMissingServletRequestParameterException (
        ex: MissingServletRequestParameterException,
        request: WebRequest
    ) : ResponseEntity<Any> {
        val message = "Обязательный параметр \"${ex.parameterName}\" (${ex.parameterType}) не указан."
        val response = ExceptionDTO(
            HttpStatus.BAD_REQUEST,
            ex.javaClass.name,
            request.getDescription(false).substring(4),
            message,
            HttpStatus.BAD_REQUEST.value(),
            System.currentTimeMillis()
        )
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(response)
    }

    @ExceptionHandler(value = [HttpMessageNotReadableException::class])
    protected fun handleHttpMessageNotReadableException(
        ex: HttpMessageNotReadableException,
        request: WebRequest
    ) : ResponseEntity<Any> {
        val response = ExceptionDTO(
            error = HttpStatus.BAD_REQUEST,
            exception = ex.javaClass.name,
            message = "Проверьте синтаксис тело запроса",
            path = request.getDescription(false).substring(4),
            status = HttpStatus.BAD_REQUEST.value(),
            timestamp = System.currentTimeMillis(),
            details = ex.message
        )
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(response)
    }

    @ExceptionHandler(value = [AbstractApplicationException::class])
    protected fun handleAllAbstractApplicationException(
        ex: AbstractApplicationException,
        request: WebRequest
    ) : ResponseEntity<Any> {
        val response = ExceptionDTO(
            error = ex.getStatus(),
            exception = ex.javaClass.name,
            message = ex.message,
            path = request.getDescription(false).substring(4),
            status = ex.getStatus().value(),
            timestamp = System.currentTimeMillis(),
            details = ex.getDetails()
        )
        return ResponseEntity
            .status(ex.getStatus())
            .body(response)
    }

        protected fun handleAllExceptions(
            ex: Exception,
            request: WebRequest
        ) : ResponseEntity<Any> {
            val status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR

            val response = ExceptionDTO(
                error = status,
                exception = ex.javaClass.name,
                path = request.getDescription(false).substring(4),
                status = status.value(),
                timestamp = System.currentTimeMillis()
            )

            return ResponseEntity
                .status(status)
                .body(response)
        }
}

