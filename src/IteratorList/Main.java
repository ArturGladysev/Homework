package IteratorList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/*
Задача: написать итератор, принимающий List, но перебирающий элементы в обратном порядке.
 */

public class Main {
    public static void main(String[] args)
    {
        String[] stringArray = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(stringArray));
        LinkedList<String> linkedList = new LinkedList<String>(Arrays.asList(stringArray));
        IteratorListV1 iteratorV1FromArrayList = new IteratorListV1(arrayList);
        IteratorListV1 iteratorV1FromLinkedList = new IteratorListV1(linkedList);

        String value;
        System.out.println("ArrayList reverse by IteratorListV1 variant: ");
        while(iteratorV1FromArrayList.hasNext())
        {
            value = (String)iteratorV1FromArrayList.next();
            System.out.println(value + " ");
        }

        System.out.println("LinkedList reverse by IteratorListV1 variant: ");
        while(iteratorV1FromLinkedList.hasNext())
        {
            value = (String)iteratorV1FromLinkedList.next();
            System.out.println(value + " ");
        }

        IteratorListV2<String> iteratorV2FromArrayList = new IteratorListV2<String> (arrayList);
        IteratorListV2<String> iteratorV2FromLinkedList = new IteratorListV2<String> (linkedList);

        System.out.println("ArrayList reverse by IteratorListV2 variant: ");
        while (iteratorV2FromArrayList.hasNext()) {
            value = iteratorV2FromArrayList.next();
            System.out.println(value + " ");
        }

        System.out.println("LinkedList reverse by IteratorListV2 variant: ");
        while(iteratorV2FromLinkedList.hasNext())
        {
            value = iteratorV2FromLinkedList.next();
            System.out.println(value + " ");
        }
    }
}
