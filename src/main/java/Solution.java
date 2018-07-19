

import jdk.nashorn.internal.objects.NativeNumber;

import java.util.LinkedList;
import java.util.Queue;

class TreeNode
{
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val)
    {
        this.val = val;

    }

}

/**
 * 思路：二叉树中序遍历
 */
public class Solution
{

    private int k;
    private TreeNode result;

    TreeNode KthNode(TreeNode pRoot, int k)
    {
        this.k = k;
        result = null;
        getResult(pRoot);
        return result;

    }

    void getResult(TreeNode pRoot)
    {
        if (pRoot==null)
        {
            return;
        }

        if (pRoot.left != null)
        {
            getResult(pRoot.left);
        }
        k--;
        if (k==0)
        {
            result=pRoot;
        }
        if (pRoot.right!=null)
        {
            getResult(pRoot.right);
        }
    }


    public static void main(String[] args)
    {
        TreeNode a = new TreeNode(8);
        TreeNode a1 = new TreeNode(6);
        TreeNode a2 = new TreeNode(10);
        TreeNode a3 = new TreeNode(11);
        a.left = a1;
        a.right = a2;
        a1.left = a3;


        Solution solution = new Solution();

    }

}