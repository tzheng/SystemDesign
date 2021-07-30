# 程序员面试总结

## 行为面试 Behavior Interview
* [Behavior Interview](behavior-interview/behavior-interview.md)


## 系统设计
系统设计，总的来说就类似于面试官让你盖房子，你搭个框架给他看证明你有能力，面试官可以就跟你讨论讨论框架，也可以跟你讨论什么材料适合，也可以跟你讨论具体某一层楼的室内装修，所以准备系统设计特别耗费时间，需要比较全面的知识。很多新手往往觉得无从下手，但是我认为对于新手还是有一些基本套路可循，所以做了一份设计总结。

系统设计中，最重要的就是 Trade-off，中文应该叫权衡，系统设计之所以难就是难在这里，因为你在设计过程中做的每一个决定都需要给出理由，比如为什么用SQL为什么用NoSQL，这时候就需要考察你对不同数据库的理解，而且在不断深入的过程中，面试官会随时让你挑一个地方让你做具体设计，如果碰巧挑到你不是很熟悉的地方或者没有考虑过的问题，那么面试就危险了。所谓我在这里介绍概念和题目讲解的时候，尽量多覆盖一些知识点，尤其是讲解题目，我会尽量列出多种方法，然后说说不同方法适用于什么情况，如何做出权衡。**个人水平有限，如果有错误的地方请大家指出改正。**

我认为几个需要强调的地方：

* 系统设计没有标准答案 
* 系统设计重点在于权衡
* 不要只说名词不解释
* 面试过程中需要随机应变
* 面试官有的时候可能不要你回答业界通用的做法

在跟一些面试官交流过程中，大部分面试官都提到了系统设计的考点：

* do you gather requirement
* do you define the right limits of the system(what the system can/cannot do), do you realize trade-offs
* do you think big enough to have the right level of abstraction and keep it simple
* do you have basic knowledge, such as avoid single point of failure, CAP theorem, scaling, fault tolerant, consistent hasing...
* can you think clearly, do you have a good working method, do you loop back on requirement to test if your design work well, do you call out how the system can and can't envolve
* do you have domain expertise

一般系统设计都会涉及到以下几个方面：

首先是需求收集，这里要不断和面试官沟通，了解这个系统需要支持哪些功能，用户量有多少，然后你可以估算系统的QPS。然后需要考虑系统有哪些特性，根据前面计算的QPS判断系统是读多还是写多，是需要可用性(availability) 还是需要一致性(Consistency)。具体可以参照以下文章：
* [估算](SystemDesignBasics/estimations.md)
* [CAP定理](SystemDesignBasics/cap.md)

搜集完需求之后，接下来就是具体业务逻辑设计，业务逻辑很难归纳，因为每个系统都有自己的业务逻辑，不过针对一些特定的问题有些常见的解决方案，比如你需要设计一个全局UUID有几种解决办法，或者地理位置服务（LBS）通常怎么设计，或者是如何处理海量数据，这些内容可以在系统设计概念中找到。
* [地理位置服务](SystemDesignBasics/lbs.md)
* [分布式全局id](SystemDesignBasics/globaluuid.md)
* [海量数据处理](SystemDesignBasics/massive-data-processing.md)
* [消息队列](SystemDesignBasics/message-queue.md)


再接着就是讨论数据存储，数据存储无外乎就是缓存和永久存储，缓存有许多种类，比如write through, cache aside 等等。永久存储一般是讨论SQL 和 NoSQL的区别，极个别特殊情况可能会直接用文件系统(File System)来存储，这些内容会在以下部分谈到：
* [缓存](SystemDesignBasics/cache.md)
* [SQL和NoSQL](SystemDesignBasics/sqlvsnosql.md)

除此之外，每一层的设计都会涉及到扩容的问题，还有如何增加备份冗余，以防单点故障(Single point of failure)的问题。为了处理海量访问请求，通常解决办法就是增加机器数量，然后保证负载均衡(load balance)，业务逻辑层，数据存储层都自己的办法，比如master-slave。这些内容会在很多地方被反复提及，具体可以参照：
* [一致性哈希 - Consistent Hashing](SystemDesignBasics/consistent-hashing.md)
* [分区 - Sharding, Partitioning](SystemDesignBasics/sharding.md)

在讨论完了基本概念之后，我们可以总结一套写常见系统设计题的解答。
* [热门排行榜 - Top K问题](SystemDesignQuestions/top-k-problem.md)
* [输入提示 - Typeahead](SystemDesignQuestions/typeahead.md)
* [视频平台(Youtube, Netflix)](SystemDesignQuestions/videoPlatform.md)
* [网络爬虫](SystemDesignQuestions/WebCrawl.md)
* [在线聊天服务](../SystemDesignQuestions/chat.md)
* [信息流/时间线（Facebook，Twitter...)](SystemDesignQuestions/newsfeed.md)
* [地理位置服务（Yelp)](SystemDesignQuestions/PlaceSuggestionPOI.md)
* [打车服务(Uber, Lyft)](SystemDesignQuestions/rideshare.md)
* [搜索服务(Google, Facebook Status Search)](SystemDesignQuestions/search.md)
* [点击统计服务](SystemDesignQuestions/clickCount.md)
* [分布式文件系统](SystemDesignQuestions/distributedFileSystem.md)
* [分布式工作调度服务](SystemDesignQuestions/eventScheduler.md)

