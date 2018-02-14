# CAP定理 - CAP Theorem

稍微有一些计算机基础的人都知道，在设计算法的时候，需要考虑时间复杂度和空间复杂度，**时间换空间，空间换时间，**要根据实际情况来选择合适的算法，在时间和空间之间做权衡。系统设计也类似，需要在不同的选择之间做权衡，不过不是时间和空间，而是一致性\(consistency\)和可用性\(availability\)，也就是“**consistency换availability，availability换consistency**”，在整个系统设计的过程中，都要把这句话放在心上，这里说的consistency和availability就是CAP里面的C和A。

根据[维基百科](https://zh.wikipedia.org/wiki/CAP%E5%AE%9A%E7%90%86)

> 在[理论计算机科学](https://zh.wikipedia.org/wiki/%E7%90%86%E8%AB%96%E8%A8%88%E7%AE%97%E6%A9%9F%E7%A7%91%E5%AD%B8)中，**CAP定理**（CAP theorem），又被称作**布鲁尔定理**（Brewer's theorem），它指出对于一个[分布式计算系统](https://zh.wikipedia.org/wiki/%E5%88%86%E5%B8%83%E5%BC%8F%E8%AE%A1%E7%AE%97)来说，不可能同时满足以下三点：
>
> * [一致性](https://zh.wikipedia.org/wiki/%E4%B8%80%E8%87%B4%E6%80%A7)（Consistence） （等同于所有节点访问同一份最新的数据副本）
> * 可用性（[Availability](https://zh.wikipedia.org/wiki/%E5%8F%AF%E7%94%A8%E6%80%A7)）（每次请求都能获取到非错的响应——但是不保证获取的数据为最新数据）
> * 分区容错性（[Network partitioning](https://zh.wikipedia.org/w/index.php?title=Partition_tolerance&action=edit&redlink=1)）（以实际效果而言，分区相当于对通信的时限要求。系统如果不能在时限内达成数据一致性，就意味着发生了分区的情况，**必须就当前操作在C和A之间做出选择**）
>
> 根据定理，**分布式系统只能满足三项中的两项而不可能满足全部三项**。理解CAP理论的最简单方式是想象两个节点分处分区两侧。允许至少一个节点更新状态会导致数据不一致，即丧失了C性质。如果为了保证数据一致性，将分区一侧的节点设置为不可用，那么又丧失了A性质。除非两个节点可以互相通信，才能既保证C又保证A，这又会导致丧失P性质。



