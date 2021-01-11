package IteratorList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        String[] one_mas = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
        ArrayList<String> array = new ArrayList<String>(Arrays.asList(one_mas));
        LinkedList<String> link = new LinkedList<String>(Arrays.asList(one_mas));
        IteratorListV1 it_array = new IteratorListV1(array);
        IteratorListV1 it_link = new IteratorListV1(link);

        System.out.println("Array reverse one variant: ");
        while (it_array.hasNext()) {
            String word = (String) it_array.next();
            System.out.println(word + " ");
        }
        System.out.println("Linked reverse one variant: ");
        while (it_link.hasNext()) {
            String word2 = (String) it_link.next();
            System.out.println(word2 + " ");

        }
        IteratorListV2<String> it_array_v2 = new IteratorListV2<String> (array);
        IteratorListV2<String> it_link_v2 = new IteratorListV2<String> (link);

        System.out.println("Array reverse two variant: ");
        while (it_array_v2.hasNext()) {
            String word3 = it_array_v2.next();
            System.out.println(word3 + " ");
        }
        System.out.println("Linked reverse two variant: ");
        while (it_link_v2.hasNext()) {
            String word4 = it_link_v2.next();
            System.out.println(word4 + " ");

        }
    }
}
