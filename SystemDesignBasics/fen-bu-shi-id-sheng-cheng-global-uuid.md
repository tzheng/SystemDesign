# [分布式ID生成 - Global UUID](/SystemDesignBasics/fen-bu-shi-id-sheng-cheng-global-uuid.md)分布式ID生成

在分布式系统中，随着业务的增长， 我们会对数据或者计算资源进行拆分，但是在分区之后，我们还是需要保证ID在整个系统中都是唯一的，否则就会出现冲突。这里介绍一些常见的生成方案。

## UUID

使用平台自带的UUID API 生成：如果使用Java，直接利用util里面的UUID生成128位全局唯一标识符，原理大同小异，一般是通过时间，节点ID（比如mac地址），namespace，随机数等等组合来生成，具体可以参考 [UUID RFC](https://tools.ietf.org/html/rfc4122)。

**优点：**

* 简单直接，不需要中心化的服务器

**缺点：**

* ID本身128位，太长占用比较多空间
* ID以字符串存储，查询效率低，不利于索引查询，参考《[UUIDs are Popular, but Bad for Performance](https://www.percona.com/blog/2019/11/22/uuids-are-popular-but-bad-for-performance-lets-discuss/)》
* ID本身无序；ID本身没有任何含义，可读性差

适用于ID不作为数据库主键，对可读性没有要求的时候。

## 数据库自增ID - Auto-increment ID

大部分数据库都能产生自增ID，比如MySQL等等。如果需要支持多台机器并发，可以给每台机器设置步长（step size），比如步长为3，机器A起点是1，每次用1，4，7...,机器B起点2，使用2，5，8…，以此类推。

**优点：**

* 简单方便，不需要额外操作
* ID递增有序

**缺点**

* 数据库扩容非常困难（需要修改步长，起始值等等）
* 递增容易被猜中，导致泄漏信息（比如一天增加多少订单
* 虽然可以有多台服务器实现并发，生成ID仍然是一次读写操作，高并发的时候只能依靠增加机器实现，成本高

在对并发要求不高，数据量不大，可预见的未来不会需要扩容的时候可以使用，比如企业员工管理。

## Snowflake算法

Snowflake是最知名的分布式ID生成算法之一，由Twitter开源。算法有许多变种，比如时间大小不同，位数可以变化等等，但总体思想不变。其核心是采用64位存储ID，最高位为始终为0，然后接下来41位存时间戳\(timestamp\)，接着存10位的机器ID，最后12位自增ID。

![](/assets/snowflake_en_v3.png)\*图片来源：[https://shardingsphere.apache.org/document/legacy/3.x/document/en/features/sharding/other-features/key-generator/](https://shardingsphere.apache.org/document/legacy/3.x/document/en/features/sharding/other-features/key-generator/)

**优点：**

* 本地生成，不需要中心化服务器，高性能
* 能实现趋势递增，不像简单自增那样容易被猜测
* 64位，比UUID更小，在Java中可以用long存，可以作为数据库主键
* ID本身带有时间，具有可读性，某些服务可以用来计算SLA

**缺点：**

* 依赖于时间戳，如果服务器时间回拨，生成的ID可能重复

关于如何解决Snowflake算法依赖时间的缺点，具体可以参考[美团的解决方案](https://tech.meituan.com/2017/04/21/mt-leaf.html)。

# 总结

和其他系统设计问题一样，分布式ID生成是没有标准答案的，以上的解决方案也只是提供一些思路，完全可以有，比如把输入数据序列化（serialization）然后加上时间戳算MD5等等。对于以上的一些方案，也有一些变体，比如可以预先生成ID等等，关键还是在于了解每种方案的优缺点，做好分析，根据自己的要求找到最适合的解决方案。

---

**参考文章：**

* [https://tools.ietf.org/html/rfc4122](#)
* [https://www.percona.com/blog/2019/11/22/uuids-are-popular-but-bad-for-performance-lets-discuss/](https://www.percona.com/blog/2019/11/22/uuids-are-popular-but-bad-for-performance-lets-discuss/)
* [https://tech.meituan.com/2017/04/21/mt-leaf.html](https://tech.meituan.com/2017/04/21/mt-leaf.html)



