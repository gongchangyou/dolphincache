package com.mouse.dolphincache;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/8/26 21:03
 */
@SpringBootTest
public class BinarySearchTest {
    @Test
    void search() {
        int arr [] =new int[]{1,3,4,5,8,4,9};

        Arrays.sort(arr);

        int index1 = Arrays.binarySearch(arr,6);

        int index2 = Arrays.binarySearch(arr,4);

        int index3 = Arrays.binarySearch(arr,0);

        int index4 = Arrays.binarySearch(arr,10);

        System.out.println("index1 = "+ index1 +", index2 = " + index2 +

                ", index3 = " + index3 +", index4 = "+ index4);
    }
}
