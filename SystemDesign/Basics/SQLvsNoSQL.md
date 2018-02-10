# 数据存储 - SQL, NoSQL

## 概念

数据存储是系统设计中最重要的环节之一，通常是考察面试者对关系型数据库和非关系型数据库的理解，在极少数情况下也可能会使用文件系统，或者分布式文件系统。这里我们主要还是着重说一下 SQL vs NoSQL

## SQL基本概念

这里说的SQL代表的是关系型数据库\(RDBMS\)，并不是代表一个单一的产品，比如MySQL。 在系统设计的时候，只要能做出适当的权衡，在关系型数据库和非关系型数据库中选出适合的结果即可，并不需要具体到某一个产品，除非特殊职位，一般不会问你MySQL和Oracle的区别之类的问题。网上有非常多的资料介绍什么是SQL，相信学习过计算机课程的人在作业或者项目中多多少少都用过SQL，这里就不对SQL的定义和种类做更多叙述，还是主要讨论和系统设计相关的内容。

SQL最重要的特性之一就是SQL能支持事务\(transaction\)，transaction有四个重要的特性，这里直接引用[维基百科](https://zh.wikipedia.org/wiki/ACID)：**ACID**，是指数据库管理系统（DBMS）在写入或更新资料的过程中，为保证事务（transaction）是正确可靠的，所必须具备的四个特性：原子性（atomicity，或称不可分割性）、一致性（consistency）、隔离性（isolation，又称独立性）、持久性（durability）。

原子性：一个事务（transaction）中的所有操作，要么全部完成，要么全部不完成，不会结束在中间某个环节。事务在执行过程中发生错误，会被回滚（Rollback）到事务开始前的状态，就像这个事务从来没有执行过一样。

一致性：在事务开始之前和事务结束以后，数据库的完整性没有被破坏。这表示写入的资料必须完全符合所有的预设规则，这包含资料的精确度、串联性以及后续数据库可以自发性地完成预定的工作。

隔离性：数据库允许多个并发事务同时对其数据进行读写和修改的能力，隔离性可以防止多个事务并发执行时由于交叉执行而导致数据的不一致。事务隔离分为不同级别，包括读未提交（Read uncommitted）、读提交（read committed）、可重复读（repeatable read）和串行化（Serializable）。

持久性：事务处理结束后，对数据的修改就是永久的，即便系统故障也不会丢失。

SQL事务的ACID特性十分重要，往往用这个特性来判断SQL是否合适，但是并不是说SQL就一定要支持transaction，在某些情况下也不一定要做到ACID，最终还是根据系统设计的需求来决定。

#### SQL如何Scale up

当我们只有一台机器（SQL服务器）的时候，在负载增加的情况下，这台机器可能成为整个系统的瓶颈，为了保证系统的性能，要么我们就制造一台更强的机器，但是技术发展日新月异，买高性能的机器很贵，很快就被淘汰了，这个方法被证明不如增加多台便宜的机器好用\(Replication\)。增加多台机器的时候，就会带来一些问题，比如如何保证各台机器上的数据的一致性？各台机器之间的关系是什么，如何协调？解决这些问题，SQL的方法比较成熟，常见的有Master-Slave 和 Master-Master。

##### **Master-Slave Replication 模式**

Master-Slave模式就是选择一台机器作为master，剩下的机器作为slave，当需要写入数据的时候，只能写到Master机器上，这样就能支持事务\(transaction\)，保证了数据的一致性。当需要读取的时候，由Load Balancer分配，到任意一台slave机器上读取，这样就均衡了负载，系统能处理的QPS就更高了。

既然写只能写到Master机器上，那么Master机器在获得新数据的时候，就要同步数据到Slave机器上，同步的过程需要时间，所以有可能某个时刻master和slave上的数据不同，前面的CAP理论就讲到，Consistency和Availability不可兼得，既然我们要系统在负载大的时候可用性高，就要牺牲读的一致性，有的时候可能读到旧数据。但是一台master保证了写入的时候不会有冲突。

Master并不是固定的某台机器，如果master机器坏了，可以指派\(promote\)某一台slave机器作为新的master。这里的master也不一定是个物理机器，可以是个逻辑上的Master机器，比如一台机器即是某个系统的master，也可能是另外一个系统的slave。

Master-Slave的适用场景

* Master-Slave模式只有一台机器处理写入请求，多台机器处理读，所以这个模式**特别适合读多写少**的情况**。**
* 即使Master故障，系统仍然可以支持读操作\(read\)

Master-Slave的缺点

* 一台master仍然会是single point of failure
* 如果slave机器较多，replicate的成本较高，时间比较长
* 如果写操作较多，master频繁复制到slave上，占用了slave的机器资源，影响性能（所以这个模式适合**读多写少**）
* master故障的时候，master上新写入的数据来不及复制到任何slave上，新数据暂时丢失

##### Master-Master Replication 模式

考虑到Master-Slave只有一台机器写，有可能不够，所以另外一个模式是让两（多）台机器做master。两台机器都处理读和写的请求。

Master-Master的适用场景

* 写和读的请求差不多的时候，或者写多读少的情况
* 需要更高的可用性的时候，一台master故障，另外一台能正常工作

Master-Master的缺点

* 如果要保证两台master机器数据一致\(consistency\)，两台机器在写的时候要同步\(synchronize\)，增加延时write latency
* 如果要保证系统可用性，减小write latency, 就要牺牲一致性，这样就不能支持ACID事务了
* 同步的时候可能需要解决数据冲突（Conflict），交给用户？last write wins？...
* 可能需要自己做load balance，分配好读写任务保证负载均衡

如果有多台master，我们要考虑数据的强一致性（strong consistency）的问题，这里可以参考[** Paxos algorithm**](https://www.quora.com/In-distributed-systems-what-is-a-simple-explanation-of-the-Paxos-algorithm) 这是一个必须掌握的概念，我以后会增加这一部分的内容。

**最后，不管是Master-Slave还是Master-Master，这都不是数据库系统特有的scale up方式，其他系统也可以采用。其他的scale up方法有replication和sharding，常见的方法和优缺点会在**[**《分区 - Sharding, Partitoning》**](/SystemDesign/Basics/Sharding.md)**里面说到。**

## NoSQL基本概念

NoSQL原本指的是一切不是SQL的数据库，2009年，Last.fm的Johan Oskarsson发起了一次关于分布式开源数据库的讨论，来自Rackspace的Eric Evans再次提出了NoSQL的概念，这时的NoSQL主要指**非关系型、分布式、不提供ACID的数据库设计模式**\([source: wikipedia](https://en.wikipedia.org/wiki/NoSQL)\)。因为其具有水平可扩展性\(horizontal scale\)，NoSQL天生就自带scale up能力。

##### NoSQL种类

面试中比较常见的一般是key-value store或者Wide-column Store，其他的比如Graph Database 或者 Document Store应该是很少见的，一般不会涉及。

Key-value Store可以直接想象成Hash Table，适合数据结构比较简单的情况，因为读写复杂度都是O\(1\)，它的效率非常高，由于不需要建立类似SQL的index，也比较适合增删改查十分频繁的情况。它的缺点也是因为结构简单，有时候无法保证value的数据都是valid，比如value中某个attribute name拼写错误；它也无法支持跨表查询\(JOIN table\)。

Wide-column Store也是非常常用的，典型的代表就是[BigTable](https://static.googleusercontent.com/media/research.google.com/en//archive/bigtable-osdi06.pdf)，[HBase](http://hbase.apache.org/book.html#arch.overview)和[Cassandra](https://docs.datastax.com/en/archived/cassandra/2.0/cassandra/architecture/architectureIntro_c.html)，这三个并不是都要掌握的很细，选一个作为例子了解什么是Row key，也就是partition key，什么是Column Family\(group of column\) 和 Column\(name/value pair\)。了解NoSQL的最佳方式是快速翻阅这本书 《NoSQL Distilled: A Brief Guide to the Emerging World of Polyglot Persistence》，有中文版。



##### NoSQL如何Scale Up

NoSQL本身就是分布式的，所以scale up的方式也是分布式计算的经典方式，这部分内容会在 [《分区 - Sharding, Partitioning》](/SystemDesign/Basics/Sharding.md)里面具体解释。



## 选择合适的数据库

在大部分情况下，SQL或者NoSQL都是适用的， 甚至你可以把SQL当做NoSQL来用（比如建立一个table，两个column分别是key和value即可），所以关键还是要向面试官展示你知道他们的特性，知道如何做出正确的选择。

需要特别注意的是，**面试过程中千万不要使用“业界通用做法”作为选择依据**，比如你知道有些情况业界一般是用NoSQL来做，所以就直接告诉面试官这个情况我们用NoSQL，因为大家都是这样做，这样是不对的。甚至有一些候选人还据理力争，说我就是做这个的，我们都是这么做的，跟面试官冲突，面试完了上网发帖说某某公司面试官水平不行，业界通用做法都不知道。其实面试官只是在考察你是否能证明\(justify\)你的选择是正确的。

正确的做法应该是跟面试官沟通需求，然后说考虑到我们的需求，我们需要某某特性，我觉得这个情况下SQL/NoSQL更加合适。

### 适用的场景

**数据非常规范，相互联系紧密，有结构化的数据一般使用SQL。**因为通常情况下，如果数据是结构化的，业务层的需求可能会带有复杂的query，SQL可以建立多重索引，可以提高查询效率，NoSQL在支持secondary index方面不如SQL。

**数据一致性\(consistency\)比较重要。**SQL事务的ACID特性可保证事物正确可靠，如果系统设计需求中有提到需要支持transaction保证ACID特性，尤其是涉及钱的时候（比如银行转账系统），很有可能需要使用SQL。

**需要成熟的解决方案。 **SQL就是Structured Query Language，提供了成熟的结构化查询语言。除了查询语言之外，由于历史较长，SQL的搭建流程，配置，库\(Library\)等各个方面都比较成熟，用户社区也活跃，出现问题容易找到解决方案。同时scale up的时候也有比较清晰的模式\(pattern\)

在选择SQL和NoSQL区别不大的时候，可以考虑使用SQL，节省时间和人力成本。

### NoSQL使用的场景

**非结构化数据，或者数据模型不是非常清晰的时候可以使用NoSQL。**NoSQL的schema比较灵活自由，可以动态更新，值\(value\)里面不需要指定数据格式的，可以存自定义的数据。如果SQL数据量大，新增一个一个column可能会带来重建索引、数据迁移之类的额外开销。

**系统的可用性\(availability\)比较重要的时候。**比如在使用SQL的时候，为了保证consistency，一般会选一个master，那么在master出问题的时候就会有单点故障\(single point of failure\)，所以在对系统可用行要求比较高，数据一致性不那么重要的时候，比如社交网络，一般可以使用NoSQL

**数据量较大大，操作频繁，QPS较高的时候。**总的来说NoSQL天生就适合scale up，在面对大量负载的时候，NoSQL的performance和availability都比SQL好。此外关系型数据库的JOIN复杂度不低 -- 《[how evil is sql join](https://www.quora.com/How-evil-is-SQL-Join)》。

#### 参考资料

《NoSQL Distilled: A Brief Guide to the Emerging World of Polyglot Persistence》- by Pramod J. Sadalage,‎ Martin Fowler

[https://www.w3resource.com/mongodb/nosql.php](https://www.w3resource.com/mongodb/nosql.php)

[https://github.com/donnemartin/system-design-primer\#database](https://github.com/donnemartin/system-design-primer#database)

