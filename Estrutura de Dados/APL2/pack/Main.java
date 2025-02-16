/*
 *
 * Daniel Rubio Camargo - 10408823
 * Gustavo Matta - 10410154
 *
 */

package pack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        
        System.out.println("\nPrograma iniciado!\n");
        
        String ed2FilePath;
        
        BST bst = new BST();
        AVL avl = new AVL();
        
        List<ScopeNode> scopesListBST = new ArrayList<>();
        List<ScopeNode> scopesListAVL = new ArrayList<>();
        
        boolean isLoaded = false;
        
        final int MAX_OPTION = 9;
        final int MIN_OPTION = 1;

        // Criação dos scanners no início do método main
        Scanner scanner = new Scanner(System.in);
        Scanner fileScanner = new Scanner(System.in);
        Scanner nodeScanner = new Scanner(System.in);
        Scanner searchScanner = new Scanner(System.in);

        try {
            
            int option = -1;
            
            while(option != 9) {
                
                showMenu();
                option = scanner.nextInt();
                
                if(option > MAX_OPTION || option < MIN_OPTION) {
                    System.out.println("Opção inválida!\n\n");
                    continue;
                }
                
                switch(option) {
                
                case 1: 
                    
                    // receber o caminho do arquivo
                    System.out.println("\nEntre com o caminho do arquivo: ");
                    ed2FilePath = fileScanner.nextLine();
                    
                    // carregar dados de um arquivo ED2
                    FileHandler fileHandler = new FileHandler(ed2FilePath);
                    System.out.println("\nCarregando dados do arquivo \"" + ed2FilePath + "\"\n");
                    
                    // verificar conteúdo do arquivo lido
                    // carregar dados do arquivo para as árvores BST e AVL
                    Parser parser = new Parser();
                    List<String> contentList = fileHandler.getStringFromFile();
                    
                    // caso não exista o arquivo
                    if(!fileHandler.getFound()) {
                        break;
                    }
                    
                    parser.run(contentList);
                    System.out.println("Verificando conteúdo do arquivo \"" + ed2FilePath + "\"\n");
                    System.out.println("Carregando dados do arquivo para as árvores BST e AVL!\n");
                    System.out.println("Verficação e mapeamento concluídos com sucesso! Arquivo apresenta estrutura esperada!\n");
                    
                    bst = parser.getParsedBST();
                    avl = parser.getParsedAVL();
                    
                    scopesListBST = parser.getScopesBST();
                    scopesListAVL = parser.getScopesAVL();
                    
                    isLoaded = true; // conteúdo do arquivo foi mapeado para as árvores e é possível continuar o programa
                    
                    /*
                     * teste
                    System.out.println("\nBST:\n");
                    bst.showDetailsWithScopeId();
                    System.out.println("\nAVL:\n");
                    avl.showDetailsWithScopeId();
                    System.out.println();
                    */
                    
                    break;
                    
                case 2:
                    
                    // buscar uma chave/escopo na árvore
                    
                    if(!isLoaded) {
                        System.out.println("\nNada foi mapeado para as árvores BST e AVL!\n");
                        break;
                    }
                    
                    System.out.println("\nInsira o identificador da chave/escopo: ");
                    String identifier = nodeScanner.nextLine();
                    
                    System.out.println("\nInsira em qual das árvores será a busca:\n\n"
                            + "1 - BST\n"
                            + "2 - AVL\n"
                            + "\nOpção:");
                    
                    int searchOption = searchScanner.nextInt();

                    switch(searchOption) {
                    case 1:
                        
                        List<Node> bstResults = bst.search(identifier);
                        
                        if(bstResults.isEmpty()) {
                            System.out.println("Nenhum nó encontrado na BST com o identificador: " + identifier + "\n");
                        } 
                        else {
                            System.out.println("Nós encontrados na BST:\n");
                            for (Node node : bstResults) {
                            	String scopeIdentifier = "";
                            	if(node.getScopeID() == 0) {
                            		scopeIdentifier = "GLOBAL";
                            	}
                            	for (ScopeNode sn : scopesListBST) {
                            		if(node.getType() == NodeType.KEY) {
                            			if(sn.scopeID == node.scopeID) {
                                			scopeIdentifier = sn.getIdentifier(); 
                                			break;
                                		}
                            		}
                            		else {
                            			ScopeNode aux = (ScopeNode)node;
                            			if(aux.getParentScope() == 0) {
                                    		scopeIdentifier = "GLOBAL";
                                    	}
                            			if(sn.getScopeID() == aux.getParentScope()) {
                                			scopeIdentifier = sn.getIdentifier(); 
                                			break;
                                		}
                            		}
                            	}
                                if(node.getType() == NodeType.KEY) {
                                	System.out.println("-> Chave (" + node.getIdentifier() + ") com valor (" + node.getValue() + ") no escopo (" + scopeIdentifier + ")\n");
                                }
                                else {
                                	System.out.println("-> Escopo (" + node.getIdentifier() + ") no escopo (" + scopeIdentifier + ")\n");
                                }
                            }
                        }
                        
                        break;
                        
                    case 2:

                        List<Node> avlResults = avl.search(identifier);
                        
                        if(avlResults.isEmpty()) {
                            System.out.println("Nenhum nó encontrado na AVL com o identificador: " + identifier + "\n");
                        } 
                        else {
                            System.out.println("Nós encontrados na AVL:\n");
                            for (Node node : avlResults) {
                            	String scopeIdentifier = "";
                            	if(node.getScopeID() == 0) {
                            		scopeIdentifier = "GLOBAL";
                            	}
                            	for (ScopeNode sn : scopesListAVL) {
                            		if(node.getType() == NodeType.KEY) {
                            			if(sn.scopeID == node.scopeID) {
                                			scopeIdentifier = sn.getIdentifier(); 
                                			break;
                                		}
                            		}
                            		else {
                            			ScopeNode aux = (ScopeNode)node;
                            			if(aux.getParentScope() == 0) {
                                    		scopeIdentifier = "GLOBAL";
                                    	}
                            			if(sn.getScopeID() == aux.getParentScope()) {
                                			scopeIdentifier = sn.getIdentifier(); 
                                			break;
                                		}
                            		}
                            	}
                            	if(node.getType() == NodeType.KEY) {
                                	System.out.println("-> Chave (" + node.getIdentifier() + ") com valor (" + node.getValue() + ") no escopo (" + scopeIdentifier + ")\n");
                                }
                                else {
                                	System.out.println("-> Escopo (" + node.getIdentifier() + ") no escopo (" + scopeIdentifier + ")\n");
                                }
                            }
                        }
                        
                        break;
                    
                    default:
                        break;
                    }
                    
                    break;
                    
                case 3:
                    
                    // inserir uma chave/escopo na árvore
                    
                    if(!isLoaded) {
                        System.out.println("\nNada foi mapeado para as árvores BST e AVL!\n");
                        break;
                    }
                    
                    System.out.println("\nInserir:\n1 - Chave\n2 - Escopo");
                    int insertOption = scanner.nextInt();
                    scanner.nextLine();  // Consumir a nova linha pendente
                    
                    System.out.println("\nInsira o identificador da chave/escopo: ");
                    String newIdentifier = scanner.nextLine();
                    
                    System.out.println("Insira o ScopeID do escopo onde será inserido (0 para global): ");
                    int parentScopeID = scanner.nextInt();
                    scanner.nextLine();  // Consumir a nova linha pendente
                    
                    boolean parentExistsBST = parentScopeID == 0 || scopesListBST.stream().anyMatch(scope -> scope.getScopeID() == parentScopeID);
                    boolean parentExistsAVL = parentScopeID == 0 || scopesListAVL.stream().anyMatch(scope -> scope.getScopeID() == parentScopeID);
                    
                    if (!parentExistsBST || !parentExistsAVL) {
                        System.out.println("Erro: ScopeID do escopo pai não existe.\n");
                        break;
                    }
                    
                    if (insertOption == 1) {
                        System.out.println("Insira o valor da chave: ");
                        String newValue = scanner.nextLine();
                        
                        KeyNode newKeyNodeBST = new KeyNode(newIdentifier, newValue);
                        KeyNode newKeyNodeAVL = new KeyNode(newIdentifier, newValue);
                        
                        newKeyNodeBST.setScopeID(parentScopeID);
                        newKeyNodeAVL.setScopeID(parentScopeID);
                        
                        bst.insertNode(newKeyNodeBST);
                        avl.insertNode(newKeyNodeAVL);
                        
                        System.out.println("Chave inserida com sucesso!\n");
                    } 
                    else if (insertOption == 2) {
                        ScopeNode newScopeNodeBST = new ScopeNode(newIdentifier, "(", parentScopeID);
                        ScopeNode newScopeNodeAVL = new ScopeNode(newIdentifier, "(", parentScopeID);
                        
                        newScopeNodeBST.setScopeID(scopesListBST.size() + 1);
                        newScopeNodeAVL.setScopeID(scopesListAVL.size() + 1);
                        
                        bst.insertNode(newScopeNodeBST);
                        avl.insertNode(newScopeNodeAVL);
                        
                        scopesListBST.add(newScopeNodeBST);
                        scopesListAVL.add(newScopeNodeAVL);
                        
                        System.out.println("Escopo inserido com sucesso!\n");
                    } 
                    else {
                        System.out.println("Opção inválida!\n");
                    }
                    
                    break;
                    
                case 4:
                    
                    // alterar uma chave da árvore
                    
                    if(!isLoaded) {
                        System.out.println("\nNada foi mapeado para as árvores BST e AVL!\n");
                        break;
                    }
                    
                    scanner.nextLine();  // Consumir a nova linha pendente
                    System.out.println("\nInsira o identificador da chave: ");
                    String keyToUpdate = scanner.nextLine();
                    
                    System.out.println("Insira o ScopeID do escopo onde a chave está localizada: ");
                    int scopeID = scanner.nextInt();
                    scanner.nextLine();  // Consumir a nova linha pendente
                    
                    List<Node> bstUpdateResults = bst.search(keyToUpdate);
                    List<Node> avlUpdateResults = avl.search(keyToUpdate);           
                    List<Node> bstScopedResults = new ArrayList<>();
                    
                    for (Node node : bstUpdateResults) {
                        if (node.getScopeID() == scopeID && node instanceof KeyNode) {
                            bstScopedResults.add(node);
                        }
                    }
                    
                    List<Node> avlScopedResults = new ArrayList<>();
                    
                    for (Node node : avlUpdateResults) {
                        if (node.getScopeID() == scopeID && node instanceof KeyNode) {
                            avlScopedResults.add(node);
                        }
                    }
                    
                    if (bstScopedResults.isEmpty() || avlScopedResults.isEmpty()) {
                        System.out.println("Nenhuma chave encontrada com o identificador: " + keyToUpdate + " no escopo: " + scopeID + "\n");
                    } 
                    else {
                        System.out.println("Insira o novo valor da chave: ");
                        String newValue = scanner.nextLine();
                        
                        for (Node node : bstScopedResults) {
                            if (node instanceof KeyNode) {
                                ((KeyNode) node).setValue(newValue);
                            }
                        }
                        
                        for (Node node : avlScopedResults) {
                            if (node instanceof KeyNode) {
                                ((KeyNode) node).setValue(newValue);
                            }
                        }
                        
                        System.out.println("Chave atualizada com sucesso!\n");
                    }
                    
                    break;
                    
                case 5:
    
                    // remover uma chave da árvore
    
                    if(!isLoaded) {
                        System.out.println("\nNada foi mapeado para as árvores BST e AVL!\n");
                        break;
                    }
    
                    scanner.nextLine(); 
                    System.out.println("\nInsira o identificador da chave: ");
                    String keyToRemove = scanner.nextLine();
    
                    System.out.println("Insira o ScopeID do escopo onde a chave está localizada: ");
                    int removeScopeID = scanner.nextInt();
                    scanner.nextLine();  
    
                    List<Node> bstRemoveResults = bst.search(keyToRemove);
                    List<Node> avlRemoveResults = avl.search(keyToRemove);
                    List<Node> bstScopedResultsToRemove = new ArrayList<>();
                    
                    for (Node node : bstRemoveResults) {
                        if (node.getScopeID() == removeScopeID && node instanceof KeyNode) {
                            bstScopedResultsToRemove.add(node);
                        }
                    }
    
                    List<Node> avlScopedResultsToRemove = new ArrayList<>();
                    for (Node node : avlRemoveResults) {
                        if (node.getScopeID() == removeScopeID && node instanceof KeyNode) {
                            avlScopedResultsToRemove.add(node);
                        }
                    }
    
                    if (bstScopedResultsToRemove.isEmpty() || avlScopedResultsToRemove.isEmpty()) {
                        System.out.println("Nenhuma chave encontrada com o identificador: " + keyToRemove + " no escopo: " + removeScopeID + "\n");
                    }
                    else {
                        for (Node node : bstScopedResultsToRemove) {
                            bst.remove(node.getIdentifier());
                        }
    
                        for (Node node : avlScopedResultsToRemove) {
                            avl.remove(node.getIdentifier());
                        }
    
                        System.out.println("Chave removida com sucesso!\n");
                    }
    
                    break;
                    
                case 6:
                    
                    // salvar dados para um arquivo
                    
                    if(!isLoaded) {
                        System.out.println("\nNada foi mapeado para as árvores BST e AVL!\n");
                        break;
                    }
                    
                    scanner.nextLine(); 
                    System.out.println("\nInsira o nome do arquivo para salvar os dados: ");
                    String saveFilePath = scanner.nextLine();
                    
                    try {
                        File saveFile = new File(saveFilePath);
                        
                        if (saveFile.exists()) {
                            System.out.println("Arquivo já existe. Deseja sobrescrever? (s/n)");
                            String overwriteOption = scanner.nextLine();
                            
                            if (!overwriteOption.equalsIgnoreCase("s")) {
                                System.out.println("Operação de salvamento cancelada.\n");
                                break;
                            }
                        }
                        
                        FileHandler.saveToFile(saveFilePath, bst); 
                        
                        System.out.println("Dados salvos com sucesso no arquivo: " + saveFilePath + "\n");
                        
                    } catch (IOException e) {
                        System.out.println("Erro ao salvar dados no arquivo: " + e.getMessage() + "\n");
                    }
                    
                    break;

                    
                case 7:
                    
                    // exibir o conteúdo e as propriedades da árvore BST
                    
                    if(!isLoaded) {
                        System.out.println("\nNada foi mapeado para as árvores BST e AVL!\n");
                        break;
                    }
                    
                    System.out.println("\n----------------------------------------------------------------------------------------------------------------");
                    System.out.println("Conteúdo e propriedades da árvore BST:\n");
                    
                    bst.showDetails();
                    
                    break;
                    
                case 8:
                    
                    // exibir o conteúdo e as propriedades da árvore AVL
                    
                    if(!isLoaded) {
                        System.out.println("Nada foi mapeado para as árvores BST e AVL!\n");
                        break;
                    }
                    
                    System.out.println("\n----------------------------------------------------------------------------------------------------------------");
                    System.out.println("Conteúdo e propriedades da árvore AVL:\n");
                    
                    avl.showDetails();
                    
                    break;
                
                }
                
            }
        } 
        
        finally {
            scanner.close();
            fileScanner.close();
            nodeScanner.close();
            searchScanner.close();
        }
        
        System.out.println("\nFinalizando o programa...");

    }
    
    public static void showMenu() {
        
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=- Menu -=-=-=-=-=-=-=-=-=-=-=-=-\n\n"
                + "1. Carregar dados de um arquivo ED2\r\n"
                + "2. Buscar uma chave/escopo na árvore\r\n"
                + "3. Inserir uma chave/escopo na árvore\r\n"
                + "4. Alterar uma chave da árvore\r\n"
                + "5. Remover uma chave da árvore\r\n"
                + "6. Salvar dados para um arquivo\r\n"
                + "7. Exibir o conteúdo e as propriedades da árvore BST\r\n"
                + "8. Exibir o conteúdo e as propriedades da árvore AVL\r\n"
                + "9. Encerrar o programa\n"
                + "\n-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n"
                + "\nInsira sua opção: ");
        
    }

}

// Códigos do professor André Kishimoto utilizados: Parser, Token, Tokenizer, TokenType
// Foram feitas algumas modificações para servirem aos problemas propostos

// Referências: 
// -> Slides do moodle
// -> Vídeos (Youtube):
//		->https://www.youtube.com/watch?v=JPI-DPizQYk
//		->https://www.youtube.com/watch?v=PBkXmhiCP1M
//		->https://www.youtube.com/watch?v=r6vNthpQtSI