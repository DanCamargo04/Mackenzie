/*
 *
 * Daniel Rubio Camargo - 10408823
 * Gustavo Matta - 10410154
 *
 */

package pack;

public class AVL extends BST {

    public AVL() {
        super();
    }

    public AVL(Node root) {
        super(root);
    }

    private void rotateLeft(Node currentNode) {
    	
        if (currentNode == null) return;

        Node newSubtreeRoot = currentNode.getRight();
        if (newSubtreeRoot == null) return;

        currentNode.setRight(newSubtreeRoot.getLeft());
        if (newSubtreeRoot.getLeft() != null) {
            newSubtreeRoot.getLeft().setParent(currentNode);
        }

        newSubtreeRoot.setParent(currentNode.getParent());

        if (currentNode.isRoot()) {
            this.root = newSubtreeRoot;
        } 
        else if (currentNode.getParent().getLeft() == currentNode) {
            currentNode.getParent().setLeft(newSubtreeRoot);
        } 
        else {
            currentNode.getParent().setRight(newSubtreeRoot);
        }

        newSubtreeRoot.setLeft(currentNode);
        currentNode.setParent(newSubtreeRoot);

        currentNode.updateBalanceFactor();
        newSubtreeRoot.updateBalanceFactor();
    }

    private void rotateRight(Node currentNode) {
    	
        if (currentNode == null) return;

        Node newSubtreeRoot = currentNode.getLeft();
        if (newSubtreeRoot == null) return;

        currentNode.setLeft(newSubtreeRoot.getRight());
        
        if (newSubtreeRoot.getRight() != null) {
            newSubtreeRoot.getRight().setParent(currentNode);
        }

        newSubtreeRoot.setParent(currentNode.getParent());

        if (currentNode.isRoot()) {
            this.root = newSubtreeRoot;
        } 
        else if (currentNode.getParent().getRight() == currentNode) {
            currentNode.getParent().setRight(newSubtreeRoot);
        } 
        else {
            currentNode.getParent().setLeft(newSubtreeRoot);
        }

        newSubtreeRoot.setRight(currentNode);
        currentNode.setParent(newSubtreeRoot);

        currentNode.updateBalanceFactor();
        newSubtreeRoot.updateBalanceFactor();
    }

    private void rotateLeftRight(Node currentNode) {
    	
        if (currentNode == null || currentNode.getLeft() == null) return;

        rotateLeft(currentNode.getLeft());
        rotateRight(currentNode);
    }

    private void rotateRightLeft(Node currentNode) {
    	
        if (currentNode == null || currentNode.getRight() == null) return;

        rotateRight(currentNode.getRight());
        rotateLeft(currentNode);
    }

    private void balanceTree(Node currentNode) {
    	
        if (currentNode == null) return;

        int bf = currentNode.getBalanceFactor();

        if (bf > 1) {
            if (currentNode.getRight().getBalanceFactor() < 0) {
                rotateRightLeft(currentNode);
            } 
            else {
                rotateLeft(currentNode);
            }
        } 
        else if (bf < -1) {
            if (currentNode.getLeft().getBalanceFactor() > 0) {
                rotateLeftRight(currentNode);
            } 
            else {
                rotateRight(currentNode);
            }
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

        balanceTree(root);
    }

    protected Node insertRec(Node current, Node newNode) {
    	
        if (current == null) {
            amountOfNodes++;
            return newNode;
        }

        // Permitindo duplicação: nós iguais vão para a direita
        if (newNode.getIdentifier().compareTo(current.getIdentifier()) <= 0) {
            current.setLeft(insertRec(current.getLeft(), newNode));
        } 
        else {
            current.setRight(insertRec(current.getRight(), newNode));
        }

        current.updateBalanceFactor();
        return current;
    }

    public void remove(String identifier) {
        root = removeRec(root, identifier);
        balanceTree(root);
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

        current.updateBalanceFactor();
        return current;
    }

    private Node findSmallestValue(Node root) {
        return root.getLeft() == null ? root : findSmallestValue(root.getLeft());
    }
}
