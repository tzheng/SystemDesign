# Majority Element 找主元素

Given an array of size _n_, find the majority element. The majority element is the element that appears more than`⌊ n/2 ⌋` times.

You may assume that the array is non-empty and the majority element always exist in the array.

  


**LC. 169. Majority Element**

首先来看最简单的版本，有一个元素超过了 n/2，找到他，解法也很直接，遇到相同的数就累加count，遇到不同的就减1，如果count = 0，就更新一下major元素为当前元素，因为某个元素超过了 n/2，那么到最后肯定他的 count会 &gt; 0， 因为和他相同的元素比和他不同的元素多。代码如下

  


![](http://mmbiz.qpic.cn/mmbiz_png/PwUnHgDiafaG5hsjF4czNBaOg7w6pgwMY46FLvrb1Q0MHsgN2ehofFiak3QWYzgk6Axo2TG3vy5XGM7akLriaYdog/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1)

具体模拟过程请看下图。图片来源 google image

![](http://mmbiz.qpic.cn/mmbiz_png/PwUnHgDiafaG5hsjF4czNBaOg7w6pgwMYMydDl1vNWaUVOLDibcgydVtuSgZjbMmgtNibYGvLTKYtUnNQNdicnBkEw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1)

  


  


**Followup 1: LC 229.  Majority Element II   **

题目升级了，现在要找到所有出现次数大于 N/3 的元素。这种元素最多可以有两个。我们看到， 找N/2的解法就是用一个count来记录出现次数，现在拓展到两个了，那么我们就可以用两个count来记录。 遍历数组，对于当前数字，如果他等于n1, 或者 n2, 就累加c1，c2， 如果c1，c2 等于0， 就最更新为当前数字如果数既不是n1也不是n2，那么c1，c2都要减去1.**当然要注意，找到n1，n2之后还要再遍历一遍数组，确定他们真的出现次数大于N/3。**这里的时间复杂度仍然是O\(N\)

  


![](http://mmbiz.qpic.cn/mmbiz_png/PwUnHgDiafaG5hsjF4czNBaOg7w6pgwMYYAlvxfBehb8RgAgqb61BlVZMX2BCf2LSqEn6O1ZXrS0XCAWtnSs2Dw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1)

  


  


**Followup 2: 我们再次升级，找出所有出现次数大于n/k的数字**

经过了前面的找出现次数大于 n/2 和 n/3的过程，我们知道，所有出现次数大于 n/k的数字最多只有 k-1 个。解法和N/3的类似，只是此时我们需要开一个k-1大小的数组，这里我们叫他tmp，数组元素结构如下：  


classElemCount {

    Integerelem;

    intcount;

}

也就是相当于 Followup 1 中的 {c1, n1}, {c2, n2}的合体。然后我们遍历数组，逻辑和前面题目类似。如果当前数字已经出现过，那么增加count，如果没有出现过，那么看看tmp数组有没有被填满。如果tmp没有填满，就把当前数字加进去，如果tmp已经填满了，那么我们就把tmp里面所有的数字的count 都减去1。具体代码如下。 时间复杂度是 O\(NK\), 空间复杂度是 O\(K\)

  


![](http://mmbiz.qpic.cn/mmbiz_png/PwUnHgDiafaG5hsjF4czNBaOg7w6pgwMYFESbnu5hCLTdflaNTEPYKjB7HzsAW55kI9OHeNWibslYtzIArEp6iavg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1)

  


**Followup 3: 如果输入数组是排好序的，而且不能使用额外空间，怎么办？**

我们这里以 k=4 为例子。

比如  1,1,2,2,2,3,3,3  把整个区域分成\[0, n/4-1\]; \[n/4, n/2-1\]; \[n/2, 3n/4-1\]; \[3n/4, n-1\]   分成四块， 如下所示

  


      \[1,1\] , \[2,2\], \[2,3\], \[3,3\]

     B1     B2     B3     B4

  


我们看到如果一个数字出现超过N/4次，他必然会在自己相邻的N/4块中再出现一次。因为数组是排序的，当数字出现次数大于N/4，一个长度为N/4的区块肯定装不下这个数字，所以在相邻的区块上一定会有该数字出现。比如上面的2， 在B2出现之后，B3还出现。3在B3出现了，在B4也出现了。

  


所以我们可以在在1/4, 1/2, 3/4处往左边和右边做binary search找边界，如果边界大于N/K，说明数字出现超过了N/K次，时间复杂度是O\(k \* log\(n/k\)\) 空间是O\(1\)  


  


![](http://mmbiz.qpic.cn/mmbiz_png/PwUnHgDiafaG5hsjF4czNBaOg7w6pgwMYYzSnROmEO1O8iaR4uOb0yWibjciaRMNh5xNFDibr3Fw8CgBG1NeRfsvOEg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1)

  


  


