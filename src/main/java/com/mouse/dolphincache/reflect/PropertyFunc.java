package com.mouse.dolphincache.reflect;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/8/29 22:56
 */
public interface PropertyFunc<T, R> extends Function<T, R>, Serializable {

}
