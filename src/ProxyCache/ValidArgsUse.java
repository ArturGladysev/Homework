package ProxyCache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ValidArgsUse {             // Аннотация позволяет отключать аргументы, указывая их номера

   public int onePar() default 0;
 int twoPar() default 0;
    int threePar() default 0;

}
