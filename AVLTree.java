/***
 * Brian Leierzapf
 * 6/9/2019
 * CSD335
 *
 * Assignment AVL Tree Implementation
 *
 * The following class is an implementation method for an AVL tree.
 * Lightly based off of some sudo code from ZyBooks AVL tree method.
 * Modified and shorted into a simpler and easier flow to understand.
 * I found the sudo code to be confusing to convert and required much
 * rework and changing around of code to make it practical.
 */

package AVLTreeAssignment;

import java.util.ArrayList;

public class AVLTree {

    // Node constructor
    private Node root;
    private class Node {
        private Node left, right;
        private int height = 1;
        private int value;
        private Node (int val) {
            this.value = val;
        }
    }

    // Return height of nodes, unless node is null
    private int height (Node node) {
        if (node == null) {
            return 0; }
        return node.height;
    }

    // AVLTree constructor
    public AVLTree(){
        root = null;
    }

    // Public method for insert call
    public void insert(int x){
        root = insertNode(root, x);
    }

    // Public method for removeNode call
    public void removeNode(int x){
        root = deleteNode(root, x);
    }

    /* Recursion method of insert. Moves through tree to find empty
     * to find correct available stop to insert new node value.
     */
    private Node insertNode(Node node, int x){
        if (node == null) {
            return new Node(x);
        }

        if (x < node.value) {
            node.left = insertNode(node.left, x);
        }
        else {
            node.right = insertNode(node.right, x);
        }

        // After value is inserted into tree, rebalance is performed.
        node = rebalance(x, node);

        return node;
    }

    /* Method to rebalance tree. This is required to keep the tree as short
     * as possible. Within this method a call to update node height and get
     * balance is performed prior to the rotation.
     */
    private Node rebalance(int x, Node node) {
        // Update node height
        node.height = updateHeight(node);

        // Get tree balance number
        int balance = getBalance(node);

        // Left Rebalance (Zig)
        if (balance > 1) {
            // Left Left Rotation (Zig Zig)
            if(x < node.left.value) {
                return rotateRight(node);
                // Left Right Rotation (Zig Zag)
            } if (x > node.left.value) {
                node.left =  rotateLeft(node.left);
                return rotateRight(node);
            }
        }
        // Right Rebalance (Zag)
        if (balance < -1) {
            // Right Right Rotation (Zag Zag)
            if(x > node.right.value) {
                return rotateLeft(node);
                // Right Left Rotation (Zag Zig)
            } if (x < node.right.value) {
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
        }
        return node;
    }

    // Performs rotation of nodes to rebalance tree
    private Node rotateRight(Node node) {
        // Get values of node children
        Node lNode = node.left;
        Node lrNode = lNode.right;
        // Right rotation performed
        lNode.right = node;
        node.left = lrNode;
        // Update heights for effected nodes
        node.height = updateHeight(node);
        lNode.height = updateHeight(lNode);
        // Return new root
        return lNode;
    }

    // Performs rotation of nodes to rebalance tree
    private Node rotateLeft(Node node) {
        // Get values of node children
        Node rNode = node.right;
        Node rlNode = rNode.left;
        // Left rotation performed
        rNode.left = node;
        node.right = rlNode;
        // Update heights for effected nodes
        node.height = updateHeight(node);
        rNode.height = updateHeight(rNode);
        // Return new root
        return rNode;
    }

    // Updates height of tree by +1 of max height of subtrees
    private int updateHeight(Node node){
        return Math.max(height(node.left), height(node.right)) + 1;
    }

    // Get balance between sides of tree
    private int getBalance(Node node) {
        if (node == null) { return 0; }
        return height(node.left) - height(node.right);
    }

    // Process through tree to find value to delete
    private Node deleteNode(Node node, int x) {
        if (node == null) { return null; }

        // Recursion through tree to find node to remove
        if (x < node.value) {
            node.left = deleteNode(node.left, x);
        } else if(x > node.value) {
            node.right = deleteNode(node.right, x);
        } else {
            if((node.left == null) || (node.right == null)) {
                Node tempNode = null;

                // Checks if either child nodes are null
                if (node.left != null) {
                    tempNode = node.left;
                } else if (node.right != null) {
                    tempNode = node.right;
                }

                // Set tempNode to leaf node or to null if no child nodes
                if(tempNode != null) {
                    node = tempNode;
                } else { node = null; }

            } else {
                /* To be removed node has 2 children. Removed node replaced
                 * with lowest value from the right subtree.
                 */
                Node minNode = minRightChild(node.right);
                // Moves lowest value to node position
                node.value = minNode.value;
                // Delete node that was moved
                node.right = deleteNode(node.right, minNode.value);
            }
        }

        // If tree now only has 1 value return it.
        if (node == null) { return null; }

        // Rebalance tree after removal of node
        node = rebalance(x, node);

        return node;
    }

    // Finds min child of right tree (left most of right tree)
    private Node minRightChild(Node rightNode) {
        Node cur = rightNode;
        while (cur.left != null) {
            cur = cur.left;
        }
        return cur;
    }

    /**
     * Following methods are for the purpose of printing out BST
     * Not part of the assignment but used for testing.
     */

    /* This method returns the Height of the binary search tree. A tree's height
     * is the largest depth of any node. A tree with just one node has height 0.
     */
    public int getHeight() {
        int h = 0;
        ArrayList<Integer> hList = new ArrayList<>();
        hList.add(h);
        hList = treeHeight(hList, h, root);
        for(int i = 0; i < hList.size(); i++){
            if(hList.get(i)>h){
                h = hList.get(i);
            }
        }
        return h;
    }

    private ArrayList<Integer> treeHeight(ArrayList<Integer> hList, int h,  Node bstree){
        if(bstree != null){
            if(hList.get(0) < h){
                hList.add(h);
            }
            h++;
            if (bstree.left != null) {
                treeHeight(hList, h, bstree.left);
            }
            if (bstree.right != null) {
                treeHeight(hList, h, bstree.right);
            }
        }
        return hList;
    }

    /* This method returns an array of node values of all of the nodes at the
     * level specified by the method parameter, level. All nodes at the same
     * depth form a tree Level.
     */
    private int fillArrPos;

    public int[] getNodesAtLevel(int level){
        return createNodeArray(level);
    }

    private int[] createNodeArray(int level){
        //Create array size of max number of results at level in binary tree.
        int valArr[] = new int[((int) Math.pow(2, level))];
        fillArrPos = 0;

        //Fill array with -1 values. If -1 shows in result the node was null.
        for (int i = 0; i < valArr.length; i++){
            valArr[i] = -1;
        }

        if(root != null) {
            valArr = nodesAtLevel(valArr, level, 0, root);
        }
        return valArr;
    }

    private int[] nodesAtLevel(int[] valArr, int returnLevel, int depth, Node bstree){
        if(returnLevel == 0){
            valArr[0] = bstree.value;
            return valArr;
        }

        if(depth + 1 == returnLevel){
            if(bstree.left != null){
                valArr[fillArrPos] = bstree.left.value;
            }
            if(bstree.right != null){
                valArr[fillArrPos+1] = bstree.right.value;
            }
            fillArrPos = fillArrPos + 2;
            return valArr;
        }else {
            if (bstree.left != null) {
                nodesAtLevel(valArr, returnLevel, depth + 1, bstree.left);
            }
            if (bstree.right != null) {
                nodesAtLevel(valArr, returnLevel, depth + 1, bstree.right);
            }
        }

        return valArr;
    }
}
