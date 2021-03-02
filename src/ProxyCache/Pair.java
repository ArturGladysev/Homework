package ProxyCache;


import java.io.Serializable;
import java.util.*;



//Класс генирурует уникальный ключ на базе имени класса, аргументов и булевых флаго
// для отлючения аргументов при проверки уникальности возвращаемого значения.
// Уникальность проверяется через equals, ключе генерируется через toString
public class Pair<T, U> implements Serializable {
    public boolean[] flagMetodUse;
    public T someMethod;
    public List<U> listArgs;

    public Pair(T method, U[] args) {
        someMethod = method;
        if (args != null) {
            listArgs = Arrays.asList(args);

            flagMetodUse = new boolean[args.length];
            for(int i = 0; i<flagMetodUse.length; ++i) {
                flagMetodUse[i] = true;
            }
        }
    }

    @Override
    public boolean equals(Object pair) {
        if (pair instanceof Pair) {
            String a = this.toString();
            String b = pair.toString();
            if (a.equals(b)) {
                System.out.println(a + "&Equals&" + b);
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode () {
        String s = this.toString();
        return s.hashCode();
    }

    @Override
    public String toString() {
        if (listArgs != null) {
            return someMethod.toString() + "->" + listArgs.toString() + " ParamActive:(" + Arrays.toString(flagMetodUse) + ")";
        }
        else {
            return someMethod.toString() + "->"  + " ParamActive:(none)";
        }
    }

}





















