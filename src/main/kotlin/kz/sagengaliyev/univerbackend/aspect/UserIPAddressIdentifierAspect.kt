package kz.sagengaliyev.univerbackend.aspect

import jakarta.servlet.http.HttpServletRequest
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Aspect
@Component
class UserIPAddressIdentifierAspect(
    private val logger: Logger = LoggerFactory.getLogger(UserIPAddressIdentifierAspect::class.java)
) {

    @Pointcut("execution(* kz.sagengaliyev.univerbackend.controller.*.*(..))")
    fun IPLoggingPointCut(){}

    @After("IPLoggingPointCut()")
    fun after(joinPoint: JoinPoint) {
        val requestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
        val request: HttpServletRequest = requestAttributes.request

        val ipAddress = request.getHeader("X-Forwarded-For") ?: request.remoteAddr

        val methodSignature = joinPoint.signature.toShortString()


        logger.info("User with IP address ${ipAddress} called the method: ${methodSignature} ")

    }

    @AfterThrowing("IPLoggingPointCut()", throwing = "ex")
    fun logWhenAccessDenied(ex: Throwable) {
        val requestAttributes = RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes
        val request: HttpServletRequest? = requestAttributes?.request

        val ipAddress = request?.let {
            it.getHeader("X-Forwarded-For") ?: it.remoteAddr
        }

        logger.error("Exception occured for IP address: $ipAddress with reason - ${ex.message}")

    }
}