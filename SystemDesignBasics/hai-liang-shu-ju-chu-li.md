# 海量数据处理

海量数据处理也是系统设计面试中比较常见的类型，一些经典的Top K面试题比如五分钟内分享最多的文章，一天内听过最多的音乐，typeahead等等，它们的核心就是怎样处理海量数据，找出符合要求的数据。具体题目的例子和细节可以在《Top K 问题 - Top K Problem》一文中找到。本文主要介绍一些常用的处理方法和概念。

## 分治和Map Reduce

#### 外排序和归并排序

海量数据意味着数据量过大无法全部装入一台机器的内存，或者超过一台机器的处理能力。这时候就需要分而治之，其原理就是将一个问题分解成多个相同的子问题，不断重复直到最后可以简单处理子问题位置，最后在把子问题合成较大的问题，这个合并的过程就叫做归并。一个很经典的分治算法就是[归并排序](https://zh.wikipedia.org/wiki/归并排序)。而外排序就是归并排序的延伸，我们直接摘抄维基百科对于外排序的定义：

> 外排序（External sorting）是指能够处理极大量数据的排序算法。通常来说，外排序处理的数据不能一次装入内存，只能放在读写较慢的外存储器（通常是硬盘）上。外排序通常采用的是一种“排序-归并”的策略。在排序阶段，先读入能放在内存中的数据量，将其排序输出到一个临时文件，依此进行，将待排序数据组织为多个有序的临时文件。而后在归并阶段将这些临时文件组合为一个大的有序文件，也即排序结果。

#### MapReduce

MapReduce是一种分布式运算的模式，最常用的实现该模式的框架就是Hadoop。简单地说它就是将大量数据通过Map分解执行，然后通过Reduce归并，其实原理就是归并排序。最经典的MapReduce的例子就是统计单词频率\(WordCount\)，既统计文章中每个单词出现的次数，[官方文档也是以此作为例子](https://hadoop.apache.org/docs/current/hadoop-mapreduce-client/hadoop-mapreduce-client-core/MapReduceTutorial.html#Example:_WordCount_v1.0)。我们也以WordCount为例子介绍，MapReduce的主要分为以下几个步骤：

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

当每台机器都完成了Map映射之后，就可以进入Reduce阶段了，我们上面的数据进行重新划分\(shuffling\)，比如我们分配四台机器分别处理one，two，three，four，我们就可以把所有的one分配给机器1，以此类推，然后每台机器就能统计出每个单词在所有地方出现的频率，最后把结果汇总即可。

```
Machine1: {one:2, one:1} -> {one:3}
Machine2: {two:2} -> {two:2}
Machine3: {three:1, three:1, three:2} -> {three: 4}
Machine4: {four1, four:1} -> {four:2}
```

以上的例子只是对MapReduce过程的一个非常简化的概括，实际过程更加复杂，在系统设计中我们更需要了解的是它的思路。除了统计词频以外，MapReduce还有许多应用，更多例子可以在MapReduce论文中文版和《[基于MapReduce的应用案例](https://zhuanlan.zhihu.com/p/31972709)》中找到。这里列举一些论文中的例子

> * 分布式的Grep：Map函数输出匹配某个模式的一行，Reduce函数是一个恒等函数，即把中间数据复制到输出。
> * 计算URL访问频率：Map函数处理日志中web页面请求的记录，然后输出\(URL,1\)。Reduce函数把相同URL的value值都累加 起来，产生\(URL,记录总数\)结果。
> * 倒转网络链接图：Map函数在源页面（source）中搜索所有的链接目标（target）并输出为\(target,source\)。 Reduce函数把给定链接目标（target）的链接组合成一个列表，输出\(target,list\(source\)\)。
> * 每个主机的检索词向量：检索词向量用一个\(词,频率\)列表来概述出现在文档或文档集中的最重要的一些词。Map函数为每一个输入文档输出\(主机 名,检索词向量\)，其中主机名来自文档的URL。Reduce函数接收给定主机的所有文档的检索词向量，并把这些检索词向量加在一起，丢弃掉低频的检索 词，输出一个最终的\(主机名,检索词向量\)。
> * 倒排索引：Map函数分析每个文档输出一个\(词,文档号\)的列表，Reduce函数的输入是一个给定词的所有（词，文档号），排序所所有的文档 号，输出\(词,list（文档号）\)。所有的输出集合形成一个简单的倒排索引，它以一种简单的算法跟踪词在文档中的位
> * 分布式排序：Map函数从每个记录提取key，输出\(key,record\)。Reduce函数不改变任何的值。这个运算依赖分区机制\(在 4.1描述\)和排序属性\(在4.2描述\)。

\[TBD: add image\]

\*_图片来源：_[_https://stackoverflow.com/questions/20317152/how-shuffling-is-done-in-mapreduce_](https://stackoverflow.com/questions/20317152/how-shuffling-is-done-in-mapreduce)

## Bloom Filter

如果我们要想判断一个元素是不是在一个集合里，一般是将集合中所有元素保存在某个数据结构里，然后通过比较确定，根据元素的特性，我们可以用树，哈希表等结构来存储。但是不同的简单的数据结构都有一定的缺陷，比如通过树查找元素的时候时间复杂度为O\(logN\)，通过哈希表为O\(1\)，空间复杂度为O\(N\)。 当数据量巨大的时候，时间效率和空间效率就显得十分重要。BloomFilter就是一个在时间和空间上都十分有效的算法。



缺点：有一定的误识别率，删除困难

更多细节可以阅读《[海量数据处理之Bloom Filter详解](http://buttercola.blogspot.com/2015/11/big-data-bloom-filter.html)》

## 多层桶



## Trie树



## Lossy Counting & Sticky Sampling



