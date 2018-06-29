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
| Send 1K bytes over 1 Gbps network | 10,000  ns = 10 us |
| Read 4K randomly from SSD\* | 150,000 ns = 150 us     ~1GB/sec SSD |
| Read 1 MB sequentially from memory | 250,000 ns = 250 us |
| Round trip within same datacenter | 500,000 ns  = 500 us |
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
>
> 1. 100M DAU, 根据80-20法则，我们假设 20%的人会发帖，那就是20M 的帖子
> 2. 如果20M的帖子是平均在24小时之内发的，QPS = 20M / 24\*60\*60, 大约200 QPS
> 3. 但是我们都知道发帖时间分布是不均匀的，比如今晚有比赛，比赛那个期间发帖会高很多，峰值可能是5倍，1k peak QPS。（5倍的数字是随便说的，说10倍也行，但是不要太离谱说个200倍）
> 4. 所以写入的QPS大致为几百到一千
> 5. 100M 的用户假设平均每天刷一次脸书/推特，



所以

第二种是细节的估算，[Jeff Dean的演讲中](http://www.cs.cornell.edu/projects/ladis2009/talks/dean-keynote-ladis2009.pdf)也给出了例子。

> How long to generate image results page \(30 thumbnails\)?
>
> Design 1: Read serially, thumbnail 256K images on the fly
>
> 30 seeks \* 10 ms/seek + 30 \* 256K / 30 MB/s = 560 ms
>
> Design 2: Issue reads in parallel:
>
> 10 ms/seek + 256K read / 30 MB/s = 18 ms \(Ignores variance, so really more like 30-60 ms, probably\)



但是无论是哪一种估算，它们都是为了做设计决定而服务的，千万不要本末倒置。网络上一些教程常常说系统设计第一步就是收集需求估算QPS，导致某些面试者在面试的时候收集完需求就就各种计算，写了一白板的各种数字，其实有时候面试官根本不在意这些数字，他们只是想让你先大致画出一个架构，支持两三个人用就好，这样给面试官留下的第一印象就不是很好了。

所以我个人的建议是面试者在面试的时候还是看碟下菜，先问清楚设计的系统要多大规模，如果得到的回答是没事这个先不管，你就别做计算。如果得到的回答是用户很多，我的建议是不要一下把所有东西都算了，**刚开始可以做一个简单的计算，判断系统的某些特性，比如是读多还是写多（read heavy/write heavy）**，具体的计算可以再画架构的时候再完成，然后根据计算结果作出决定（譬如每个请求request都会导致5次数据库读写，service QPS是10k，数据库是50k，考虑到我们存的是非结构化数据，又有50k的QPS，所以我们使用NoSQL比较好），这样体现出你在每一步设计都考虑到了scalebility，通过计算来证明你的设计决定是正确的。



