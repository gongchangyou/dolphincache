package com.mouse.dolphincache;

import btree4j.BTree;
import btree4j.BTreeCallback;
import btree4j.BTreeException;
import btree4j.Value;
import btree4j.indexer.BasicIndexQuery;
import btree4j.utils.io.FileUtils;
import com.mouse.dolphincache.model.Person;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/8/27 15:36
 */
public class PersonTest {
    @Test
    public void randRange() {
        val r = new Random();
        for (int i = 0; i < 10; i++) {
            val end = r.nextInt(1000);
            val start = r.nextInt(end);

            try {
                test(start, end);
            } catch (BTreeException e) {
                e.printStackTrace();
            }
        }
    }

    public void test(long start, long end) throws BTreeException {
        File tmpDir = FileUtils.getTempDir();
        Assert.assertTrue(tmpDir.exists());
        File tmpFile = new File(tmpDir, "BTreeTest1.idx");
        tmpFile.deleteOnExit();
        if (tmpFile.exists()) {
            Assert.assertTrue(tmpFile.delete());
        }

        BTree btree = new BTree(tmpFile);
        btree.init(/* bulkload */ false);

        val list = new ArrayList<Person>();
        val map = new HashMap<Long, Person>();
        val r = new Random();
        for (int i = 0; i < 10000; i++) {
            val  p = Person.builder()
                    .id(i)
                    .name("name" + i)
                    .age(r.nextInt(100))
                    .build();
            list.add(p);
            Value k = new Value(i);
            btree.addValue(k, p.hashCode());
            map.put((long) p.hashCode(), p);
        }

val result = new ArrayList<>();
        BTreeCallback callback = new BTreeCallback() {

            @Override
            public boolean indexInfo(Value value, long pointer) {
                //System.out.println(pointer);
                result.add(map.get(pointer));
                return true;
            }

            @Override
            public boolean indexInfo(Value key, byte[] value) {
                throw new UnsupportedOperationException();
            }
        };

        BasicIndexQuery.IndexConditionBW bw = new BasicIndexQuery.IndexConditionBW(new Value(start), new Value(end));

        val sw = new StopWatch();
        sw.start("b+tree");
        btree.search(bw, callback);
        sw.stop();

        sw.start("list");
        val resultList = list.stream().filter(p -> p.getId()<=end && p.getId()>=start).collect(Collectors.toList());
        sw.stop();
        System.out.println(start + " -- " + end);
        System.out.println("result =" + result.size() + "resultList=" + resultList.size() + sw.prettyPrint());
    }
}
