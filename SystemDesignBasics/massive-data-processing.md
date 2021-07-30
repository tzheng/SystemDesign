# 海量数据处理

海量数据处理也是系统设计面试中比较常见的类型，一些经典的Top K面试题比如五分钟内分享最多的文章，一天内听过最多的音乐，Typeahead等等，它们的核心就是怎样处理海量数据，找出符合要求的数据。具体题目的例子和细节可以在《Top K 问题 - Top K Problem》一文中找到。本文主要介绍一些常用的处理方法和概念。

## 分治和Map Reduce

#### 外排序和归并排序

海量数据意味着数据量过大无法全部装入一台机器的内存，或者超过一台机器的处理能力。这时候就需要分而治之，其原理就是将一个问题分解成多个相同的子问题，不断重复直到最后可以简单处理子问题位置，最后在把子问题合成较大的问题，这个合并的过程就叫做归并。一个很经典的分治算法就是[归并排序](https://zh.wikipedia.org/wiki/归并排序)。而外排序就是归并排序的延伸，我们直接摘抄维基百科对于外排序的定义：

> 外排序（External sorting）是指能够处理极大量数据的排序算法。通常来说，外排序处理的数据不能一次装入内存，只能放在读写较慢的外存储器（通常是硬盘）上。外排序通常采用的是一种“排序-归并”的策略。在排序阶段，先读入能放在内存中的数据量，将其排序输出到一个临时文件，依此进行，将待排序数据组织为多个有序的临时文件。而后在归并阶段将这些临时文件组合为一个大的有序文件，也即排序结果。

#### MapReduce

MapReduce是一种分布式运算的模式，最常用的实现该模式的框架就是Hadoop。简单地说它就是将大量数据通过Map分解执行，然后通过Reduce归并，其实原理就是归并排序。最经典的MapReduce的例子就是统计单词频率\(WordCount)，既统计文章中每个单词出现的次数，[官方文档也是以此作为例子](https://hadoop.apache.org/docs/current/hadoop-mapreduce-client/hadoop-mapreduce-client-core/MapReduceTutorial.html#Example:_WordCount_v1.0)。我们也以WordCount为例子介绍，MapReduce的主要分为以下几个步骤：

Split

根据需求把输入数据拆分，比如统计一本书的单词单词，我们可以把书分成三章

```
chapter1: one two three one
chapter2: two three four two
chapter3: one three four three
```

**Map**

然后我们把分好的三个部分交给三台机器处理，机器根据需求做（这里的需求是统计单词频率）把数据拆分映射。

```
Machine1: {one:1, two:1, three:1, one:1}
Machine2: {two:1, three:1, four:1, two:1}
Machine3: {one:1, three:1, four:1, three:1}
```

Combine

```
Machine1: {one:2, two:1, three:1}
Machine2: {two:2, three:1, four:1}
Machine3: {three:2, one:1, four:1}
```

**Reduce**

当每台机器都完成了Map映射之后，就可以进入Reduce阶段了，我们上面的数据进行重新划分(shuffling)，比如我们分配四台机器分别处理one，two，three，four，我们就可以把所有的one分配给机器1，以此类推，然后每台机器就能统计出每个单词在所有地方出现的频率，最后把结果汇总即可。

```
Machine1: {one:2, one:1} -> {one:3}
Machine2: {two:2} -> {two:2}
Machine3: {three:1, three:1, three:2} -> {three: 4}
Machine4: {four1, four:1} -> {four:2}
```

以上的例子只是对MapReduce过程的一个非常简化的概括，实际过程更加复杂，在系统设计中我们更需要了解的是它的思路。除了统计词频以外，MapReduce还有许多应用，更多例子可以在MapReduce论文中文版和《[基于MapReduce的应用案例](https://zhuanlan.zhihu.com/p/31972709)》中找到。这里列举一些论文中的例子

> * 分布式的Grep：Map函数输出匹配某个模式的一行，Reduce函数是一个恒等函数，即把中间数据复制到输出。
> * 计算URL访问频率：Map函数处理日志中web页面请求的记录，然后输出(URL,1)。Reduce函数把相同URL的value值都累加 起来，产生(URL,记录总数)结果。
> * 倒转网络链接图：Map函数在源页面（source）中搜索所有的链接目标（target）并输出为(target,source)。 Reduce函数把给定链接目标（target）的链接组合成一个列表，输出(target,list(source))。
> * 每个主机的检索词向量：检索词向量用一个(词,频率)列表来概述出现在文档或文档集中的最重要的一些词。Map函数为每一个输入文档输出(主机 名,检索词向量)，其中主机名来自文档的URL。Reduce函数接收给定主机的所有文档的检索词向量，并把这些检索词向量加在一起，丢弃掉低频的检索 词，输出一个最终的(主机名,检索词向量)。
> * 倒排索引：Map函数分析每个文档输出一个(词,文档号)的列表，Reduce函数的输入是一个给定词的所有（词，文档号），排序所所有的文档 号，输出(词,list（文档号）)。所有的输出集合形成一个简单的倒排索引，它以一种简单的算法跟踪词在文档中的位
> * 分布式排序：Map函数从每个记录提取key，输出(key,record)。Reduce函数不改变任何的值。这个运算依赖分区机制(在 4.1描述)和排序属性(在4.2描述)。

[TBD: add image]

\*_图片来源：_[_https://stackoverflow.com/questions/20317152/how-shuffling-is-done-in-mapreduce_](https://stackoverflow.com/questions/20317152/how-shuffling-is-done-in-mapreduce)

## Bloom Filter

如果我们要想判断一个元素是不是在一个集合里，一般是将集合中所有元素保存在某个数据结构里，然后通过比较确定，根据元素的特性，我们可以用树，哈希表等结构来存储。但是不同的简单的数据结构都有一定的缺陷，比如通过树查找元素的时候时间复杂度为O(logN)，通过哈希表为O(1)，空间复杂度为O(N)。 当数据量巨大的时候，时间效率和空间效率就显得十分重要。BloomFilter就是一个在时间和空间上都十分有效的算法。
**原理** 
Bloom Filter 的核心是一个很大的位数组（bitarray, 假设长度为m）和多个哈希函数（假设为k个）。位数组每一位初始值都为0。
当增加新元素的时候，将新元素通过哈希函数算出一个index（ 0 <= index < m)，然后奖维数组对应index的那一位改成1，使用k个哈希函数重复此过程。 
当查询元素的时候，将元素通过k个哈希函数算出k个index，检查位数组这些位置是否都为1，如果不是，则说明不在集合中，如果是，则说明可能在集合中。

```
比如当m = 3, k =2 时，我们有bitarray = [0, 0, 0]

此时插入 "hello", 通过两个哈希函数算出 hash1("hello") = 0, hash2("hello") = 2， 将 bitarray 更新成 [1, 0, 1]
当查询 "hello" 时，通过hash1("hello") = 0, hash2("hello") = 2， 我们发现 bitarray[0], bitarray[2] 都为1，则说明"hello" 可能在集合中。
当查询"world" 时， 通过hash1("world") = 1, hash2("hello") = 2, 我们发现 bitarray[1]为0，说明"world"不在集合中。
```

**特点：** 判断一个元素是否属于某个集合的时候，如果返回不属于，则表示一定不属于；如果返回属于，有可能会把不属于这个集合的元素误认为属于(false positive)。
**优点：** 判断数据是否存在时，时间和空间上都很有效率 O(1)
**缺点：** 缺点：有一定的误识别率（如果多个词都hash到同一个位置），删除困难。


更多细节可以阅读《[海量数据处理之Bloom Filter详解](http://buttercola.blogspot.com/2015/11/big-data-bloom-filter.html)》

## 多级划分
在统计海量数据的时候，经常需要根据时间进行统计（比如过去x小时热搜），而且经常出现对颗粒度（granularity）有不同的要求的情况，（比如需要过去5分钟，1小时，1天的热搜）。在这种情况下，我们可以使用多级划分来实现，把数据按照不同的单位来划分存储，然后根据需求返回结果。

例如，我们需要最近1分钟的数据，我们就按照1秒钟为单位划分存储数据，建立一个含有60个元素的数组（seconds[60]），分别对应过去的60秒。如果统计最近1天的数据，那么就可以按照1小时为单位划分建立个有24个元素的数组（hours[24]）分别对应过去的24小时。当然可以按照更小的单位来划分，比如每30分钟为一组，需要根据实际情况改变。

当某个数据被加入的时候，假设数据时间戳是“X时Y分Z秒”，我们把时间戳对应的 seconds[z]，hours[x] 都+1。 当一秒钟过去以后，我们把前一秒统计的数据seconds[z-1]加到总数中，同时拿到60秒前对应的数据seconds[z]，将它从总数中（total_1min)减去，并将seonds[z]重置为0。同理，当1天过去以后，我们把前一小时的数据hours[x-1]加到总数(total_1day)，同时减去24小时前对应的hours[x]并重置为hours[x]为0。

这样我们就可以实现批量增减，获取数据的时候可以直接从total_1min, total_1day中获取，避免了大量的+1， -1计算。当然，这个方案的缺点是一定程度上牺牲了时效性。


## Trie树
Trie树最常见的应用就是Typeahead。 
**原理**
本质是一个多叉树，根节点不包含字符，其余每个节点代表一个字符，从根节点到某一个节点的路径连接起来就是一个完整的字符串，对应一个输入的值。比如我们有["app"， "ap", "aq", "bd", "c"，"app"] 六个词，我们就构成如下左边的Trie树。 当统计词频的时候，我们把对应的词频放在节点中，如下图右边的Trie树所示。如果需要typeahad，可以把typeahead需要的结果存在节点中，比如当用户输入app的时候，我们想提示[appl, apps, ...]，那我们就在 a->p->p 的路径上，在最后一个p 存 [appl, apps, ...]。 
```
          (root)                                 (root)
         /   |   \                            /     |    \
        /    |    \                          /      |     \ 
       a     b     c                        a       b      c(1) 
      / \    |                             / \      |
     p   q   d                           p(1) q(1)  d(1)
    /                                   /
   p                                   p(2)
```

Trie树的实现可以参照 [Leetcode 208](https://leetcode.com/problems/implement-trie-prefix-tree/description/)

**Scale Up**
当一个Trie树过大的时候，我们同样也需要把它拆分（sharding）然后存在不同的机器上。拆分的方式有很多种。
方案一：根据26个字母划分，a分到机器1， b分到机器2……以此类推。这个方案的缺点在于很多时候数据都不是平均分布的，比如a开头的词汇可能远远多于x开头的词，这样划分会导致机器之间负载不均衡。
方案二（**推荐**）：把从根到叶子节点的路径作为一致性哈希(consistent hashing)的参数，根据哈希结果划分到机器上。比如 hash("app") =1, 把 a->p->p这条路径分到机器1，hash("appl") = 2, 把 a->p->p->l 这条路径分到机器2，这样每台机器可能都有完整结构的trie 树，只是对应的叶子节点不同，这样可以让数据均衡分布在不同机器上。

## Lossy Counting & Sticky Sampling
上面谈到了我们可以用Map-Reduce来处理数据，根据通过多极划分、Trie等数据结构来存储数据。但是在实际操作中，很有可能会出现长尾效应，即会存在大量的数据出现频率只有一两次，然后剩下的数据出现频率远远多于其他数据，例如 apple, app被搜索的次数可能为一百万,astronaut被搜的次数为一千，然后剩余大量a开头的词，或者完全没有意义的词asdxf,aegvs 等等出现几十次到一两次不等，存储这些非热门词汇需要大量的空间。当我们只需要排名前十的热搜的时候，存非热门词汇显然是浪费空间的一件事情。这时候我们就可以通过Lossy Counting 和Sticky Sampling来做优化。

**Lossy Counting**
1. 首先建立一个Map作为计数器(counter<string, Integer>)，key是关键词，value是频率。
2. 把输入数据流分为多个大小固定的窗口(window size = m)。
3. 在读取一个窗口的数据的时候，把计数器里面对应词的频率加一。
4. 读完一个窗口的数据以后，把计数器里所有的词(key)的频率(value)都减一，如果减一后为0，则将数据剔除出计数器。
5. 重复3-4 一直到读完所有窗口的数据。

显然，通过每次读完一个窗口后的减一操作，我们可以把出现频率比较低的数据剔除出，这样就可以节省空间。同时我们也看到，窗口的大小对误差有影响，如果窗口定得很小，那么被剔除的词就会比较多，一般窗口的大小由错误率来决定，如果错误率为x，那么窗口大小为 1/x. 

**Sticky Sampling**
Sticky Sampling 的原理是：
1. 首先建立计数器
2. 当从数据流读取一个数据的时候
    a. 如果数据已经存在计数器中，我们把该数据的频率加一
    b. 如果数据没有出现在计数器中，我们给其一个 1/r的概率加入计数器，这就采样（sampling）。
3. 初始的时候我们把r设为1，采样率很大，随着数据不断增加，我们同时增加r，这样采样率 1/r 就会越来越小。我们同样可以设置一个窗口大小t，对于前t个数据，r为1，对于后2t个数据，r=2，以此类推。
4. 每当r 发生变化的时候，我们扫描计数器中的所有数据，对于每一条数据，我们投一个硬币（正反概率相同）来决定后面操作
    a. 如果正面，则停止投硬币。
    b. 如果反面，将频率(value)减一，如果减一后为0，则将数据剔除计数器，否则继续投直到正面出现。

Sticky Sampling的准确度显然也和窗口大小有关。关于窗口大小如何计算，可以参考下面的文章。在系统设计中，重要的是思路，一般不会具体到计算环节。


更多细节可以参考文章 [Frequency Counting Algorithms over Data Streams](https://micvog.com/2015/07/18/frequency-counting-algorithms-over-data-streams/)


