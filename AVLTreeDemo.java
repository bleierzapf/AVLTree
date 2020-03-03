package AVLTreeAssignment;

import java.util.Arrays;

public class AVLTreeDemo {

    public static void main(String[] args) {
        int h;

        AVLTree bst = new AVLTree();
        bst.insert(28);
        bst.insert(14);
        bst.insert(25);
        bst.insert(85);
        bst.insert(40);
        bst.insert(12);
        bst.insert(10);
        //bst.insert(24);
        bst.insert(8);
        bst.insert(6);

        //print(bst);

        //bst.removeNode(8);
        //bst.removeNode(12);

        //print(bst);

        bst.insert(100);
        bst.insert(34);
        bst.insert(75);
        bst.insert(16);
        bst.insert(42);
        bst.insert(55);
        //print(bst);

        bst.removeNode(85);
        bst.removeNode(40);
        bst.removeNode(55);


        print(bst);

/*
        h = bst.getHeight();
        System.out.println(h);
        System.out.println(Arrays.toString(bst.getNodesAtLevel(0)));
*/
        /*bst.insert(56);
        bst.insert(9);
        bst.insert(45);
        bst.insert(37);
        bst.insert(13);
        bst.insert(91);*/


        /*
        h = bst.getHeight();
        System.out.println(h);
        */

        /*
        for(int i = 0; i <= h; i++){
            System.out.println(Arrays.toString(bst.getNodesAtLevel(i)));
        }
        */

    }

    public static void print(AVLTree bst){
        int height = bst.getHeight();
        System.out.println("**********");
        for(int i = 0; i <= height; i++){
            System.out.println("Level " + i + ": " + Arrays.toString(bst.getNodesAtLevel(i)));
        }
        System.out.println("**********");
    }

}
