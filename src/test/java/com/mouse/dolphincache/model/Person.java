package com.mouse.dolphincache.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/8/27 15:35
 */
@Data
@Builder
public class Person {
    private long id;
    private String name;
    private int age;

}
