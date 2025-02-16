/*
 *
 * Daniel Rubio Camargo - 10408823
 * Gustavo Matta - 10410154
 *
 */

package pack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*
================================================================================
GRAMÁTICA
================================================================================
<data>         ::= ((<scope> | <key> | <comment>)* <blank_line>)*
<scope>        ::= <identifier> (<blank> | <blank_line>)* "(" <blank_line>+ <data>* <blank> ")"
<key>          ::= <identifier> <blank> "=" <blank> <value>
<identifier>   ::= <string>
<value>        ::= <string>
<comment>      ::= "#" <string>

<string>       ::= <char>+
<char>         ::= <basic_latin> | <latin_1_supp> | <whitespace>
<basic_latin>  ::= [\u0020-\u007F]  ; Unicode Basic Latin
<latin_1_supp> ::= [\u00A0-\u00FF]  ; Unicode Latin-1 Supplement

<blank_line>   ::= <blank> <newline>
<blank>        ::= <whitespace>*
<whitespace>   ::= " " | "\t"
<newline>      ::= "\n" | "\r" | "\r\n"
*/

// v erificar os tokens recebidos e mapear para as árvores

public class Parser {

    private List<Token> tokens;
    private Token currToken;
    private int index = 0;
    private Stack<Node> stackBST = new Stack<>();
    private Stack<Node> stackAVL = new Stack<>();
    private Stack<Integer> idStack = new Stack<>(); 
    List<ScopeNode> scopesListBST = new ArrayList<>();
    List<ScopeNode> scopesListAVL = new ArrayList<>();
    private int currScopeId = 1;
    private AVL avl = new AVL();
    private BST bst = new BST();

    public Parser() {
        tokens = null;
        currToken = null;
        index = -1;
    }

    public BST getParsedBST() {
        return bst;
    }

    public AVL getParsedAVL() {
        return avl;
    }
    
    public List<ScopeNode> getScopesBST(){
    	
    	return scopesListBST;
    }
    
    public List<ScopeNode> getScopesAVL(){
    	
    	return scopesListAVL;
    }

    public void run(List<String> contents) {
    	
        Tokenizer tokenizer = new Tokenizer();
        tokens = tokenizer.tokenize(contents);
        currToken = null;
        index = -1;

        // Descomente o código abaixo para ver a lista de tokens gerada pelo Tokenizer.
        /*
        System.out.println("======================== TOKENS ========================");
        for (var token : tokens) {
            System.out.println(token);
        }
        System.out.println("======================== TOKENS ========================\n");
		*/

        parse();

        System.out.println("Parse realizado com sucesso! Nenhum erro encontrado!\n");
    }

    // verifica se a quantidade de nós de ambas as árvores batem
    public void verifyNodeCount() {
    	
        int bstNodeCount = bst.getNodeCount();
        int avlNodeCount = avl.getNodeCount();

        if (bstNodeCount != avlNodeCount) {
            throw new RuntimeException("Erro: A quantidade de nós na BST (" + bstNodeCount + ") e na AVL (" + avlNodeCount + ") não são iguais.");
        } 
    }
    
    private void parse() {
    	
        advance();
        data();
        
        /*
         * teste
        avl.inOrderTraversal();
        System.out.println();
        bst.inOrderTraversal();
        System.out.println("\n");
        */
        
        verifyNodeCount(); 

        if (currToken.getType() != TokenType.EOF) {
            throw new RuntimeException("Parser.parse(): Esperado fim do conteúdo (EOF), mas encontrou " + currToken);
        }
    }

    private void consumeWhitespaces() {
        while (currToken.getType() == TokenType.WHITESPACE) {
            consume(TokenType.WHITESPACE);
        }
    }

    private void consumeNewLines() {
        while (currToken.getType() == TokenType.NEWLINE) {
            consume(TokenType.NEWLINE);
        }
    }

    private void consumeComment() {
    	
        consume(TokenType.COMMENT);
        consumeWhitespaces();
        
        if(currToken.getType() == TokenType.STRING) {
        	consume(TokenType.STRING);
        }
        
        while(true) {
        	
        	consumeWhitespaces();
        	
            if(currToken.getType() == TokenType.EOF || currToken.getType() == TokenType.NEWLINE) {
            	break;
            }
            
        	consume(TokenType.STRING);
        }
        
    }

