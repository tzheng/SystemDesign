# 分布式ID生成 - Global UUID

在分布式系统中， 我们会对数据或者计算资源进行拆分（具体可以参照《分区 Sharding,Partitioning》），但是在分区之后，我们还是需要保证ID在整个系统中都是唯一的，否则就会出现冲突。

保证ID全局唯一有很多办法。

## UUID

使用平台自带的UUID API 生成：如果使用Java，直接利用util里面的UUID生成128位全局唯一标识符，原理大同小异，一般是通过时间，节点ID（比如mac地址），namespace，随机数等等组合来生成，具体可以参考 [UUID RFC](https://tools.ietf.org/html/rfc4122)。

**优点：**

* 简单直接，理论上没有性能瓶颈；
* 不需要中心化的服务器。

**缺点：**

* ID本身128位，太长占用比较多空间；
* ID以字符串存储，查询效率低，不利于索引查询，参考《[UUIDs are Popular, but Bad for Performance](https://www.percona.com/blog/2019/11/22/uuids-are-popular-but-bad-for-performance-lets-discuss/)》；
* ID本身无序；ID本身没有任何含义，可读性差。

## 数据库自增ID - Auto-increment ID

单台机器，多台机器

## 预先批量生成ID

## Snowflake算法



---

**参考文章：**

* [https://tools.ietf.org/html/rfc4122](#)
* [https://www.percona.com/blog/2019/11/22/uuids-are-popular-but-bad-for-performance-lets-discuss/](https://www.percona.com/blog/2019/11/22/uuids-are-popular-but-bad-for-performance-lets-discuss/)



**选读：Redis生成**

一般美国主流IT公司系统设计面试不会针对性地考察对某项技术的了解程度，所以把Redis生成放在选读，仅供了解。



