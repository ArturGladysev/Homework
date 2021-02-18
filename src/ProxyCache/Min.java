package ProxyCache;

public interface Min {
    @Cache(isSerialisable = true, addToZip = true, fileName = "E:/Artyr/studyjava/ProjectsServer/resourse/m1",
            zipName = "E:/Artyr/studyjava/ProjectsServer/resourse.zip")
    int min(int one, int two);

    @Cache(isSerialisable = true,  fileName = "E:/Artyr/studyjava/ProjectsServer/resourse/m1")
    int min(int one);

    @Cache(isSerialisable = true , fileName = "E:/Artyr/studyjava/ProjectsServer/resourse/m1")
    default void printNull() {
        System.out.println("Zero");

    }
}