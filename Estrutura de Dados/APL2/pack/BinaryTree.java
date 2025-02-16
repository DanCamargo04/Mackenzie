/*
 *
 * Daniel Rubio Camargo - 10408823
 * Gustavo Matta - 10410154
 *
 */

package pack;

public class BinaryTree {

    protected Node root;
    protected int amountOfNodes;

    public BinaryTree() {
        this(null);
        this.amountOfNodes = 0;
    }

    public BinaryTree(Node root) {
        this.root = root;
        this.amountOfNodes = 0;
    }
    
    public int getNodeCount() {
        return amountOfNodes;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int getDegree() {
        return getDegree(root);
    }
    
    private int getDegree(Node node) {
    	
        if (node == null || node.isLeaf()) {
            return 0;
        }

        int degree = node.getDegree();

        if (node.hasLeftChild()) {
            degree = Math.max(degree, getDegree(node.getLeft()));
        }

        if (node.hasRightChild()) {
            degree = Math.max(degree, getDegree(node.getRight()));
        }

        return degree;
        
    }

    public int getHeight() {
    	
        if (isEmpty()) {
            return -1;
        }

        return root.getHeight();
        
    }

    // LNR
    public void inOrderTraversal() {
    	inOrderTraversal(this.root, true);
    }
    
    private void inOrderTraversal(Node n, boolean canPrint) {
    	
    	if(n == null) return;
    	
    	if(n.getLeft() != null) {
    		inOrderTraversal(n.getLeft(), canPrint);
    	}
    	if(canPrint) {
        	System.out.print(n.getIdentifier() + " ");
        }
        if(n.getRight() != null) {
        	inOrderTraversal(n.getRight(), canPrint);
        }
        
    }

    // NLR
    public void preOrderTraversal() {
    	preOrderTraversal(this.root, true);
    }
    
    private void preOrderTraversal(Node n, boolean canPrint) {

    	if(n == null) return;
    	
    	if(canPrint) {
        	System.out.print(n.getIdentifier() + " ");
        }
    	if(n.getLeft() != null) {
    		preOrderTraversal(n.getLeft(), canPrint);
    	}
        if(n.getRight() != null) {
        	preOrderTraversal(n.getRight(), canPrint);
        }
        
    }

    // LRN
    public void postOrderTraversal() {
    	postOrderTraversal(this.root, true);
    }
    
    private void postOrderTraversal(Node n, boolean canPrint) {
    	
    	if(n == null) return;
    	
        if(n.getLeft() != null) {
        	postOrderTraversal(n.getLeft(), canPrint);
        }
        if(n.getRight() != null) {
        	postOrderTraversal(n.getRight(), canPrint);
        }
        if(canPrint) {
        	System.out.print(n.getIdentifier() + " ");
        }
        
    }
    
    public void showDetails() {
    	
    	System.out.println("Altura: " + this.getHeight() + "\n"
    					+ "Grau: " + this.getDegree() + "\n"
    					+ "Quantidade de nós: " + this.amountOfNodes + "\n");
    	System.out.print("Percurso em ordem: ");
    	this.inOrderTraversal();
    	System.out.print("\nPercurso pre ordem: ");
    	this.preOrderTraversal();
    	System.out.print("\nPercurso pós ordem: ");
    	this.postOrderTraversal();
    	
    	System.out.println("\n----------------------------------------------------------------------------------------------------------------");
    	System.out.println("Dados dos nós:\n");
    	showDetailsWithScopeId(this.root, true);
    	
    	System.out.println("----------------------------------------------------------------------------------------------------------------\n");
    	
    }
    
    private void showDetailsWithScopeId(Node n, boolean canPrint) {
    	
    	if(n == null) return;
    	
    	if(n.getLeft() != null) {
        	showDetailsWithScopeId(n.getLeft(), canPrint);
        }
        if(n.getRight() != null) {
        	showDetailsWithScopeId(n.getRight(), canPrint);
        }
        if(canPrint) {
        	
        	if(n.getType() == NodeType.KEY) {
        		System.out.print("CHAVE -> " + n.getIdentifier() + " | Valor: " + n.getValue() + " | É raíz: " + n.isRoot() + " | É folha:  " + n.isLeaf() + " | Grau: " + n.getDegree() + " | Altura: " + n.getHeight() + " | Nível: " + n.getLevel() + " | ScopeID: " + n.getScopeID() + "\n");
        	}
        	else {
        		ScopeNode sn = (ScopeNode)n;
        		System.out.print("ESCOPO -> " + sn.getIdentifier() + " | É raíz: " + sn.isRoot() + " | É folha:  " + sn.isLeaf() + " | Grau: " + sn.getDegree() + " | Altura: " + sn.getHeight() + " | Nível: " + sn.getLevel() + " | ScopeID: " + sn.getScopeID() + " | ParentScopeID: " + sn.getParentScope() + "\n");
        	}
        }
    }

}