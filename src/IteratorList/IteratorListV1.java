package IteratorList;
import java.util.*;

    public class IteratorListV1 implements Iterator {   //Итератор использует индекс List для итерации и не является дженериком (row тип, просто
        private List list;                               // для наглядности)
        private int index;

        public IteratorListV1(List list) {
            this.list = list;
            index  = list.size()-1;
        }

        @Override
        public boolean hasNext() {
            if(index> 0) {
                return true;
            }
            return false;
        }

        @Override
        public Object next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            else {
                --index;
                return (Object) list.get(index);
            }

        }

}
