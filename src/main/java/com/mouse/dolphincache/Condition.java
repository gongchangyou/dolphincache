package com.mouse.dolphincache;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/8/28 17:56
 */
@FunctionalInterface
interface Condition <T>
{
    boolean check(Comparable key, T value);
}
