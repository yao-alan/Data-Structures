/** Created by Alan Yao on 6/25/20 */

import java.util.*;

public class TrieSet
{
    Node sentinel;

    private class Node
    {
        private boolean isKey;
        private TreeMap<Character, Node> charMap;

        private Node() {
            charMap = new TreeMap<>();
        }
        private Node(boolean isKey) {
            this.isKey = isKey;
            charMap = new TreeMap<>();
        }
    }
    
    public TrieSet() {
        sentinel = new Node();
    }
    public TrieSet(String str) {
        sentinel = new Node();
        add(str);
    }

    public boolean contains(String str) {
        Node p = sentinel;
        Node c;
        int i = 0;
        while ((c = p.charMap.get(str.charAt(i))) != null) {
            p = c;
            ++i;
            if (i == str.length() && p.isKey == true)
                return true;
        }
        return false;
    }
    public void add(String str) {
        Node p = sentinel;
        Node c;
        int i = 0;
        while ((c = p.charMap.get(str.charAt(i))) != null) {
            p = c;
            ++i;
        }
        while (i < str.length() - 1) {
            Node next = new Node(false);
            p.charMap.put(str.charAt(i), next);
            p = next;
            ++i;
        }
        p.charMap.put(str.charAt(i), new Node(true));
    }
    /** Collects all keys in trie */
    public List<String> collect() {
        return collectHelp("", new ArrayList<>(), sentinel);
    }
    private List<String> collectHelp(String prefix, ArrayList<String> words, Node current) {
        if (current.isKey)
            words.add(prefix);
        for (Map.Entry<Character, Node> entry : current.charMap.entrySet()) {
            collectHelp(prefix + entry.getKey().toString(), words, entry.getValue());
        }
        return words;
    }
    /** Prints all words in the trie */
    public void printAll() {
        ArrayList<String> words = (ArrayList<String>)collect();
        for (String s : words)
            System.out.println(s);
    }
    /** Returns all keys with given prefix */
    public List<String> keysWithPrefix(String prefix) {
        return keysWithPrefixHelp(prefix, new ArrayList<>(), sentinel);
    }
    private List<String> keysWithPrefixHelp(String prefix, ArrayList<String> words, Node current) {
        int i = 0;
        Node c;
        while (i < prefix.length() && (c = current.charMap.get(prefix.charAt(i))) != null) {
            ++i;
            current = c;
        }
        if (i != prefix.length())
            return null;
        else 
            return collectHelp(prefix, words, current);
    }
    public void printAllWithPrefix(String prefix) {
        ArrayList<String> words = (ArrayList<String>)keysWithPrefix(prefix);
        for (String s : words)
            System.out.println(s);
    }
    public String longestPrefixOf(String word) {
        String prefix = "";
        Node current = sentinel;
        Node c;
        int i = 0;
        while ((c = current.charMap.get(word.charAt(i))) != null && (i == 0 || current.charMap.size() == 1)) {
            prefix += word.charAt(i);
            current = c;
            ++i;
        }
        return prefix;
    }
    /** Tester method */
    public static void main(String[] args) {
        TrieSet trie = new TrieSet();
        trie.add("B");
        trie.add("Bayus");
        trie.add("Bluh");
        trie.add("Blubba");
        trie.add("Blubbah");
        trie.add("UCB");
        System.out.println(trie.longestPrefixOf("Blubba"));
    }
}