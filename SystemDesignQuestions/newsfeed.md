# 信息流 - Newsfeed/Timeline

待完成

设计一个New feeds api，可以返回user的friends的new feeds。

一开始先问问题，了解清楚需求。接着估算QPS、users等。
算完QPS，我就设计api，写了 readNewFeed(…) -> … 这个接口。
然后开始画图做设计。
画完service后开始画db tables，然后写有哪些fields，并且分析说用哪种数据库。

后面还讲到了怎么处理hot data，怎么做notification，怎么做error handling。
也聊到了在Internet受限的地方，要如何改进service。
