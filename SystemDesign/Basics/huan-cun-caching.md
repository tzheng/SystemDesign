# 缓存 - Caching

## 概念

缓存效率远比数据库高，使用缓存可以减少对数据库的请求次数，提高系统效率，减少延时。

## 缓存使用场景

缓存可以再任何地方使用，系统设计过程中，往往会涉及到客户端\(client\)，服务器端\(server\)，和数据库端的设计，在任何一个环节我们都可以考虑使用缓存来提高响应速度。同时，缓存也可以作为一个单独的层级\(layer\)存在。

CDN

## 缓存更新时机

为了保证缓存中的数据的时效性，我们需要根据实际情况来选择不同的更新时机。

#### Cache-aside

#### Write-through

#### Write-back



# 缓存的优缺点

# 缓存淘汰算法 - Eviction Policies

缓存容量有限，当容量到达上线的时候，新写入的数据肯定需要按照某种算法淘汰旧的数据，这些算法就是缓存的淘汰算法\(eviction policy\)，常见的淘汰算法有：

* First In First Out \(FIFO\): 把缓存看成一个队列，遵循先进先出原则，不考虑数据访问是否频繁，当缓存满的时候，把最先进入缓存的数据给淘汰掉。 
  * set\(key, value\), 如果缓存中存在该key，则重置value，如果不存在，则将该key插入，如果缓存已满，淘汰最先进入数据
* Last In First Out \(LIFO\): 和FIFO相反。
* Least Recently Used \(LRU\): 非常常用的缓存算法，基于“如果一个数据在最近一段时间没有被访问到，那么在将来它被访问的可能性也很小”的思路，Leetcode上有LRU的题目，建议自己实现。
* Least Frequently Used \(LFU\): 使用最不频繁的最先淘汰，基于“如果一个数据在最近一段时间内使用次数很少，那么在将来一段时间内被使用的可能性也很小”的思路。Leetcode上有LFU的题目，建议自己实现。

当然还有其他的，MRU\(Most Recently Used\)、MFU\(Most Frequently Used\), Random Replacement等等，看名字大概就知道什么意思了。



