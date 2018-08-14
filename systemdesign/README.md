# System Design

系统设计中，最重要的就是 Trade-off，中文应该叫权衡，系统设计之所以难就是难在这里，因为你在设计过程中做的每一个决定都需要给出理由，比如为什么用SQL为什么用NoSQL，这时候就需要考察你对不同数据库的理解，而且在不断深入的过程中，面试官会随时让你挑一个地方让你做具体设计，如果碰巧挑到你不是很熟悉的地方或者没有考虑过的问题，那么面试就危险了。所谓我在这里介绍概念和题目讲解的时候，尽量多覆盖一些知识点，尤其是讲解题目，我会尽量列出多种方法，然后说说不同方法适用于什么情况，如何做出权衡。**个人水平有限，如果有错误的地方请大家指出改正。**

我认为几个需要强调的地方：

* 系统设计没有标准答案 
* 系统设计重点在于权衡
* 不要只说名词不解释
* 面试过程中需要随机应变
* 面试官有的时候可能不要你回答业界通用的做法

在跟一些面试官交流过程中，大部分面试官都提到了系统设计的考点：

* do you gather requirement
* do you define the right limits of the system\(what the system can/cannot do\), do you realize trade-offs
* do you think big enough to have the right level of abstraction and keep it simple
* do you have basic knowledge, such as avoid single point of failure, CAP theorem, scaling, fault tolerant, consistent hasing...
* can you think clearly, do you have a good working method, do you loop back on requirement to test if your design work well, do you call out how the system can and can't envolve
* do you have domain expertise

在后面的例子中我会通过实例来展示如何权衡和应变，在我们看具体题目之前，可以先来看看一些常见的概念或者trade-off

1. CAP
2. SQL vs NoSQL
3. Sharding/Partitioning and Replication
4. Cache
5. Consistent Hashing
6. Long-polling vs Socket
7. ...

然后我们看一些常见的数字，比较经典的总结在这里

* [Latency numbers every programmer should know - 1](https://gist.github.com/jboner/2841832)
* [Latency numbers every programmer should know - 2](https://gist.github.com/hellerbarde/2843375)

我认为常见的系统设计主要可以概括为这几类问题

1. [Tiny URL - 入门级别问题](tinyurl.md)
2. Newsfeed - 信息流
3. 分布式存储 
4. Top K 类型
5. Calendar 类型
6. Real-time chat/comments - 实时聊天/评论系统
7. 网络爬虫
8. Type-ahead
9. 设计搜索引擎
10. 地理信息 - POI
11. 购票系统
12. ...

