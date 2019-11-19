import java.util.ArrayList;
import java.util.Arrays;

public class Node {
	private String term;
	private ArrayList<Node> children;
	private char type;

	public Node(String term) {
		this.term = term;
		this.children = new ArrayList<Node>();
	}

	public String getTerm() {
		return this.term;
	}

	public ArrayList<Node> getChildren() {
		return this.children;
	}

	public void setChildren(ArrayList<Node> children) {
		this.children = children;
	}

	public char getType() {
		return this.type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public String toString() {
		return this.type + ":" + this.term + "=>" + Arrays.toString(this.children.toArray());
	}
}
