package CachedInvocationHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Аннотация позволяет отключать аргументы, указывая их номера
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ArgumentsIgnore
{
    boolean oneArgument() default true;

    boolean twoArgument() default true;

    boolean threeArgument() default true;

    boolean fourArgument() default true;

    boolean fiveArgument() default true;
}
