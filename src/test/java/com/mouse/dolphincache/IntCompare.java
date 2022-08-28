package com.mouse.dolphincache;

import lombok.val;
import org.junit.jupiter.api.Test;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/8/28 14:31
 */
public class IntCompare {

    @Test
    void greater() {
        Integer a = 20;
        Integer b = 20;
        System.out.println(a.compareTo(b));
    }
}