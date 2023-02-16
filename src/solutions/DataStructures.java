package solutions;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

class UnionFind {
    private Map<Integer, Integer> parents;
    private Map<Integer, Integer> sizeOfSets;
    private int numOfSets;

    UnionFind() {
        this.parents = new HashMap<>();
        this.sizeOfSets = new HashMap<>();
        this.numOfSets = 0;
    }

    public void add(int num) {
        if (parents.containsKey(num))
            return;
        parents.put(num, null);
        sizeOfSets.put(num, 1);
        numOfSets++;
    }

    public int find(int num) {
        int root = num;
        while (parents.get(root) != null) {
            root = parents.get(root);
        }
        int cur = num;
        while (cur != root) {
            int parent = parents.get(cur);
            parents.put(cur, root);
            cur = parent;
        }
        return root;
    }

    public void merge(int num1, int num2) {
        int root1 = find(num1);
        int root2 = find(num2);
        if (root1 == root2)
            return;
        parents.put(root2, root1);
        sizeOfSets.put(root1, sizeOfSets.get(root1) + sizeOfSets.get(root2));
        numOfSets--;
    }

    public boolean isConnected(int num1, int num2) {
        return find(num1) == find(num2);
    }

    public int getSizeOfSet(int num) {
        return sizeOfSets.get(find(num));
    }

    public int getNumOfSets() {
        return numOfSets;
    }
}



class TrieNode {
    Map<Character, TrieNode> children;
    boolean isWord;
    String word;
    TrieNode() {
        this.children = new HashMap<>();
        this.isWord = false;
        this.word = null;
    }
}

class Trie {

    TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode cur = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (!cur.children.containsKey(ch)) {
                cur.children.put(ch, new TrieNode());
            }
            cur = cur.children.get(ch);
        }
        cur.isWord = true;
        cur.word = word;
    }

 
    public boolean search(String word) {
        TrieNode node = findNode(word);
        return node != null && node.isWord;
    }


    public boolean startsWith(String prefix) {
        return findNode(prefix) != null;
    }

    private TrieNode findNode(String str) {
        TrieNode cur = root;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (!cur.children.containsKey(ch))
                return null;
            cur = cur.children.get(ch);
        }
        return cur;
    }
    
    public boolean searchWithPattern(String word) {
        return divideConquer(root, word, 0);
    }

    private boolean divideConquer(TrieNode root, String word, int index) {
        if (index == word.length()) {
            return root.isWord;
        }
        char ch = word.charAt(index);
        if (ch == '.') {
            for (char c : root.children.keySet()) {
                if (divideConquer(root.children.get(c), word, index + 1)) {
                    return true;
                }
            }
        }
        if (!root.children.containsKey(ch))
            return false;
        return divideConquer(root.children.get(ch), word, index + 1);
    }
}


class SegmentTreeNode {
    SegmentTreeNode left, right;
    int start, end;
    long sum;
    SegmentTreeNode(int start, int end) {
        this.left = null;
        this.right = null;
        this.start = start;
        this.end = end;
        this.sum = 0;
    }
}

class SegmentTree {
    SegmentTreeNode root;
    SegmentTree(int[] nums) {
        if (nums == null || nums.length == 0) {
            root = null;
        } else {
            root = construct(nums, 0, nums.length - 1);
        }
    }

    private SegmentTreeNode construct(int[] nums, int start, int end) {
        SegmentTreeNode node = new SegmentTreeNode(start, end);
        if (start == end) {
            node.sum = nums[start];
            return node;
        }
        int mid = start + (end - start) / 2;
        node.left = construct(nums, start, mid);
        node.right = construct(nums, mid + 1, end);
        node.sum = node.left.sum + node.right.sum;
        return node;
    }

    public void modify(SegmentTreeNode node, int index, int val) {
        if (node.start == index && node.end == index) {
            node.sum = val;
            return;
        }
        int mid = node.start + (node.end - node.start) / 2;
        if (index <= mid) {
            modify(node.left, index, val);
        } else {
            modify(node.right, index, val);
        }
        node.sum = node.left.sum + node.right.sum;
    }

    public long query(SegmentTreeNode node, int start, int end) {
        if (node.start == start && node.end == end) {
            return node.sum;
        }
        int mid = node.start + (node.end - node.start) / 2;
        if (end <= mid) {
            return query(node.left, start, end);
        }
        if (start > mid) {
            return query(node.right, start, end);
        }
        return query(node.left, start, mid) + query(node.right, mid + 1, end);
    }

    public void printTree() {
        Queue<SegmentTreeNode> queue = new ArrayDeque<>();
        List<String> strs = new ArrayList<>();
        queue.add(root);
        strs.add(String.valueOf(root.sum));
        while (!queue.isEmpty()) {
            SegmentTreeNode cur = queue.poll();
            if (cur.left != null) {
                queue.offer(cur.left);
                strs.add(String.valueOf(cur.left.sum));
            } else {
                strs.add("#");
            }
            if (cur.right != null) {
                queue.offer(cur.right);
                strs.add(String.valueOf(cur.right.sum));
            } else {
                strs.add("#");
            }
        }
        System.out.println(strs);
    }
}




