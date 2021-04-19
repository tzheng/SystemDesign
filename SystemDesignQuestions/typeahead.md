# 输入提示问题 - Typeahead Problem


## 题目
Typeahead/Auto Complete 一般用于输入提示/自动补全，这类问题在系统设计中也十分常见，一般typeahead有两种，一种是所有用户的typeahead提示都一样，是最近热门搜索词，另一种是根据用户自己的历史记录给出相应推荐，当然，有时候还会有混合型的。 这类问题在某些部分和[Top K 问题](top-k-problem.md) 相似，都是需要在大量数据中选出热门的搜索词条，对结果同样也不需要非常高的精度。不过Typeahead还有个读频繁的，在数据存储和分区方面会有不

## 总结
读写频繁，不用完全精确选出提示内容（精确程度需要和面试官确认）。
* 数据查询（用户每次搜索通过查询服务返回提示）
* 数据搜集（可以通过采样、缓存等方式优化） 
* 数据聚合（一致性哈希分配机器，桶，堆和队列进行统计）



## 需求分析 
### 直接需求
* 用户输入的时候给出提示
* 提示的词条为5个（10个也行，一般为一个很小的数字），提示词条根据所有用户的搜索行为来选择，比如当前大量用户搜索apple，则输入app应该提示出apple。
* 需要做拼写检查，找出相近词的提示（比如color/colour， 或者recruiter/recrutier)
* 不需要根据每个用户行为定制结果，即所有用户的提示都一样。
* 提示词条的时效性为x年/x个月，如一个词仅在很久以前是热搜，即便搜索量巨大，现在也不应该出现，当然一般这种情况不太可能发生。

### 隐含需求
以下内容需要跟面试官确认。
* 读写频繁 (read & write heavy)
* 数据不要求有非常高的实时性（可以有适当延迟，比如几分钟，但不能是一天或者几个小时）
* 数据不要求有非常高的精确性（只需要知道热搜前几名是什么即可）
* 高可用性（一般系统都需要）

## 估算 （Estimation）
* DAU: 1 Billion, 20% of user post everyday <br>
* Write QPS: 1B * 20% / 86400 ~ 200M/100K ~ 2k QPS, peak QPS ~ 6k<br>
* Read QPS: 假设为写的10%， 200 QPS <br>
* Data storage: 假设 word (20 Bytes), count(8 Bytes) , 200M/day * 28 Bytes ~ 200*30MB ~ 6GB / day <br>

数据量大，服务用户多，肯定需要分布式系统来处理。


## 其他
#### 如果需求是每个用户结果不一样？
## 估算 （Estimation）
* DAU: 1 Billion, 20% of user post everyday <br>
* Write QPS: 1B * 20% / 86400 ~ 200M/100K ~ 2k QPS, peak QPS ~ 6k<br>
* Read QPS: 假设为写的10%， 200 QPS <br>
* Data storage: 假设 word (20 Bytes), count(8 Bytes) , 200M/day * 28 Bytes ~ 200*30MB ~ 6GB / day <br>

数据量虽然不大，但是QPS比较高，需要多台机器处理。             