package ProxyCache;

public interface Concat {

    @Cache(isSerialisable = true , fileName = "E:/Artyr/studyjava/ProjectsServer/resourse/m1")  //Тестовый метод, обычный конкат строк.
    String concat(String one, String two);

    @Cache(isSerialisable = true , addToZip = true, fileName = "E:/Artyr/studyjava/ProjectsServer/resourse/m1")
    @ValidArgsUse(onePar = 2, threePar = 3)                                                      // Игнорирование второго и третьего артументов
    String concat( String one, String two, String three);

    default  void printNull() {
    System.out.println("Zero");
}
}
