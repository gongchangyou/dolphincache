# dolphincache
b+tree based cache

本意是想在内存中构造出一棵B+树，希望根据索引查询能够更快一些


平衡树表现 和 极端情况比如只有半边的树 都还不如使用 list.stream().filter() 直接流式筛选一把梭。
1. search 单个元素 B树效果拔群！ m在 [3,9] 都能做到 和 list.stream().filter() 1:99的耗时, m=4或5时效果最好.
2. 如果我们做成一棵真正的B+树, 把value放到leaf上 ，且 每个leafNode指向下一个leaf(sibling)， 我想范围查询的速度会快很多

BPlusTree 在元素为1000时,效果不明显，耗时大约是流式查找的20%~100%
100,000~ 1,000,000的效果最好, 耗时会锐减到list查找的百分之一!



### 开源组件 btree4j
```
<dependency>
    <groupId>io.github.myui</groupId>
    <artifactId>btree4j</artifactId>
    <version>0.9.1</version>
</dependency>
```

开源框架*btree4j*的表现搜索单个key的表现不俗，比流式查找快很多。

但是里面把key 转成byte[] 的过程中，129 会被转成 -127, 这样构造出的树有问题。



注意到BTree.java line 1227行, ptrs 有上下确界的范围查找了。

```
for(i = leftIdx; i < this.ptrs.length; ++i) {
if (i <= rightIdx && query.testValue(this.keys[i])) {
callback.indexInfo(this.keys[i], this.ptrs[i]);
}
}
```

10w - 100w 时 ， B+tree性能优于流式， 大于1000w 数据可能overflow.

但是 IndexConditionBW 首次实例化耗时较久 , 会占到了search耗时的一半。 大概因为JIT,所以后续再创建耗时会下来。

000001026  000%  o1
000000170  000%  o2
000504932  011%  IndexConditionBW
001124575  025%  search
002851406  064%  list

多调用search几次性能也会提升，跟steam().filter 可以拉开10~20倍的差距，btree4j还是非常快的。 