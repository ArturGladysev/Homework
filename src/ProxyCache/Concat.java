package ProxyCache;

public interface Concat {

    //Тестовый метод, обычный конкат строк.
    @Cache(isSerialisable = true , fileName = "resourse/cacheFiles/m")
    String concat(String one, String two);

    @Cache(isSerialisable = true , addToZip = true, fileName = "resourse/cacheFiles/m")
    // Игнорирование второго и третьего артументов
    @ValidArgsUse(onePar = 2, threePar = 3)
    String concat( String one, String two, String three);

    default  void printNull() {
    System.out.println("Zero");
}
}
