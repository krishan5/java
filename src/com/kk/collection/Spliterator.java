package com.kk.collection;

import java.util.Arrays;
import java.util.List;

public class Spliterator {
    public static void main(String[] args) {
        spliterator();
    }

    private static void spliterator() {
        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7,8);
        java.util.Spliterator rightHalf = numbers.spliterator();

        //leftHalf = 1,2,3,4 ; rightHalf = 5,6,7,8
        java.util.Spliterator leftHalf = rightHalf.trySplit();

        //firstLeftQuarter = 1,2 ; leftHalf = 3,4
        java.util.Spliterator firstLeftQuarter = leftHalf.trySplit();

        //firstRightQuarter = 5,6 ; rightHalf = 7,8
        java.util.Spliterator firstRightQuarter = rightHalf.trySplit();

        firstLeftQuarter.forEachRemaining(System.out::println); //1,2
        System.out.println("-------");
        leftHalf.forEachRemaining(System.out::println); //3,4
        System.out.println("-------");
        firstRightQuarter.forEachRemaining(System.out::println); //5,6
        System.out.println("-------");
        rightHalf.forEachRemaining(System.out::println); //7,8
    }
}
