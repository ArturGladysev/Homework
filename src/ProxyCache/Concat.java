package ProxyCache;

public interface Concat {

    @Cache(isSerialisable = true , fileName = "E:/Artyr/studyjava/ProjectsServer/resourse/m")  //Тестовый метод, обычный конкат строк.
    String concat(String one, String two);

    @Cache(isSerialisable = true , fileName = "E:/Artyr/studyjava/ProjectsServer/resourse/m")
    @ValidArgsUse(onePar = 2, threePar = 3)                                                      // Игнорирование второго и третьего артументов
    String concat( String one, String two, String three);

    default  void printNull() {
    System.out.println("Zero");
}
}
