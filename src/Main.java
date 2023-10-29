import javax.swing.tree.TreeNode;


// DFS top down (pre-order) meaning top -> bottom, L -> R
// O(N) time for worst case of final node in bottom R being visited
// O(N) space for worst case where tree essentially a LL so call stack is the length of the "LL"
class Solution {
    public boolean validate(TreeNode root, Integer low, Integer high) {
        // Empty trees are valid BSTs.
        if (root == null) {
            return true;
        }
        // The current node's value must be between low and high.
        if ((low != null && root.val <= low) || (high != null && root.val >= high)) {
            return false;
        }
        // The left and right subtree must also be valid.
        return validate(root.left, low, root.val) && validate(root.right, root.val, high);
    }

    public boolean isValidBST(TreeNode root) {
        return validate(root, null, null);
    }
}// not in this solution there is only one base case of true and one case of false where false from # being OOB
// is reurned to line 23 which then returns T if both L and R children are valid

/*                  10
                5       15
             3     7   N    13
nodes visited, 10, 5, 3, 7, 15, 13. validating 5 before increasing depth and addressing leftmost: 3

 */



// this solution is hard as fuck. this is DFS in order.
class Solution {
    // We use Integer instead of int as it supports a null value.
    private Integer prev;

    public boolean isValidBST(TreeNode root) {
        prev = null;
        return inorder(root);
    }

    private boolean inorder(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (!inorder(root.left)) {
            return false;
        }
        if (prev != null && root.val <= prev) {
            return false;
        }
        prev = root.val;
        return inorder(root.right);
    }
}
/*                  10
                5       15
             3     7   N    13
nodes validating 3,5,7,10,15,13.  note that call stack becomes increasingly deep in this solution since no
recursive call is popped off until final left node pointing to null basee reached, so has stack frame for
10, 5, 3, null.

this solution is also hard as fuck because it has what i'm going to refer to as a 2-part false return mechanism
where unlike above, line 58 initiates the false return and returns to line 55 if error in L branch or line 61
if in R branch/subtree. then either of those statements again returns false.

say with 15 as current root, explore L find null, all good, add 15 since > 10 lower bound, explore 13.left is
null then to 13 realize nope 13 < 15 no good. so part way through recursive call where 13 is root, says 13 is OOB
and sends back to 15 root and says the inOrder(.right) call is false and returns to 10 root inOrder(.right) call
false again. so we'll have first false scenario when encountering thee bad # then repeated false to inOrder
.left or .right calls all the back down the stack.

 */