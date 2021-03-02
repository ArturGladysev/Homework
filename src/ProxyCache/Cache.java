package ProxyCache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//Аннотация является маркером кэширования метода, позволяет установить флаг сериализации,  указать путь
// к файлу и установить допустимый размер хранения значений.

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {
    boolean isSerialisable() default false;
    String fileName() default "resourse/cacheFiles/m1";
int maxSizeCacheValues() default 10000;
 boolean addToZip() default false;
String zipName () default  "resourse/cacheFiles/zipFiles/zipAr.zip";
}
