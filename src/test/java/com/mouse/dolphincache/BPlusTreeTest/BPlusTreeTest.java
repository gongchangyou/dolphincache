package com.mouse.dolphincache.BPlusTreeTest;

import btree4j.BTree;
import btree4j.BTreeException;
import btree4j.Value;
import btree4j.utils.io.FileUtils;
import com.mouse.dolphincache.BPlusTree;
import lombok.val;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/8/26 21:03
 */
@SpringBootTest
public class BPlusTreeTest {
    @Test
    void multiSearch() {
        for (int m = 2; m < 10; m++) {
            searchRange();
        }
    }

    @Test
    void searchRangeMulti() {
        for (int m = 0; m < 10; m++) {
            searchRange();
        }
    }

    void searchRange() {
        BPlusTree<Integer> bPlusTree = new BPlusTree<>(10);
        val list = new ArrayList<Integer>();
        for (int i =0;i<1000000;i++) {
//            bPlusTree.insert(1000, 1000);
            bPlusTree.insert(i, i);
//            list.add(1000);
            list.add(i);
        }

        val target = 50;
        val sw = new StopWatch();
        sw.start("b+tree");
        val l1 = bPlusTree.search(target, target, (key, value)-> key.compareTo(50) == 0);
        sw.stop();
        sw.start("stream");
        val list2 = list.stream().filter(i -> i==target).collect(Collectors.toList());
        sw.stop();
        System.out.println(sw.prettyPrint());
    }

    @Test
    void searchRange1Multi() {
        for (int m = 0; m < 10; m++) {
            searchRange1();
        }
    }

    @Test
    void searchRange1() {
        BPlusTree<Integer> bPlusTree = new BPlusTree(10);
        val list = new ArrayList<Integer>();
        for (int i =0;i<100000;i++) {
            bPlusTree.insert(i, i);
            list.add(i);
        }


        val sw = new StopWatch();
        sw.start("b+tree");
        val list1 = bPlusTree.search(200, 500, (key, value) -> key.compareTo(200) > 0 && key.compareTo(500) <= 0);
        sw.stop();
        sw.start("stream");
        val list2 = list.stream().filter(i -> i>=200 && i<=500).collect(Collectors.toList());
        sw.stop();

        System.out.println(sw.prettyPrint());

    }

}
