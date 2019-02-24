package Algo.Trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

class Trie {

	//TODO add trie print tree ... 	
	class TrieNode {
		char val;
		boolean isWord = false;
		int nodeLevel = -1 ;
		TrieNode parent = null ;
		HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();

		public TrieNode(char c, int level, TrieNode parent) {
			this.val = c; this.nodeLevel = level; this.parent = parent;
		}

		@Override
		public String toString() {
			return "TrieNode [val=" + val + ", isWord=" + isWord
					+ ", nodeLevel=" + nodeLevel + "]";
		}
	}

	HashMap<Character, TrieNode> main = new HashMap<Character, TrieNode>();

	@Override
	public String toString() {
		return "nodes : " + main;
	}

	/** Inserts a word into the trie. */
	public void insert(String word) {
		TrieNode current = null;
		char c;
		for (int i = 0; i < word.length(); i++) {
			c = word.charAt(i);
			if (i == 0) {
				if (!main.containsKey(c)) {
					main.put(c, new TrieNode(c, 0, null));
				}
				current = main.get(word.charAt(i));
			} else {
				if (!current.children.containsKey(word.charAt(i))) {
					current.children.put(c, new TrieNode(c, current.nodeLevel+1, current));
				}
				current = current.children.get(word.charAt(i));
			}

			if (i == word.length() - 1) {
				current.isWord = true;
			}
		}
	}

	/** Returns if the word is in the trie. */
	public boolean search(String word) {

		char c;
		TrieNode current = null;
		for (int i = 0; i < word.length(); i++) {
			c = word.charAt(i);
			if (i == 0) {
				if (!main.containsKey(c)) {
					break;
				}
				current = main.get(c);
			} else {
				if (!current.children.containsKey(c)) {
					break;
				}
				current = current.children.get(c);
			}

			if (i == word.length() - 1 && current.isWord) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns if there is any word in the trie that starts with the given prefix.
	 */
	public boolean startsWith(String prefix) {

		char c;
		TrieNode current = null;
		for (int i = 0; i < prefix.length(); i++) {
			c = prefix.charAt(i);

			if (i == 0) {
				if (!main.containsKey(c)) {
					return false;
				}
				current = main.get(c);
			} else {
				if (!current.children.containsKey(c)) {
					return false;
				}
				current = current.children.get(c);
			}
		}
		return true;
	}

	public ArrayList<String> getAllWordsStartingWith(String prefix) {
		ArrayList<String> result = new ArrayList<String>();

		if (prefix.length() == 0) {
			for (TrieNode current : main.values()) {
				getAllWordsFromNode(prefix, current, result);
			}
			return result;
		}

		// first stop at the end of the prefix ...
		char c;
		TrieNode current = null;
		for (int i = 0; i < prefix.length(); i++) {
			c = prefix.charAt(i);
			
			if (i == 0) {
				if (main.containsKey(c)) {
					current = main.get(c);
				} else {
					return result;
				}
			} else {
				if (current.children.containsKey(c)) {
					current = current.children.get(c);
				}else {
					return result ;
				}
			}
			
			if(current.isWord && i < prefix.length()-1){
				result.add(prefix.substring(0, i+1)) ;
			}
		}
		// removing last letter ... because it will be concatenated inside
		prefix = prefix.substring(0, prefix.length() - 1);
		return getAllWordsFromNode(prefix, current, result);
	}

	private ArrayList<String> getAllWordsFromNode(String prefix, TrieNode current, ArrayList<String> result) {

		// TODO check this bug
		if (current.isWord) {
			result.add(prefix + current.val);
		}

		// now current have the last char in the prefix ...
		for (TrieNode child : current.children.values()) {
			// System.out.println("Now in " + current.val + " going in child " + child.val);
			getAllWordsFromNode(prefix + current.val, child, result) ;
		}

		return result;
	}

	public int getTrieSize(){
		int count = 0 ;
		for(TrieNode node : main.values()){
			Queue<TrieNode> queue = new LinkedList<TrieNode>() ;
			queue.add(node);
			while(!queue.isEmpty()){
				TrieNode current = queue.poll() ;
				count++ ;
				queue.addAll(current.children.values()) ;
			}
		}
		return count;
	}
	

	public String printTrie(boolean printToConsole){
		StringBuffer sb = new StringBuffer() ;
		for(TrieNode node : main.values()){
			String nodePrint = printNode(node) ;
			sb.append(nodePrint) ;
			if(printToConsole) System.out.println(nodePrint);
		}
		return sb.toString() ;
	}
	
	private String printNode(TrieNode node) {
	    int indent = 0;
	    StringBuilder sb = new StringBuilder();
	    printChildrenTree(node, indent, sb);
	    return sb.toString();
	}

	private static void printChildrenTree(TrieNode node, int indent, StringBuilder sb) {
	    sb.append(getIndentString(indent));
	    sb.append("+--");
	    sb.append(node.val);
	    sb.append("/");
	    sb.append("\n");
	    for (TrieNode child : node.children.values()) {
	        if (!child.children.isEmpty()) {
	            printChildrenTree(child, indent + 1, sb);
	        } else {
	        	printNode(child, indent + 1, sb);
	        }
	    }
	}

	private static void printNode(TrieNode node, int indent, StringBuilder sb) {
	    sb.append(getIndentString(indent));
	    sb.append("+--");
	    sb.append(node.val);
	    sb.append("\n");
	}

	private static String getIndentString(int indent) {
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < indent; i++) {
	        sb.append("|  ");
	    }
	    return sb.toString();
	}

	public String reverseWord(String word) {
		StringBuffer sb = new StringBuffer();
		for (char c : word.toCharArray()) {
			sb.insert(0, c);
		}
		return sb.toString();
	}

	public void insertArray(String[] words) {
		for (String word : words) {
			this.insert(word);
		}
	}

	public void insertReversedWords(String[] words) {
		for (String word : words) {
			this.insert(reverseWord(word));
		}
	}
}