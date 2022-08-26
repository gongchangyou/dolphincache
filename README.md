# dolphincache
b+tree based cache

本意是想在内存中构造出一棵B+树，希望根据索引查询能够更快一些，但是尝试几个m的取值之后发现查询速度不稳定，


平衡树表现 和 极端情况比如只有半边的树 都还不如使用 list.stream().filter() 直接流式筛选一把梭。
1. search 单个元素 B树效果拔群！ m在 [3,9] 都能做到 和 list.stream().filter() 1:99的耗时, m=4或5时效果最好.
2. 如果我们做成一棵真正的B+树(把value放到leaf上 ，且 每个leafNode指向下一个leaf， 我想范围查询的速度会快很多 TODO)