    private void data() {
    	
        while (currToken.getType() != TokenType.EOF) {
        	
            if (currToken.getType() == TokenType.STRING) {
                String currIdentifier = currToken.getValue();
                consume(TokenType.STRING);
                try {
                    verifyKeyOrScope(currIdentifier);
                } 
                catch (Exception e) {
                    e.printStackTrace();
                }
            } 
            else if (currToken.getType() == TokenType.END_SCOPE) {
                endScope();
            } 
            else if (currToken.getType() == TokenType.COMMENT) {
                consumeComment();
            } 
            else if (currToken.getType() == TokenType.NEWLINE) {
                consumeNewLines();
            } 
            else if (currToken.getType() == TokenType.WHITESPACE) {
                consumeWhitespaces();
            } 
            else {
                break;
            }
        }
    }

    private void key(String currIdentifier) {
    	
        consume(TokenType.KEY);
        consumeWhitespaces();

        String value = currToken.getValue();
        consume(TokenType.STRING);
        consumeWhitespaces();

        KeyNode knBST = new KeyNode(currIdentifier, value);
        KeyNode knAVL = new KeyNode(currIdentifier, value);
        
        bst.insertNode(knBST);
        avl.insertNode(knAVL);
        
        stackBST.push(knBST);
        stackAVL.push(knAVL);
    }

    private void startScope(String currIdentifier) {
    	
    	int s = 0;
    	
    	if(!idStack.isEmpty()) {
    		s = idStack.lastElement();
    	}
    	
        ScopeNode snBST = new ScopeNode(currIdentifier, "(", s);
        ScopeNode snAVL = new ScopeNode(currIdentifier, "(", s);
        
        snBST.setScopeID(currScopeId);
        snAVL.setScopeID(currScopeId);
        idStack.push(currScopeId);
        currScopeId++;
        
        bst.insertNode(snBST);
        avl.insertNode(snAVL);
        
        stackBST.push(snBST);
        stackAVL.push(snAVL);
        
        scopesListBST.add(snBST);
        scopesListAVL.add(snAVL);
        
        consume(TokenType.START_SCOPE);
        consumeNewLines();
        consumeWhitespaces();
    }

    private void endScope() {
    	
        Node auxBST = null;
        Node auxAVL = null;
        
        // BST
        if (!stackBST.isEmpty()) {
            auxBST = stackBST.pop();
        }
        while (auxBST != null && !auxBST.getValue().equals("(") && !stackBST.isEmpty()) {
            auxBST.setScopeID(idStack.lastElement());
            auxBST = stackBST.pop();
        }

        // AVL
        if (!stackAVL.isEmpty()) {
            auxAVL = stackAVL.pop();
        }
        while (auxAVL != null && !auxAVL.getValue().equals("(") && !stackAVL.isEmpty()) {
            auxAVL.setScopeID(idStack.lastElement());
            auxAVL = stackAVL.pop();
        }
        
        idStack.pop();
        
        consume(TokenType.END_SCOPE);
    }

    private void verifyKeyOrScope(String currIdentifier) throws Exception {
    	
        consumeNewLines();
        consumeWhitespaces();

        if (currToken.getType() == TokenType.KEY) {
            key(currIdentifier);
        } 
        else if (currToken.getType() == TokenType.START_SCOPE) {
            try {
                startScope(currIdentifier);
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        } 
        else {
            throw new Exception("String (" + currIdentifier + ") não foi inicializada corretamente!");
        }
    }

    private void advance() {
    	
        ++index;

        if (index >= tokens.size()) {
            throw new RuntimeException("Fim de conteúdo inesperado!");
        }

        currToken = tokens.get(index);
    }

    private void consume(TokenType expected) {
    	
        if (currToken.getType() == expected) {
            advance();
        } 
        else {
            throw new RuntimeException("Parser.consume(): Token incorreto. Esperado: " + expected + ". Obtido: " + currToken);
        }
    }
}
