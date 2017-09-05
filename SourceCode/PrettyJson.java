import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class PrettyJson {

	
	static void printJSON(String jsonStr) {
		if(jsonStr == null || jsonStr.trim().length() == 0){
			System.out.println(jsonStr);
			return;
		}
		final String ret = "\n";
		StringBuilder formattedJson = new StringBuilder();
		StringBuilder spaces = new StringBuilder();
		for(int i=0; i<jsonStr.length(); ){
			char c = jsonStr.charAt(i);
			switch(c){
			case '{':
			case '[':
				spaces.append("\t");
				formattedJson.append(c).append(ret).append(spaces);
				break;
			case '}':
			case ']':
				spaces.deleteCharAt(spaces.length()-1);
				formattedJson.append(ret).append(spaces).append(c);
				if(!(i+1<jsonStr.length() && jsonStr.charAt(i+1) == ',')){
					formattedJson.append(ret).append(spaces);
				}
				break;
			case ',':
				formattedJson.append(c).append(ret).append(spaces);
				break;
			default:
				formattedJson.append(c);
				break;
			}
			i++;
		}
		System.out.println(formattedJson);
	}
	
	
	
	static String parseJson(String str) {
		Stack<String> stack = new Stack<String>();
		stack.push("");
		StringBuilder sb = new StringBuilder();
		boolean newLine = true;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			String peek = stack.peek();
			if (newLine) {
				sb.append(peek);
				newLine = false;
			}
			if ( c == '{' || c == '[') {
				sb.append(c).append("\n");
				stack.push(peek + "    ");
				newLine = true;
			} else if (c == ']' || c == '}') {
				stack.pop();
				peek = stack.peek();
				char last = sb.charAt(sb.length() -1 );
				if ( last != '}' && last != ']') {
					sb.append("\n");
				}
				sb.append(peek).append(c);
				if (i+1 >= str.length() || str.charAt(i+1) != ',')
					newLine = true;
			} else if (c == ',') {
				sb.append(c).append("\n");
				newLine = true;
			} else if (c != ' ' || c != '\t') {
				sb.append(c);
				newLine = false;
			}
		}
		System.out.println(sb.toString());
		return sb.toString();
	}
	
	public static void main(String[] args) {
		String str = "{\"id\": \"0001\",\"type\": \"donut\",\"name\": \"Cake\",\"ppu\": 0.55, \"batters\":{\"batter\":[{ \"id\": \"1001\", \"type\": \"Regular\" },{ \"id\": \"1002\", \"type\": \"Chocolate\" }]},\"topping\":[{ \"id\": \"5001\", \"type\": \"None\" },{ \"id\": \"5002\", \"type\": \"Glazed\" }]}";
		
		printJSON(str);
//		
//		
//		parseJson(str);
////		
//		prettyJson2(str);
		
	}
}
