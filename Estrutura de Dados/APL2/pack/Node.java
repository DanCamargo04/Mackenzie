/*
 *
 * Daniel Rubio Camargo - 10408823
 * Gustavo Matta - 10410154
 *
 */

package pack;

public class Node {

	protected String identifier;
	protected int scopeID = 0;
	protected String value;
	protected NodeType type;
    protected Node parent;
    protected Node left;
    protected Node right;
    protected int balanceFactor = 0;

    public Node() {
        this(0, null);
    }

    public Node(int data) {
        this(data, null);
    }

    public Node(int data, Node parent) {
        this.parent = parent;
        this.left = null;
        this.right = null;
    }
    
    public String getIdentifier() {
		return identifier;
	}
    
    public int getScopeID() {
    	return this.scopeID;
    }
    
    public void setScopeID(int newScopeID) {
    	this.scopeID = newScopeID;
    }
    
    public String getValue() {
    	return this.value;
    }
    
    public NodeType getType() {
    	return this.type;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
    	
        this.left = left;

        if (this.left != null) {
            this.left.setParent(this);
        }
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
    	
        this.right = right;

        if (this.right != null) {
            this.right.setParent(this);
        }
    }

    public boolean hasLeftChild() {
        return left != null;
    }

    public boolean hasRightChild() {
        return right != null;
    }

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }
    
    // para árvores AVL
    public int getBalanceFactor() {
    	updateBalanceFactor();
    	return this.balanceFactor;
    }
    
    protected void updateBalanceFactor() {
        
    	if (isLeaf()) this.balanceFactor = 0;

        else {
        	
            int leftHeight = -1;
            int rightHeight = -1;
            
            if (this.left != null) leftHeight = this.left.getHeight();
            if (this.right != null) rightHeight = this.right.getHeight();
            
            this.balanceFactor = rightHeight - leftHeight;
        }
    }

    public int getDegree() {
    	
        int degree = 0;

        if (hasLeftChild()) {
            ++degree;
        }

        if (hasRightChild()) {
            ++degree;
        }

        return degree;
    }

    public int getLevel() {
    	
        if (isRoot()) {
            return 0;
        }

        return parent.getLevel() + 1;
    }

    public int getHeight() {
    	
        if (isLeaf()) {
            return 0;
        }

        int height = 0;

        if (hasLeftChild()) {
            height = Math.max(height, left.getHeight());
        }

        if (hasRightChild()) {
            height = Math.max(height, right.getHeight());
        }

        return height + 1;
    }

}