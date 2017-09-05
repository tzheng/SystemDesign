import java.util.ArrayList;
import java.util.List;

public class TextJustification {
	 public List<String> fullJustify(String[] words, int maxWidth) {
        
        List<String> result = new ArrayList<String>();
        
        int last = 0;
        int currLen = 0;
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (word.length() > maxWidth) {
                return result;
            }
            //word total length + curren word length + space numbers
            if (currLen + word.length() + (i-last) > maxWidth) {
                int spaceNum = 0;
                int extraNum = 0;
                if (i - last - 1 > 0) {
                    spaceNum = (maxWidth - currLen) / (i- last - 1);
                    extraNum = (maxWidth - currLen) % (i- last - 1);
                }
                StringBuilder sb = new StringBuilder();
                for (int j = last; j < i; j++) {
                    sb.append(words[j]);
                    if (j < i-1) {
                        for (int k = 0; k < spaceNum; k++) {
                            sb.append(" ");
                        }
                        if (extraNum > 0) {
                            sb.append(" ");
                            extraNum--;
                        }
                    }
                }
                //下面这个for循环作用于一行只有一个单词还没填满一行的情况
                for (int j = sb.length(); j < maxWidth; j++) {
                    sb.append(" ");
                }
                
                result.add(sb.toString());
                currLen = 0;
                last = i;
            } 
            
            currLen += word.length();
        }
        
        //last line
        StringBuilder sb = new StringBuilder();
        for (int i = last; i < words.length; i++) {
            sb.append(words[i]);
            if (i < words.length - 1) {
                sb.append(" ");
            }
        }
        
        for (int j = sb.length(); j < maxWidth; j++) {
            sb.append(" ");
        }
        result.add(sb.toString());
        return result;
    }
	 
	 public static void main(String[] args) {
		 TextJustification clz = new TextJustification();
		 clz.fullJustify(new String[]{"a","b","c","d","e"}, 3);
	 }
}
