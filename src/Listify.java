import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Listify {

	//Splits arguments of a function/predicate.
	public static ArrayList<String> splitSp(String terms) {
		ArrayList<String> arguments = new ArrayList<String>();
		int parenthsis = 0;
		String currentTerm = "";
		for (int i = 0; i < terms.length(); i++) {
			char currentChar = terms.charAt(i);
			if (currentChar == '(') {
				parenthsis++;
				currentTerm += currentChar;
			} else {
				if (currentChar == ')') {
					parenthsis--;
					currentTerm += currentChar;
				} else {
					if (currentChar == ',' && parenthsis == 0) {
						arguments.add(currentTerm);
						currentTerm = "";
					} else {
						currentTerm += currentChar;
					}
				}
			}
		}
		arguments.add(currentTerm);
		return arguments;
	}

	//Decomposes a logical statement into different terms. The terms are structured in a tree form where each statement has children if it was a function or predicate. 
	public static Node decomposeStatement(String statement) {
		String pattern = "(\\w+)(\\([a-zA-Z0-9\\(\\)]+([,][a-zA-Z0-9\\(\\)]+)*\\))*";

		Pattern r = Pattern.compile(pattern);

		Matcher m = r.matcher(statement);
		if (m.find()) {
			Node parent = new Node(m.group(1));
			String term = m.group(2);
			if (term != null) {
				parent.setType('T');
				term = term.substring(1, m.group(2).length() - 1);
				ArrayList<String> arugements = splitSp(term);
				for (String s : arugements) {
					Node child = decomposeStatement(s);
					parent.getChildren().add(child);
				}
			} else {
				String firstCharacter = parent.getTerm().charAt(0) + "";
				boolean hasUppercase = !firstCharacter.equals(firstCharacter.toLowerCase());
				if (hasUppercase) {
					parent.setType('V');
				} else {
					parent.setType('C');
				}
			}
			return parent;
		} else {
			return null;
		}
	}

	//Simple Listify function that implements the same behavior of =.. operator in prolog.
	public static ArrayList<String> Listify(String statement) {
		ArrayList<String> arguments = new ArrayList<String>();
		String pattern = "(\\w+)(\\([a-zA-Z0-9\\(\\)]+([,][a-zA-Z0-9\\(\\)]+)*\\))*";
		Pattern r = Pattern.compile(pattern);

		Matcher m = r.matcher(statement);
		if (m.find()) {
			arguments.add(m.group(1));
			String term = m.group(2);
			if (term != null) {
				term = term.substring(1, m.group(2).length() - 1);
				ArrayList<String> arugements = splitSp(term);
				for (String s : arugements) {
					arguments.add(s);
				}
			}
		} else {
			System.out.println("NO MATCH");
		}
		return arguments;
	}

	public static void main(String[] args) {
		String line = "and(1,and(and(X1,X2),Y),Z)";
		System.out.println(Arrays.toString((Listify(line)).toArray()));
		System.out.println(decomposeStatement(line));
	}

}
