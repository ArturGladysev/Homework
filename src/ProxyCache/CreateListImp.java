package ProxyCache;

import java.util.Arrays;
import java.util.List;
// Тестовый класс, возвращающий Лист
public class CreateListImp implements CreateList {

    @Override
    public List<Integer> createList() {
 List<Integer> intList = Arrays.asList(1, 2 , 3 , 4 , 5 , 6 , 7 , 8 ,9 ,10 ,11, 12, 13);
    return intList;
    }
}
