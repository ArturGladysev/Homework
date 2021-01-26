package ProxyCache;

import java.util.List;


public interface CreateList {
    @Cache
    public List<Integer> createList();
}
