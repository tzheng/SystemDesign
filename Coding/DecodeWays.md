## Decode Ways 解码方式 {#activity-name}

Leetcode的第91题，也是一个比较经常出现的面试题。题目很考察面试者对动态规划的理解程度。

A message containing letters from `A-Z` is being encoded to numbers using the following mapping:

```
'A' -
>
 1
'B' -
>
 2
...
'Z' -
>
 26
```

Given an encoded message containing digits, determine the total number of ways to decode it.

For example,  
Given encoded message `"12"`, it could be decoded as `"AB"` \(1 2\) or `"L"` \(12\).

The number of ways decoding `"12"` is 2.

看到题目很容易想到，首先我们可以用递归来写, 思路直接参照代码，但是由于递归效率比较低，对于大数据来说会超时。因为如果数据很长的话，递归栈会特别大，而且有很多不必要的重复计算。递归是不推荐的做法，但是为了引出后面的Followup，这里还是放上了最原始的递归的代码。虽然有很多优化能做，但是这里不详细讨论了。

![](http://mmbiz.qpic.cn/mmbiz_png/PwUnHgDiafaH49NHJll2o9amxCI5u0PlqojkrsW8sQtYvMQRNT88ianVp0kldXl1oGic71eUcMebCxxepwd7WfxOA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1)

**Followup1: 优化递归**

我们看到，实际上递归 比如 1234 ， 1的时候解码了234，然后34，然后 4，继续走到2，在这里 又重新解码了 34, 4，导致多次重复。所以我们可以用动态规划来存之前解码过的结果，这样就不用反复计算。状态转移方程如下：

f\[i\] = number of decode ways from 0 to i-1;

f\[0\] = f\[1\] = 1 ,

\*num= s\[i-1\]\*10+s\[i\]

f\(i\) = f\(i-2\) + f\(i-1\), 10 &lt;=num&lt;= 26

```
 = f\(i-1\),             num&lt; 10 ornum&gt;26
```

这样做的时间复杂度是 O\(n\), 空间复杂度是 O\(n\)

![](http://mmbiz.qpic.cn/mmbiz_png/PwUnHgDiafaH49NHJll2o9amxCI5u0PlqZibkGIu1vAS0CzXxjewyt81BYSxk5Xc3e884FV4Ch5fUBMTz5xd8GPg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1)

**Followup 2：能不能用O\(1\) 的空间来解决问题**

我们看到，对于当前位置**i**，他的数值只和** i-1, i-2**的数值有关，所以其实我们根本不需要用一个数组来存。只需要两个数字存之前两位的结果，不断更新他们就好了。所以代码如下。

![](http://mmbiz.qpic.cn/mmbiz_png/PwUnHgDiafaH49NHJll2o9amxCI5u0PlqWZMj5RQcESib66hXjBqMsz5dRAMeQIntna50roic9BcRxnjFgY6ZfP1Q/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1)

**Followup 3：请给出所有的解**

由于需要给出所有的解，动态规划就不适用了，这时候我们只能通过深度优先搜索来找所有的解法。代码和本文最前面使用递归计算多少个解差不多，唯一区别就是现在需要另外开一个空间来记录当前搜索的路径。

![](http://mmbiz.qpic.cn/mmbiz_png/PwUnHgDiafaH49NHJll2o9amxCI5u0PlqDHeTqGmeAtGA70tUATfHvEAKSgT9JWzuf70dPYanib5azTcTQ8DFVJw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1)

**Followup 4：如果A 不是对应 1， 我给你一个自定义的Map，可以 A-&gt;9, B-&gt;10... 或者 A -&gt; 20, B-&gt; 50...  题目保证字母对应的数字 &lt; 100**

**    
**

之前的解法，我们在判断数字是不是有效的时候，都是直接写  '0' &lt; c &lt;= '9' 和 10 &lt;= number &lt;= 26. 其实这一部分逻辑我们可以放到一个 isValid\(String str\) 的方法里面。

privatebooleanisValid\(Stringstr\){

```
if\(str.charAt\(0\) =='0'\) {

    returnfalse;

}

intnum= Integer.valueOf\(str\);

returnnum&gt; 0 &&num&lt;= 26;
```

}

那么我们把这个方法稍微做一个修改，其实变得更简单了。

privatebooleanisValid\(intnum, Map&lt;Integer, Character&gt;map\){

```
returnmap.containsKey\(Integer.valueOf\(num\)\);
```

}

最后整体代码大致如下（未经大数据测试）

![](http://mmbiz.qpic.cn/mmbiz_png/PwUnHgDiafaH49NHJll2o9amxCI5u0PlqUhzEM0tvQjavBz03lmORNAMyE5c8yGFLbsFEbpWwmLIaBkTa7jtn6A/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1)

**总结**

总的来说，Followup4 给出的是一个通用解法。无论怎么变化， 只要掌握动态规划的转移方程，理清楚当前结果和前面结果的关系，这题就变得很容易了。

