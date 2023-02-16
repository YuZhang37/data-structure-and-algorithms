package solutions;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

class QuickSort {
	
	/*
	 * https://www.lintcode.com/problem/463/
	 * 
	 * *** Description ****
	 * 
	 * Given an integer array, sort it in ascending order. Use selection
	 * sort, bubble sort, insertion sort or any O(n2) algorithm.
	 * 
	 */
	
	public static void sortIntegers(int[] nums) {
        if (nums == null || nums.length == 0)
            return;
        quickSort(nums, 0, nums.length - 1);
    }
    private static void quickSort(int[] nums, int start, int end) {
        if (start >= end)
            return;
        int midNum = nums[start + (end - start) / 2];
        int left = start, right = end;
        while (left <= right) {
            while (left <= right && nums[left] < midNum) {
                left++;
            }
            while (left <= right && nums[right] > midNum) {
                right--;
            }
            if (left <= right) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++;
                right--;
            }
        }
        quickSort(nums, start, right);
        quickSort(nums, left, end);
    }

}


class QuickSelect {
	
	/*
	 * https://www.lintcode.com/problem/5
	 * 
	 * *** Description ****
	 * 
	 * Find the K-th largest element in an array.
	 * 
	 * 1 ≤ k ≤ 10^5
	 * 
	 */
	
	public static int kthLargestElement(int k, int[] nums) {
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length)
            return Integer.MIN_VALUE;
        return quickSelect(nums, 0, nums.length - 1, k);
    }

    private static int quickSelect(int[] nums, int start, int end, int k) {
        if (start == end)
            return nums[start];
        int midNum = nums[start + (end - start) / 2];
        int left = start, right = end;
        while (left <= right) {
            while (left <= right && nums[left] > midNum)
                left++;
            while (left <= right && nums[right] < midNum)
                right--;
            if (left <= right) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++;
                right--;
            }
        }
        if (k - 1 <= right) {
            return quickSelect(nums, start, right, k);
        } 
        if (k - 1 >= left) {
            return quickSelect(nums, left, end, k);
        }
        return nums[right + 1];
    }
	
}


class TreeNode {
	public int val;
	public TreeNode left, right;
	
	public TreeNode(int val) {
		this.val = val;
		this.left = this.right = null;
	}
}


class BinaryTreeIterativeTraversal {
	
	/* https://www.lintcode.com/problem/66/ */
	
    public static List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null)
            return ans;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode cur = stack.pop();
            ans.add(cur.val);
            if (cur.right != null) {
                stack.push(cur.right);
            }
            if (cur.left != null) {
                stack.push(cur.left);
            }
        }
        return ans;
    }
    
    /* https://www.lintcode.com/problem/67/ */
    
    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null)
            return ans;
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode cur = root;
        while (cur != null) {
            stack.push(cur);
            cur = cur.left;
        }
        while (!stack.isEmpty()) {
            cur = stack.peek();
            ans.add(cur.val);
            if (cur.right != null) {
                cur = cur.right;
                while (cur != null) {
                    stack.push(cur);
                    cur = cur.left;
                }
            } else {
                stack.pop();
                while (!stack.isEmpty() && stack.peek().right == cur) {
                    cur = stack.pop();
                }
            }
        }
        return ans;
    }
    
    
    /* https://www.lintcode.com/problem/68/ */
    
    public static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        TreeNode prev = null, cur = null;
        while (!stack.isEmpty()) {
            cur = stack.peek();
            if (prev == null || prev.left == cur || prev.right == cur) {
                if (cur.left != null) {
                    stack.push(cur.left);
                } else if (cur.right != null) {
                    stack.push(cur.right);
                }
            } else if (cur.left == prev) {
                if (cur.right != null) {
                    stack.push(cur.right);
                }
            } else {
                ans.add(cur.val);
                stack.pop();
            }
            prev = cur;
        }
        return ans;
    }
    
}


class Pair {
    int index;
    TreeNode node;
    Pair(int index, TreeNode node) {
        this.index = index;
        this.node = node;
    }
}


class BinaryTreeSerializer {
	
	/* https://www.lintcode.com/problem/7/ */

	/******** BFS Solution *********/
	
    public static String serializeWithBFS(TreeNode root) {
        if (root == null)
            return "";
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        List<String> strs = new ArrayList<>();
        strs.add(String.valueOf(root.val));
        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            strs.add(cur.left == null ? "#" : String.valueOf(cur.left.val));
            if (cur.left != null) {
                queue.offer(cur.left);
            }
            strs.add(cur.right == null ? "#" : String.valueOf(cur.right.val));
            if (cur.right != null) {
                queue.offer(cur.right);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String s : strs) {
            sb.append(s);
            sb.append(",");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public static TreeNode deserializeWithBFS(String data) {
        if (data.length() == 0)
            return null;
        String[] strs = data.split(",");
        TreeNode root = new TreeNode(Integer.parseInt(strs[0]));
        int index = 1;
        boolean isLeft = true;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode cur = queue.peek();
            TreeNode child = strs[index].equals("#") ? 
                             null : new TreeNode(Integer.parseInt(strs[index]));
            if (isLeft) {
                cur.left = child;
            } else {
                cur.right = child;
                queue.poll();
            }
            if (child != null)
                queue.offer(child);
            isLeft = !isLeft;
            index++;
        }
        return root;
    }
    
    
    /******** DFS Solution *********/
    
    public static String serialize(TreeNode root) {
        List<String> strs = new ArrayList<>();
        serializeWithDivideConquer(root, strs);
        StringBuilder sb = new StringBuilder();
        for (String s : strs) {
            sb.append(s);
            sb.append(",");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
     }

     public static void serializeWithDivideConquer(TreeNode root, List<String> ans) {
         if (root == null) {
             ans.add("#");
             return;
         }
         ans.add(String.valueOf(root.val));
         serializeWithDivideConquer(root.left, ans);
         serializeWithDivideConquer(root.right, ans);
     }

     public static TreeNode deserialize(String data) {
         String[] strs = data.split(",");
         Pair root = deserializeWithDivideConquer(strs, 0);
         return root.node;
     }

     private static Pair deserializeWithDivideConquer(String[] strs, int index) {
         if (strs[index].equals("#"))
             return new Pair(index + 1, null);
         TreeNode cur = new TreeNode(Integer.parseInt(strs[index]));
         Pair left = deserializeWithDivideConquer(strs, index + 1);
         Pair right = deserializeWithDivideConquer(strs, left.index);
         cur.left = left.node;
         cur.right = right.node;
         return new Pair(right.index, cur);
     }
}


class DeletionInBST {
   public static TreeNode removeNode(TreeNode root, int value) {
        if (root == null) {
            return null;
        }
        if (value < root.val) {
            root.left = removeNode(root.left, value);
            return root;
        } 
        if (value > root.val) {
            root.right = removeNode(root.right, value);
            return root;
        }
        if (root.right == null) {
            return root.left;
        }
        if (root.left == null) {
            return root.right;
        }
        TreeNode next = leastNode(root.right);
        if (next == root.right) {
            next.left = root.left;
            return next;
        }
        root.val = next.val;
        root.right = removeNode(root.right, next.val);
        return root;
    }

    private static TreeNode leastNode(TreeNode root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }
}



