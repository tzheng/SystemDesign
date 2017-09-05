
public class RemoveCommentsInCode {
	
/**
 * Case1: string contains "//", "/../", we cannot remove them.
If string literal contains nested string literals, we must find the second " that is not escaped by a backslash.
Every time when we meet a backslash, we skip the next character.

Case2: a single quotation character, we must ignore it.

Case3: /../ contains nested /../
For the nested /.. /.. * /.. /, we need to simulate a stack to find the correct /, otherwise, we might stop removing the comments too early.
If /../ contains the ", we cannot consider it as the start of a string literal.

In short, we just print every thing between the first /* and the last * / . 
For //, if it is not in a string literal, we just print everything after it until the end of the line.

 * @param str
 */
	public void remove(String str) {
		System.out.println("before: \n" + str);
		
		boolean singleLine = false;
		boolean multiLine = false;
		boolean inString = false;
		
		StringBuilder out = new StringBuilder();
		for (int i = 0 ; i < str.length(); i++) {
			char c = str.charAt(i);
			if (singleLine && c == '\n') {
				singleLine = false;
			} else if (multiLine && c == '*' && str.charAt(i+1) == '/') {
				multiLine = false; 
				i++;
			} else if (singleLine || multiLine) {
				continue;
			} else if (c == '/' && str.charAt(i+1) == '/' && !inString) {
				singleLine = true;
				i++;
			} else if (c== '/' && str.charAt(i+1) == '*' && !inString) {
				multiLine = true;
				i++;
			} else {
				if (c == '"') {
					if (!inString) {
						inString = true;
					} else if (str.charAt(i-1) != '\\') {
						inString = false;  //注意要预防escape
					}
				}
				out.append(c);
			}
		}
		
		
		System.out.println("after: \n" + out.toString());
		System.out.println();
	}
	public static void main(String[] args) {
		RemoveCommentsInCode clz = new RemoveCommentsInCode();
		
		clz.remove("String str = new String(); /** this is something **/");
		clz.remove("str = \"//\";");
		clz.remove("str = a; //this is comments");
		clz.remove("//whole line comments");
		clz.remove("/** multi line \n *multiLine \n **/");
		
		String prgm = "   /* Test program */ \n" +
                "   int main()  \n" +
                "   {           \n" +
                "      // variable declaration \n" +
                "      int a, b, c;    \n" +
                "      /* This is a test  \n" +
                "          multiline     \n" +
                "          comment for   \n" +
                "          testing */      \n" +
                "      a = b + c;       \n" +
                "   }           \n";
		clz.remove(prgm);
	}
}
