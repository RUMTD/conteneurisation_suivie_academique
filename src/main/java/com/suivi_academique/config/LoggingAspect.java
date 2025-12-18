package com.suivi_academique.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    /**
     * Pointcut pour tous les contrôleurs
     */
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethods() {}

    /**
     * Pointcut pour tous les services
     */
    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void serviceMethods() {}

    /**
     * Log avant l'exécution d'une méthode de contrôleur
     */
    @Before("controllerMethods()")
    public void logBeforeController(JoinPoint joinPoint) {
        HttpServletRequest request = getCurrentRequest();
        if (request != null) {
            log.info("📥 Requête entrante: {} {} | Méthode: {}.{} | Paramètres: {}",
                    request.getMethod(),
                    request.getRequestURI(),
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    Arrays.toString(joinPoint.getArgs()));
        }
    }

    /**
     * Log après le retour d'une méthode de contrôleur
     */
    @AfterReturning(pointcut = "controllerMethods()", returning = "result")
    public void logAfterController(JoinPoint joinPoint, Object result) {
        log.info("📤 Réponse envoyée: {}.{} | Résultat: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                result != null ? result.getClass().getSimpleName() : "void");
    }

    /**
     * Log en cas d'exception
     */
    @AfterThrowing(pointcut = "controllerMethods() || serviceMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.error("❌ Exception dans: {}.{} | Message: {} | Type: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                exception.getMessage(),
                exception.getClass().getSimpleName(),
                exception);
    }

    /**
     * Log du temps d'exécution des méthodes de service
     */
    @Around("serviceMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - start;

            if (executionTime > 1000) {
                log.warn("⏱️  Méthode lente détectée: {}.{} | Temps: {}ms",
                        joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(),
                        executionTime);
            } else {
                log.debug("⏱️  Temps d'exécution: {}.{} | {}ms",
                        joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(),
                        executionTime);
            }

            return result;
        } catch (Throwable throwable) {
            long executionTime = System.currentTimeMillis() - start;
            log.error("⏱️  Échec après {}ms: {}.{}",
                    executionTime,
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName());
            throw throwable;
        }
    }

    /**
     * Récupère la requête HTTP courante
     */
    private HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }
}