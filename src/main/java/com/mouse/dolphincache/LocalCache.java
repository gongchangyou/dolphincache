package com.mouse.dolphincache;

import com.mouse.dolphincache.annotations.QuerySqlField;
import com.mouse.dolphincache.reflect.PropertyFunc;
import com.mouse.dolphincache.reflect.ReflectionFieldName;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.val;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/8/28 21:47
 */
@Builder
public class LocalCache<T> {
    @Builder.Default
    private Map<String, BPlusTree<T>> bPlusTreeMap = new HashMap<>();

    //默认 度为10
    @Builder.Default
    private int m = 10;

    //lazy load
    @SneakyThrows
    public void insert(T row){
        for (Field f : row.getClass().getDeclaredFields()) {
            if (f.getAnnotation(QuerySqlField.class) != null) {
                val key = f.getDeclaringClass().getName() + "#"  + f.getName();
                if(!bPlusTreeMap.containsKey(key)) {
                    val tree = new BPlusTree<>(m);
                    bPlusTreeMap.put(key, new BPlusTree<>(m));
                }
                f.setAccessible(true);
                bPlusTreeMap.get(key).insert((Comparable) f.get(row), row);
            }
        }
    }

    public List<T> search(PropertyFunc<T, ?> property, Comparable lowerBound, Comparable upperBound) {
        val className = ReflectionFieldName.getClassName(property);
        val fieldName = ReflectionFieldName.getFieldName(property);

        val key = className + "#"  + fieldName;
        return bPlusTreeMap.get(key).search(lowerBound, upperBound, (k, value) -> {
            return k.compareTo(lowerBound) >=0 && k.compareTo(upperBound) <=0;
        });
    }

}
