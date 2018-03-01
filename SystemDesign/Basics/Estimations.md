# 基本数据估算 - Estimations

### **Numbers Everyone Should Know**

在总结数据估算方法之前，先附上一些重要的数字，这是[Jeff Dean在他的演讲中](http://www.cs.cornell.edu/projects/ladis2009/talks/dean-keynote-ladis2009.pdf)首次提到的，也是目前网上被引用最多的数据之一。

| L1 cache reference | 0.5 ns |
| :--- | :--- |
| Branch mispredict | 5 ns |
| L2 cache reference | 7 ns  ~14x L1 cache |
| Mutex lock/unlock | 25 ns |
| Main memory reference | 100 ns ~20x L2 cache, 200x L1 cache |
| Compress 1K bytes with Zippy | 3,000 ns   =   3 us |
| Send 1K bytes over 1 Gbps network | 10,000   ns       10 us |
| Read 4K randomly from SSD\* | 150,000   ns      150 us          ~1GB/sec SSD |
| Read 1 MB sequentially from memory | 250,000   ns      250 us |
| Round trip within same datacenter | 500,000   ns      500 us |
| Read 1 MB sequentially from SSD\* | 1,000,000 ns = 1,000 us = 1 ms  ~1GB/sec SSD, 4X memory |
| Disk seek | 10,000,000 ns = 10,000 us =10ms ~20x datacenter roundtrip |
| Read 1 MB sequentially from disk | 20,000,000 ns = 20,000 us = 20 ms ~80x memory, 20X SSD |
| Send packet CA-&gt;Netherlands-&gt;CA | 150,000,000 ns = 150,000 us = 150 ms |

1 ns = 10^-9 seconds

1 us = 10^-6 seconds = 1,000 ns

1 ms = 10^-3 seconds = 1,000 us = 1,000,000 ns

1 ms = 10^-3 seconds = 1,000 us = 1,000,000 ns

### 常见估算

我认为系统设计中一般有两种估算，一种是从这个系统的角度来做估算，比如计算你系统的QPS，数据库的QPS，通过估算这些数据，你可以大致知道你需要多少台机器，选择怎样的数据库。还有一种估算是细节的估算，比如说系统要求某项服务延时小于100ms，这时候可能就需要用到上面的数据，计算数据传输，读取分别需要多少时间，通过这样的估算同样也可以帮助你做出正确的选择。

**估算的结果不需特别精确，只要数量级正确即可**，比如使用到 2^10 \(1024\)的时候，为了计算方便可以用1000来计算。

对于第一种估算方式，我们以QPS为例做一个简单的讲解，有的时候面试官不会直接说你算算QPS是多少，而是会从侧面来说假设我们有X个用户，你能告诉我需要多少台机器？

> 题目：设计一个类似twitter或者facebook的newsfeed/timeline ，假设100M 日活用户\(DAU\)，估算QPS，估算数据量



