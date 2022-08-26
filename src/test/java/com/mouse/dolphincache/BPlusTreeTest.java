package com.mouse.dolphincache;

import com.mouse.dolphincache.BPlusTree;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

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
            search(m);
        }
    }

    @Test
    void search(int m) {
        BPlusTree bPlusTree = new BPlusTree(m);
        val list = new ArrayList<Integer>();
        val r = new Random();
        for (int i = 0; i < 100000; i++) {
            val key = r.nextInt(2000);
            val value = r.nextDouble() * 1000d;
            bPlusTree.insert(key, value);
            list.add(key);
        }

        val sw = new StopWatch();
        sw.start("b+tree");
        val result = bPlusTree.search(1000, 2000);
        sw.stop();
        sw.start("stream");
        val result2 = list.stream().filter((i)-> (i < 2000 && i> 1000)).collect(Collectors.toList());
        sw.stop();

        System.out.println("m="+ m+" result=" + result.size() + "result2 =" +result2.size() + sw.prettyPrint());
    }
}
