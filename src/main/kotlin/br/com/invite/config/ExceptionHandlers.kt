package br.com.invite.config

import javassist.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest


@ControllerAdvice
class ExceptionHandlers {

    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handleBusinessException(req: HttpServletRequest, ex: Throwable): ResponseEntity<ErrorInfo> {
        val errorInfo = ErrorInfo()
        errorInfo.message = ex.message
        return status(HttpStatus.BAD_REQUEST).body(errorInfo)
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseBody
    fun handlerNotFoundBusinessException(req: HttpServletRequest, ex: Throwable): ResponseEntity<ErrorInfo> {
        val errorInfo = ErrorInfo()
        errorInfo.message = ex.message
        return status(HttpStatus.NOT_FOUND).body(errorInfo)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(error: MethodArgumentNotValidException): ResponseEntity<*> {
        return status(HttpStatus.NOT_FOUND).body(error.bindingResult)
    }

}

class ErrorInfo {
    var message: String? = null
}