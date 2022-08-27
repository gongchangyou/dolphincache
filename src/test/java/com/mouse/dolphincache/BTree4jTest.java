package com.mouse.dolphincache;

import btree4j.BTree;
import btree4j.BTreeCallback;
import btree4j.BTreeException;
import btree4j.Value;
import btree4j.indexer.BasicIndexQuery;
import btree4j.utils.io.FileUtils;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/8/27 09:35
 */
public class BTree4jTest {

    @Test
    public void test() throws BTreeException {
        File tmpDir = FileUtils.getTempDir();
        Assert.assertTrue(tmpDir.exists());
        File tmpFile = new File(tmpDir, "BTreeTest1.idx");
        tmpFile.deleteOnExit();
        if (tmpFile.exists()) {
            Assert.assertTrue(tmpFile.delete());
        }

        BTree btree = new BTree(tmpFile);
        btree.init(/* bulkload */ false);

        for (int i = 0; i < 1000; i++) {
            Value k = new Value("k" + i);
            long v = i;
            btree.addValue(k, v);
        }

        for (int i = 0; i < 1000; i++) {
            Value k = new Value("k" + i);
            long expected = i;
            long actual = btree.findValue(k);
            Assert.assertEquals(expected, actual);
        }

        btree.search(new BasicIndexQuery.IndexConditionBW(new Value("k" + 900), new Value("k" + 910)),
                new BTreeCallback() {

                    @Override
                    public boolean indexInfo(Value value, long pointer) {
                        //System.out.println(pointer);
                        return true;
                    }

                    @Override
                    public boolean indexInfo(Value key, byte[] value) {
                        throw new UnsupportedOperationException();
                    }
                });
    }
}
