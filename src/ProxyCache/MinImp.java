package ProxyCache;

public class MinImp implements Min {
    @Override
    public int min(int one, int two) {
       if (one < two) {return one;}
       else { return two;}
    }

    @Override
    public int min(int one) {
        return one;
    }
}
