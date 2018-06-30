# 需求收集和数据估算 - Collect Requirements & Estimations

### 搜集需求

大家都知道，系统设计的第一步是搜集需求，所以本文也不对收集需求做太多的介绍。系统设计很多时候题目都是模糊的，即使同一个题目（比如设计timeline时间线），不同的面试官考察的侧重点也可能不同，所以一定要花时间和面试官讨论设计的系统具体要支持哪些功能。

搜集需求的过程一般是从最常见的，使用最频繁的功能开始说，然后发散到其他辅助型的功能，也不能过于详细，比如说面试官需要你支持登录功能，一般情况下就不需要再发散下去问要不要记住登录状态功能。

在搜集完需求之后，我们就需要通过适当的估算来给系统定性，并且通过数据来支持设计决定，为后面的设计指明方向，下面就介绍一些常见的数据估算方法和常用的数字。

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

我认为系统设计中一般有两种估算，一种是从这个系统的角度来做估算，比如计算你系统的QPS，数据库的QPS，通过估算这些数据，你可以大致知道你需要多少台机器，选择怎样的数据库等等。还有一种估算是细节的估算，比如说系统要求某项服务延时小于100ms，这时候可能就需要用到上面的数据，计算数据传输，读取分别需要多少时间，通过这样的估算同样也可以帮助你做出正确的选择。

**估算的结果不需特别精确，只要数量级正确即可**，比如使用到 2^10 \(1024\)的时候，为了计算方便可以用1000来计算。比如算QPS的时候要算24\*60\*60 = 86400，你用80k 或者 100k都是可以的。

对于第一种估算方式，我们以QPS为例做一个简单的讲解，有的时候面试官不会直接说你算算QPS是多少，而是会从侧面来说假设我们有X个用户，你能告诉我需要多少台机器？

> 题目：设计一个类似twitter或者facebook的newsfeed/timeline ，假设100M 日活用户\(DAU\)，估算QPS
>
> 1. 100M DAU, 根据80-20法则，我们假设 20%的人会发帖，那就是20M 的帖子
> 2. 如果20M的帖子是平均在24小时之内发的，QPS = 20M / 24\*60\*60, 大约200 QPS
> 3. 但是我们都知道发帖时间分布是不均匀的，比如今晚有比赛，比赛那个期间发帖会高很多，峰值可能是5倍，1k peak QPS。（5倍的数字是随便说的，说10倍也行，但是不要太离谱说个1000倍），所以写入的QPS大致为几百到一千
> 4. 100M 的用户假设平均每天刷一次脸书/推特，那就是100M的读请求（其实很多时候每个读请求背后会有更多的读请求来聚合信息，这里我们就不做具体计算），QPS = 100M / 24\*60\*60， 大约1k QPS，和写同理，读也有高峰时段，算5k QPS
> 5. 综上所述，我们发现系统读的请求比写的请求多。

通过这样的计算，可以很好地了解系统的特性，比如上面例子给出的时间线系统，就是一个读多写少的的系统。**在系统设计面试中，搜集完需求然后判断系统特性是最重要的一步**，之后才能根据系统的特性做更深入， 如果这一步判断错误，那就是刚出发就走错了方向，后面讲的再好也于事无补了。在你出现方向性的错误的时候，一般面试官会暗示你，让你重新思考某个环节是不是真的如你所想，这种时候就要顺着面试官的思路再思考一遍，然后做出判断（以防面试官故意误导）。

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

所以我个人的建议是面试者在面试的时候还是看碟下菜，先问清楚设计的系统要多大规模，如果得到的回答是没事这个先不管，你就别做计算。如果得到的回答是用户很多，我的建议是不要一下把所有东西都算了，**刚开始可以做一个简单的计算，判断系统的某些特性，比如是读多还是写多（read heavy/write heavy），以便确定设计的大方向。**剩下的具体的计算可以再画架构的时候再完成，然后根据计算结果作出决定（譬如每个请求request都会导致5次数据库读写，service QPS是10k，数据库是50k，考虑到我们存的是非结构化数据，又有50k的QPS，所以我们使用NoSQL比较好），这样体现出你在每一步设计都考虑到了scalebility，通过计算来证明你的设计决定是正确的。

### 其他数字

还有一些其他的数字，属于江湖经验，很多数字受具体情况和系统性能影响变化比较大，所以在这里给出来做一些参考，了解一下数量级和快慢，请不要把他们当做标准数字。

* Memcache读取时间，Memcache QPS
* 用SQL去取数据一般要几百毫秒，完全取决于表怎么设计，索引，SQL Query写的好不好等等，做的不好几秒也有可能，优化得很好几十毫秒也能达到，一般来说取200ms是合理的。SQL 单台机器QPS
* 单机端口Http Port数量65535



