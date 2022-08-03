package CachedInvocationHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/*Аннотация является маркером кэширования метода, позволяет установить флаг сериализации,
 указать путь к файлу и установить допустимое количество значений в кэше.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache
{
    int maxCachedValues() default 10000;

    String fileName() default "resource/cacheFiles/file1";

    boolean isSerializable() default false;

}
