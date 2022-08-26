# dolphincache
b+tree based cache

本意是想在内存中构造出一棵B+树，希望根据索引查询能够更快一些，但是尝试几个m的取值之后发现查询速度不稳定，还不如使用list.stream().filter()
直接流式筛选一把梭。唉
