# Payment System - 支付系统

https://juejin.cn/post/6855584834253750286
airbnb 在不同国家收不同的货币，一个用户会有多种货币在他的钱包中 用户想要从钱包提现到自己的银行。我答道的点有idompotency, money unit 要是micro_cent, 每种货币一个自己的钱包，如何处理当银行那边的transaction 可能需要1-2 天才能到账（在DB中多一个frozen_money） 重点在数据库，我准备的时候参考的是这个

