import java.util.Arrays;

public class LongestSubstringAtLeastK {
	
	public int longestSubstring(String s, int k) {
        char[] str = s.toCharArray();
        int[] counts = new int[26];
        int h, i, j, idx, max = 0, unique, noLessThanK;
//        For each h, apply two pointer technique to find the longest substring 
//        with at least K repeating characters and the number of unique characters in substring is h.
        for (h = 1; h <= 26; h++) {
            Arrays.fill(counts, 0);
            i = 0; 
            j = 0;
            unique = 0;
            noLessThanK = 0;
            while (j < str.length) {
                if (unique <= h) {
                    idx = str[j] - 'a';
                    if (counts[idx] == 0)
                        unique++;
                    counts[idx]++;
                    if (counts[idx] == k)
                        noLessThanK++;
                    j++;
                }
                else {
                    idx = str[i] - 'a';
                    if (counts[idx] == k)
                        noLessThanK--;
                    counts[idx]--;
                    if (counts[idx] == 0)
                        unique--;
                    i++;
                }
                if (unique == h && unique == noLessThanK)
                    max = Math.max(j - i, max);
            }
        }
        
        return max;
    }
	
	
	public int longestSubstringRegex(String s, int k) {
        if(k<=1){
            return s.length();
        }
        
        int[] repeat=new int['z'+1];
        for(int i=0;i<s.length();i++){
            repeat[s.charAt(i)]++;
        }
        StringBuilder regex=new StringBuilder("");
        boolean firstSplit=true;
        for(int i='a';i<='z';i++){
            if(repeat[i]>0&&repeat[i]<k){
                if(firstSplit){
                    regex.append((char)i);
                    firstSplit=false;
                }
                else{
                    regex.append("|"+(char)i);
                }
            }
        }
        if(regex.length()>0){
            //说明有分隔符
            String[] strs=s.split(regex.toString());
            int max=0;
            int tmpAns=0;
            for(String str:strs){
                tmpAns=longestSubstring(str, k);
                if(tmpAns>max){
                    max=tmpAns;
                }
            }
            return max;
        }
        else{
            //没有分隔符,说明s中的每一个字符出现的次数都大于等于k
            return s.length();
        }
    }
	
	public static void main(String[] args) {
		LongestSubstringAtLeastK clz = new LongestSubstringAtLeastK();
		clz.longestSubstring("aaabb", 3);
	}
}
