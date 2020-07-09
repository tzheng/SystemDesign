# 分布式ID生成 - Global UUID

在分布式系统中， 我们会对数据或者计算资源进行拆分（具体可以参照《分区 Sharding,Partitioning》），但是在分区之后，我们还是需要保证ID在整个系统中都是唯一的，否则就会出现冲突。

保证ID全局唯一有很多办法。

使用平台自带的UUID API 生成：如果使用Java，直接利用util里面的UUID生成128位全局唯一标识符，原理大同小异，一般是通过时间，节点ID（比如mac地址），namespace，随机数等等组合来生成，具体可以参考RFC [https://tools.ietf.org/html/rfc4122](https://tools.ietf.org/html/rfc4122)。优点是简单直接，不需要中心化的服务器，缺点是太长占用比较多空间，ID本身无序，查询效率低（在SQL里面以varchar存储）。

另外一个办法是根据用户数据产生，只要用户数据是唯一，

