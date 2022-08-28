package com.mouse.dolphincache;

import btree4j.BTree;
import btree4j.BTreeCallback;
import btree4j.BTreeException;
import btree4j.Value;
import btree4j.indexer.BasicIndexQuery;
import btree4j.utils.io.FileUtils;
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
public class BPlusTreeTest2 {
    @Test
    void multiSearch() {
        for (int m = 2; m < 10; m++) {
            searchRange(m);
            searchIndex(m);
            searchDuplicateKey(m);
        }
    }

    void searchDuplicateKey(int m) {
        val bTree = getBTree();
        BPlusTree<Double> bPlusTree = new BPlusTree(m);
        val list = new ArrayList<Integer>();
        val r = new Random();
        for (int i = 0; i < 100000; i++) {
            val key = r.nextInt(2000);
            val value = r.nextDouble() * 1000d;
            bPlusTree.insert(key, value);
            list.add(key);
            try {
                bTree.addValue( new Value(key), Double.valueOf(value).hashCode());
            } catch (BTreeException e) {
                e.printStackTrace();
            }
        }

        val sw = new StopWatch();
        sw.start("b+tree");
        val result = bPlusTree.search(1000, 1000, null);
        sw.stop();
        sw.start("stream");
        val result2 = list.stream().filter((i)->  {
            return i == 1000;
        }).collect(Collectors.toList());
        sw.stop();


        System.out.println("searchDuplicateKey m="+ m
                +" result=" + result.size()
                + "result2 =" +result2.size()
                + sw.prettyPrint());
    }

    void searchRange(int m) {
//        val bTree = getBTree();
        BPlusTree<Integer> bPlusTree = new BPlusTree(m);
        val list = new ArrayList<Integer>();
        val r = new Random();
        for (int i = 0; i < 1000000; i++) {
//            if (i > 1000 && i < 1050) {
//                continue;
//            }
            val key = r.nextInt(2000);
            val value = r.nextDouble() * 1000d;
            bPlusTree.insert(i,i);
            list.add(i);
//            try {
//                bTree.addValue( new Value(key), key);
//            } catch (BTreeException e) {
//                System.out.println(e);
////                e.printStackTrace();
//            } catch (Exception e) {
//                System.out.println(e);
//            }
        }

        val sw = new StopWatch();
        sw.start("b+tree");
        val result = bPlusTree.search(1020, 1030, (key, value) -> key.compareTo(1020) > 0 && key.compareTo(1032) <= 0);
        sw.stop();
        sw.start("stream");
        val result2 = list.stream().filter((i)->  {
                return i < 2000 && i> 1000;
        }).collect(Collectors.toList());
        sw.stop();

//        sw.start("btree4j");
//        val result3 = new ArrayList<>();
//        try {
//            bTree.search(new BasicIndexQuery.IndexConditionBW(new Value(1000), new Value(2000)),
//                    new BTreeCallback() {
//
//                        @Override
//                        public boolean indexInfo(Value value, long pointer) {
//                            //System.out.println(pointer);
//                            result3.add(pointer);
//                            return true;
//                        }
//
//                        @Override
//                        public boolean indexInfo(Value key, byte[] value) {
//                            throw new UnsupportedOperationException();
//                        }
//                    });
//        } catch (BTreeException e) {
//            e.printStackTrace();
//        }
//        sw.stop();

        System.out.println("searchRange m="+ m+" result=" + result.size() + "result2 =" +result2.size()
//                + "result3=" + result3.size()
                + sw.prettyPrint());
    }

    void searchIndex(int m) {
        BPlusTree bPlusTree = new BPlusTree(m);
        val list = new ArrayList<Integer>();
        val r = new Random();
        for (int i = 0; i < 10000; i++) {
            val key = r.nextInt(2000);
            val value = r.nextDouble() * 1000d;
            bPlusTree.insert(key, value);
            list.add(key);
        }

        val sw = new StopWatch();
        sw.start("b+tree");
        val target = 1000;
        val result = bPlusTree.search(target);
        sw.stop();
        sw.start("stream");
        val result2 = list.stream().filter((i)->  {
            return i==target;
        }).collect(Collectors.toList());
        sw.stop();

        System.out.println("search exactly index m="+ m+" result=" + result + "result2 =" +result2.size() + sw.prettyPrint());
    }


    private BTree getBTree() {
        File tmpDir = FileUtils.getTempDir();
        Assert.assertTrue(tmpDir.exists());
        File tmpFile = new File(tmpDir, "BTreeTest1.idx");
        tmpFile.deleteOnExit();
        if (tmpFile.exists()) {
            Assert.assertTrue(tmpFile.delete());
        }

        val bTree =  new BTree(tmpFile);
        try {
            bTree.init(false);
        } catch (BTreeException e) {
            e.printStackTrace();
        }
        return bTree;
    }
}
