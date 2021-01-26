package ProxyCache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)

public @interface ValidListSize {    //Аннотация маркер для проверки - является ли поле Листом.
 int maxSize() default 10;
}
