package br.com.invite.config

import javassist.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.servlet.http.HttpServletRequest


@ControllerAdvice
class ExceptionHandlers : ResponseEntityExceptionHandler() {

    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handleBusinessException(req: HttpServletRequest, ex: Throwable): ResponseEntity<ErrorInfo> {
        val errorInfo = ErrorInfo()
        errorInfo.message = ex.message
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorInfo)
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseBody
    fun handlerNotFoundBusinessException(req: HttpServletRequest, ex: Throwable): ResponseEntity<ErrorInfo> {
        val errorInfo = ErrorInfo()
        errorInfo.message = ex.message
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorInfo)
    }

}

class ErrorInfo {
    var message: String? = null
}