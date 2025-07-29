package com.kbc.KbcApp.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class AspectsLogging {

    @Pointcut("execution(* com.kbc.KbcApp.restcontrollers.UserController.login(..))")
    public void loginMethod() {}
    @Pointcut("execution(* com.kbc.KbcApp.restcontrollers.QuestionRestController.getQuestions(..))")
    public void gameStartMethod() {}

    @Pointcut("execution(* com.kbc.KbcApp.restcontrollers.GameController.addGame(..))")
    public void gameEndMethod() {}
    
    @Pointcut("execution(* com.kbc.KbcApp.restcontrollers.UserController.logout(..))")
    public void logoutMethod() {}

    @Around("loginMethod()")
    public Object logLogin(ProceedingJoinPoint joinPoint) throws Throwable {
    	System.out.println("Login attempt");
        Object result = joinPoint.proceed();
        System.out.println("Login success");
        return result;
    }

    @AfterThrowing(pointcut = "loginMethod()", throwing = "ex")
    public void afterLoginThrowing(Throwable ex) {
        System.out.println("Login exception: " + ex.getMessage());
    }

    @AfterReturning(pointcut = "loginMethod()", returning = "result")
    public void afterLoginReturning(Object result) {
        System.out.println("Login returned: " + result);
    }
    
    @Around("logoutMethod()")
    public Object logLogout(ProceedingJoinPoint joinPoint) throws Throwable {
    	System.out.println("Logout attempt");
        Object result = joinPoint.proceed();
        System.out.println("Logout success");
        return result;
    }

    @AfterThrowing(pointcut = "logoutMethod()", throwing = "ex")
    public void afterLogoutThrowing(Throwable ex) {
        System.out.println("Logout exception: " + ex.getMessage());
    }

    @AfterReturning(pointcut = "logoutMethod()", returning = "result")
    public void afterLogoutReturning(Object result) {
        System.out.println("Logout returned: " + result);
    }
    
    
    
    

    @Around("gameStartMethod()")
    public Object logGameStart(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Game start");
        Object result = joinPoint.proceed();
        return result;
    }

    @AfterThrowing(pointcut = "gameStartMethod()", throwing = "ex")
    public void afterGameStartThrowing(Throwable ex) {
        System.out.println("Game start exception: " + ex.getMessage());
    }

    @AfterReturning(pointcut = "gameStartMethod()", returning = "result")
    public void afterGameStartReturning(Object result) {
        System.out.println("Game start returned: " + result);
    }

    @Around("gameEndMethod()")
    public Object logGameEnd(ProceedingJoinPoint joinPoint) throws Throwable {
    	System.out.println("Game end");
        Object result = joinPoint.proceed();
        return result;
    }

    @AfterThrowing(pointcut = "gameEndMethod()", throwing = "ex")
    public void afterGameEndThrowing(Throwable ex) {
        System.out.println("Game end exception: " + ex.getMessage());
    }

    @AfterReturning(pointcut = "gameEndMethod()", returning = "result")
    public void afterGameEndReturning(Object result) {
        System.out.println("Game end returned: " + result);
    }
}