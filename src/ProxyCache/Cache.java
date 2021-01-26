package ProxyCache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)         //Аннотация является маркером кэширования метода, позволяет установить флаг сериализации, указать путь
@Target(ElementType.METHOD)           // к файлу и установить допустимый размер хранения значений.
public @interface Cache {
    boolean isSerialisable() default false;
    String fileName() default "E:/Artyr/studyjava/ProjectsServer/resourse/m"; //Нужен Корректный путь к файлу
int maxSizeCacheValues() default 10000;
//boolean addToZip() default false;
}
