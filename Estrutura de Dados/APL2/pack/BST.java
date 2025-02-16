/*
 *
 * Daniel Rubio Camargo - 10408823
 * Gustavo Matta - 10410154
 *
 */

package pack;

import java.util.ArrayList;
import java.util.List;

public class BST extends BinaryTree {

    public BST() {
        super();
    }

    public BST(Node root) {
        super(root);
    }

    public List<Node> search(String identifier) {
        List<Node> results = new ArrayList<>();
        search(this.root, identifier, results);
        return results;
    }

    private void search(Node node, String identifier, List<Node> results) {
    	
        if (node == null) return;

        if (identifier.compareTo(node.getIdentifier()) <= 0) {
            search(node.getLeft(), identifier, results);
        }
        if (identifier.compareTo(node.getIdentifier()) >= 0) {
            search(node.getRight(), identifier, results);
        }
        if (identifier.equals(node.getIdentifier())) {
            results.add(node);
        }
    }

    public void insertNode(Node newNode) {
    	
        if (isEmpty()) {
            root = newNode;
            amountOfNodes++;
        } 
        else {
            root = insertRec(root, newNode);
        }
    }

    protected Node insertRec(Node current, Node newNode) {
    	
        if (current == null) {
            amountOfNodes++;
            return newNode;
        }

        if (newNode.getIdentifier().compareTo(current.getIdentifier()) <= 0) {
            current.setLeft(insertRec(current.getLeft(), newNode));
        } 
        else {
            current.setRight(insertRec(current.getRight(), newNode));
        }

        return current;
    }

    public void remove(String identifier) {
        root = removeRec(root, identifier);
    }

    protected Node removeRec(Node current, String identifier) {
    	
        if (current == null) {
            return null;
        }

        if (identifier.compareTo(current.getIdentifier()) < 0) {
            current.setLeft(removeRec(current.getLeft(), identifier));
        } 
        else if (identifier.compareTo(current.getIdentifier()) > 0) {
            current.setRight(removeRec(current.getRight(), identifier));
        } 
        else {
            if (current.getLeft() == null && current.getRight() == null) {
                amountOfNodes--;
                return null;
            } 
            else if (current.getLeft() == null) {
                amountOfNodes--;
                return current.getRight();
            } 
            else if (current.getRight() == null) {
                amountOfNodes--;
                return current.getLeft();
            } 
            else {
                Node smallestValue = findSmallestValue(current.getRight());
                current.identifier = smallestValue.getIdentifier();
                if (current instanceof KeyNode) {
                    ((KeyNode) current).setValue(((KeyNode) smallestValue).getValue());
                }
                current.setRight(removeRec(current.getRight(), smallestValue.getIdentifier()));
            }
        }

        return current;
    }

    private Node findSmallestValue(Node root) {
        return root.getLeft() == null ? root : findSmallestValue(root.getLeft());
    }
}
