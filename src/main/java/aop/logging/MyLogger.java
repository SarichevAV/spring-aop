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

    @Pointcut("execution(* aop.objects.Manager.*(..))")
    public void managerMethods() {}

    @Pointcut("execution(java.util.Map *(..))")
    public void returnMap() {}

    @Pointcut("execution(java.util.Set *(..))")
    public void returnSet() {}

    @Around("managerMethods() && execution(java.util.Map *(..))")
    public Object watchTime(ProceedingJoinPoint joinPoint) {
        long start = System.currentTimeMillis();
        System.out.println("\nmethod begins: " + joinPoint.getSignature().toShortString());
        Object output = null;

        try {
            output = joinPoint.proceed(new Object[] { "/Users/admin/Documents" });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        long time = System.currentTimeMillis() - start;
        System.out.println("method end: " + joinPoint.getSignature().toShortString() + ", time=" + time + " ms");

        return output;
    }


    @AfterReturning(pointcut = "managerMethods() && execution(java.util.Set *(String)) && args(folder)", returning = "object")
    public void printSet(Object object, String folder) {

        System.out.println("Print Set begin >>");

        Set types = (Set) object;
        for (Object type : types) {
            System.out.println(type);
        }

        System.out.println("<< Print Set end");
    }

    @AfterReturning(pointcut = "managerMethods() && execution(java.util.Map *(..))", returning = "object")
    public void printMap(Object object) {

        System.out.println("Print Map begin >>");

        Map<String, Integer> types = (Map<String, Integer>) object;
        for (Map.Entry<String, Integer> entry: types.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }

        System.out.println("<< Print Map end");
        System.out.println();
    }
}
