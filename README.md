# dolphincache
b+tree based cache

本意是想在内存中构造出一棵B+树，希望根据索引查询能够更快一些，但是尝试几个m的取值之后发现查询速度不稳定，


平衡树表现 和 极端情况比如只有半边的树 都还不如使用 list.stream().filter() 直接流式筛选一把梭。
1. search 单个元素 B树效果拔群！ m在 [3,9] 都能做到 和 list.stream().filter() 1:99的耗时, m=4或5时效果最好.
2. 如果我们做成一棵真正的B+树(把value放到leaf上 ，且 每个leafNode指向下一个leaf， 我想范围查询的速度会快很多 TODO)


### 开源组件 btree4j
```
<dependency>
    <groupId>io.github.myui</groupId>
    <artifactId>btree4j</artifactId>
    <version>0.9.1</version>
</dependency>
```

开源框架*btree4j*的表现搜索单个key的表现不俗，比流式查找快很多。
注意到BTree.java line 1227行, ptrs 有上下确界的范围查找了。

```
for(i = leftIdx; i < this.ptrs.length; ++i) {
if (i <= rightIdx && query.testValue(this.keys[i])) {
callback.indexInfo(this.keys[i], this.ptrs[i]);
}
}
```

10w - 100w 时 ， B+tree性能优于流式， 大于1000w 数据可能overflow.

但是 IndexConditionBW 实例化依然耗时较久 , 居然占到了search耗时的一半。

000001026  000%  o1
000000170  000%  o2
000504932  011%  IndexConditionBW
001124575  025%  search
002851406  064%  list