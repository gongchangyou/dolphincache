package com.mouse.dolphincache;

import lombok.val;
import org.junit.jupiter.api.Test;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/8/27 19:40
 */
public class StringCompare {
    /**
     * < 0
     *
     */
    @Test
    void less() {
        val a = "abc";
        val b = "abe";
        System.out.println(a.compareTo(b));
    }

    /**
     * > 0
     */
    @Test
    void greater() {
        val a = "abh";
        val b = "abe";
        System.out.println(a.compareTo(b));
    }

    /**
     * = 0
     */
    @Test
    void eq() {
        val a = "abe";
        val b = "abe";
        System.out.println(a.compareTo(b));
    }
}
