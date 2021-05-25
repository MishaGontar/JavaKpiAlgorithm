package com.lab8.container;

import com.lab8.hashtable.HashTable;
import com.lab8.type.TwoNumbers;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;


class HashTableTest {

    private static final Random RANDOM = new Random(System.nanoTime());
    static ArrayList<TwoNumbers> list = new ArrayList<>();

    @Test
    public void test() {
        HashTable<TwoNumbers, LinkedList> tableList = new HashTable<>(LinkedList.class);
        testTable(tableList, LinkedList.class);
        System.out.println('\n');
        HashTable<TwoNumbers, BinaryTree> tableTree = new HashTable<>(BinaryTree.class);
        testTable(tableTree, BinaryTree.class);
    }

    private void testTable(HashTable<TwoNumbers, ? extends Container> table, Class<? extends Container> typeContainer) {
        System.out.println("Test HashTable with Container: " + typeContainer.getSimpleName());
        System.out.println("time : " + testAdd(table, 1_000));
        System.out.println("time : " + testContains(table));
        list.clear();
        System.out.println("time : " + testAdd(table, 10_000));
        System.out.println("time : " + testContains(table));
        list.clear();
        System.out.println("time : " + testAdd(table, 100_000));
        System.out.println("time : " + testContains(table));
        list.clear();
        System.out.println("time : " + testAdd(table, 200_000));
        System.out.println("time : " + testContains(table));
        list.clear();
        System.out.println("time : " + testAdd(table, 500_000));
        System.out.println("time : " + testContains(table));
        list.clear();
        System.out.println("time : " + testAdd(table, 1_000_000));
        System.out.println("time : " + testContains(table));
    }

    private String  testAdd(HashTable<TwoNumbers, ? extends Container> table, int quantity) {
        System.out.print("Hash insert: " + quantity+"; ");
        fillingList(quantity);
        Instant starts = Instant.now();

        for (TwoNumbers twoNumbers : list) {
            table.add(twoNumbers);
        }

        Instant ends = Instant.now();
        String answer = Duration.between(starts, ends).toNanos()+ " ns; (Millis) :" + Duration.between(starts,ends).toMillis() + " ms ";
        return answer ;
    }

    private String testContains(HashTable<TwoNumbers, ? extends Container> table) {
        System.out.print("Hash search; ");
        Instant starts = Instant.now();

        for (int i = 0; i < 500; i++) {
            table.contains(list.get(RANDOM.nextInt(list.size())));
        }

        Instant ends = Instant.now();
        String answer = Duration.between(starts, ends).toNanos()+ " ns; (Millis) :" + Duration.between(starts,ends).toMillis() + " ms ";
        return answer ;
    }

    private void fillingList(int quantity) {
        for (int i = 0; i < quantity; i++) {
            TwoNumbers twoNumbers = TwoNumbers
                    .builder()
                    .x(RANDOM.nextInt(1000) + 1)
                    .y(RANDOM.nextInt(1000) + 1)
                    .build();
            list.add(twoNumbers);
        }
    }

}