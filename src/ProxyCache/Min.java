package ProxyCache;

public interface Min {
    @Cache(isSerialisable = true, addToZip = true, fileName = "resourse/cacheFiles/m3",
            zipName = "resourse/cacheFiles/zipFiles/zipAr.zip")
    int min(int one, int two);

    @Cache(isSerialisable = true,  fileName = "resourse/cacheFiles/m6")
    int min(int one);

    @Cache(isSerialisable = true , fileName = "resourse/cacheFiles/m6")
    default String printNull() {
        return "Zero";

    }
}