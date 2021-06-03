# 复制，分区 Replication and Partitioning

## 复制
当一个系统（通常是数据存储服务）需要承载大量请求的时候，单台服务器就会成为瓶颈，同时也存在单点失败（single point of failure）的问题，所以这时候我们就需要扩容，使用多台机器来处理请求，以提高系统可用性（availability）。
在扩容的时候，我们需要保持多台机器上的数据一致，否则就会出现冲突，影响用户体验，所以我们需要用不同的方法来复制（replication），以保证数据一致。 

### 复制模式
常见的复制模式有一下几种
#### Single-Leader (Lead-Follower, Master-Slave)
中文一般称之为“主从复制”，既选某个节点为主节点（leader），其他节点为从节点（follower），所有的写操作都经过leader，然后leader把写操作再转发给follower。 读请求可以分散到所有节点去。

<b>适用范围：</b> 写少读多的情况。
<b>存在问题：</b>
* follower节点上的数据可能不是最新的，因为主节点复制到需要时间
* 主节点出问题的时候，需要额外时间从新选取新的主节点，这期间写操作无法进行

#### Multi-Leader

#### Leaderless


### 复制的优缺点
#### 一致性问题
##### Read-After-Write Consistency
当你在写操作之后马上读取同一个记录，如果处理读请求的节点不是处理写请求的节点，在异步复制的情况下， 可能存在读不到最新数据的情况。 解决方法之一：在用户做出修改操作后，让用户始终/在一定时间内从leader读。

##### Monotonic Reads Consistency
用户发多个读请求，如果分到多个不同的节点，有的节点数据比较旧，有的比较新，那么用户得到的信息会不一致。解决方法之一：类似于 sticky routing，根据用户信息做哈希，使得某个用户请求都从固定的节点读


[http://horicky.blogspot.com/2009/11/nosql-patterns.html](http://horicky.blogspot.com/2009/11/nosql-patterns.html)



避免hot partition

http://masutangu.com/2019/12/13/distributed-system-2/