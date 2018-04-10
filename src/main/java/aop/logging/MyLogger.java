package aop.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
@Aspect
public class MyLogger {

    @Pointcut("execution(* *(..))")
    public void allMethods() {}

    @Around("allMethods() && @annotation(aop.annotations.ShowTime)")
    public Object watchTime(ProceedingJoinPoint joinPoint) {
        long start = System.currentTimeMillis();
        System.out.println("\nmethod begins: " + joinPoint.getSignature().toShortString());
        Object output = null;

        try {
            output = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        long time = System.currentTimeMillis() - start;
        System.out.println("method end: " + joinPoint.getSignature().toShortString() + ", time=" + time + " ms");

        return output;
    }

    @AfterReturning(pointcut = "allMethods() && @annotation(aop.annotations.ShowResult)", returning = "object")
    public void showInfo(Object object) {

        if (object instanceof Set) {
            Set types = (Set) object;
            for (Object type : types) {
                System.out.println(type);
            }
        }

        if (object instanceof Map) {
            Map<String, Integer> types = (Map<String, Integer>) object;
            for (Map.Entry<String, Integer> entry: types.entrySet()) {
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
        }
    }
}
