package com.mouse.dolphincache.localcache;

import com.mouse.dolphincache.LocalCache;
import com.mouse.dolphincache.model.Person;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/8/28 22:36
 */
public class LocalCacheTest {

    @SneakyThrows
    @Test
    void personTest() {
        LocalCache<Person> lc = LocalCache.<Person>builder().build();
        val list = new ArrayList<Person>();
        for (int i = 0; i < 100000; i++) {
            val p = Person.builder()
                    .id(i)
                    .name("name" + i)
                    .age(i)
                    .build();
            lc.insert(p);
            list.add(p);
        }

        //you'd better run several times. JIT will speed up b+tree search
        val r = new Random();
        for (int i = 0; i < 10; i++) {
            val end = r.nextInt(10000);
            val start = r.nextInt(end);
            val sw = new StopWatch();
            sw.start("b+tree");
            val result = lc.search(Person.class.getDeclaredField("age"), start, end);
            sw.stop();
            sw.start("stream");
            list.stream().filter(p -> p.getAge() >= start && p.getAge() <= end).collect(Collectors.toList());
            sw.stop();

            System.out.println("start="+start
                    +"end="+end
                    + sw.prettyPrint());
        }

    }
}
