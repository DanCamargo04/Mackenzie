package pack;

import java.util.Scanner;

public class Main {

    public static boolean check_priority(char operator1, char operator2) { // verificar prioridade dos operadores

        int priority1, priority2;
        
        if (operator1 == '^') {     	
            priority1 = 1;            
        } else if (operator1 == '*' || operator1 == '/') {        	
            priority1 = 2;            
        } else {       	
            priority1 = 3;           
        }
        
        if (operator2 == '^') {      	
            priority2 = 1;            
        } else if (operator2 == '*' || operator2 == '/') {        	
            priority2 = 2;            
        } else {       	
            priority2 = 3;            
        }
        
        if (priority1 <= priority2) {        	
            return true;           
        }
        
        return false;
        
    }

    public static int calculate(int aux1, int aux2, char operacao) { // calcular o valor da expressão
    	
        int result = 0, higher = 0, lower = 0;
        
        if (aux1 > aux2) {       	
            higher = aux1;
            lower = aux2;          
        } else {       	
            lower = aux1;
            higher = aux2;           
        }
        
        switch (operacao) {      
            case '+': result = higher + lower; break;
            case '*': result = higher * lower; break;
            case '-': result = higher - lower; break;
            case '/': result = higher / lower; break;
            case '^': result = higher ^ lower; break;                
        }
        
        return result;
    }
    
    public static void show_menu() { // printar o menu no terminal
    	
    	System.out.print("""
        		Menu:
        		
                1 - Inserir expressão infixa
                2 - Atribuir valores para as variaveis criadas
                3 - Converter expressão infixa para pósfixa
                4 - Calcular resultado da expressão e mostrar valores gerais
                5 - Sair
                """);
    	
    }

    public static void main(String[] args) { // main
    	
        Scanner scanner = new Scanner(System.in);
        
        final int CHARACTERS_SIZE = 26; // numero de letras do alfabeto
        
        int choice = 0; // escolha da opção pelo usuário
        
        Stack signalsStack = new Stack(); // pilha de sinais (+, -, *, /, ^, (, ))
        Stack operationsStack = new Stack(); // pilha de operações
        
        String expPolonesa = new String(); // expressão polonesa
        String finalInfixa = "";
        String infixa;
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVXWYZ";
        String signals = "+-*/^()";
        
        char variablesVet[]; // vetor das variaveis criadas
        int valuesVet[]; // vetor dos valores de cada variavel
        
        variablesVet = new char[CHARACTERS_SIZE];
        valuesVet = new int[CHARACTERS_SIZE];
          
        int num = 0;
        
        for (int i = 0; i < variablesVet.length; i++) {
            variablesVet[i] = ' ';
        }
        
        while (true) {
        	
            show_menu();
            
            System.out.print("\nDigite sua opção (digite na ordem para funcionar) : ");
            
            choice = scanner.nextInt(); // leitura da opção pelo usuário
            
            if (choice == 1) { // inserir a expressão inicial (infixa)
            	
                System.out.print("Digite a expressão infixa: ");
                
                scanner.nextLine();
                
                infixa = scanner.nextLine().toUpperCase();                
                finalInfixa = infixa.replaceAll(" ", "");                
                int contPInitial = 0, contPFinal = 0;
                boolean isWrong = false;
                
                for (int p = 0; p < finalInfixa.length(); p++) {                	
                    if (finalInfixa.charAt(p) == '(') {
                        contPInitial++;
                    } else if (finalInfixa.charAt(p) == ')') {
                        contPFinal++;
                    }                    
                }
                
                for (int p = 0; p < finalInfixa.length(); p++) {                	
                    if (alphabet.indexOf(finalInfixa.charAt(p)) >= 0 || signals.indexOf(finalInfixa.charAt(p)) >= 0) {
                        continue;
                    }
                    
                    isWrong = true;
                    break;                
                }
                
                if (contPInitial != contPFinal) {
                    System.out.println("ERRO! Não há a mesma quantidade de parêntesis!\n\nFim do programa!");
                    break;
                }
                
                else if(isWrong) {
                	System.out.println("ERRO! Há sinais não permitidos na expressão!\n\nFim do programa!");
                	break;
                }
                
                System.out.println("\nExpressão corrigida: " + finalInfixa + "\n");
                
                num = finalInfixa.length();                
                boolean jaTem = false;
                int cont = 0;
       
                for (int i = 0; i < num; i++) {                	
                    if (alphabet.indexOf(finalInfixa.charAt(i)) >= 0) {
                        
                    	jaTem = false;
                        
                        for (int j = 0; j < variablesVet.length; j++) {
                            if (variablesVet[j] == finalInfixa.charAt(i)) {
                                jaTem = true;
                                break;
                            }
                        }
                        
                        if (!jaTem) {
                            variablesVet[cont++] = finalInfixa.charAt(i);
                        }                       
                    }
                }
            } 
            else if (choice == 2) { // atribuir valores para as variáveis criadas
            	
                for (int i = 0; i < variablesVet.length; i++) {                	
                    if (variablesVet[i] == ' ') {
                        break;
                    }
                    
                    System.out.print("\nVariável " + variablesVet[i] + " = ");
                    
                    int valor = scanner.nextInt();
                    valuesVet[i] = valor;                    
                }
                
                System.out.println("");
                
            } 
            else if (choice == 3) { // transformar a expressão inicial em polonesa (pósfixa)
            	
                int c = 0;
                
                for (int i = 0; i < num; i++) {
                	
                    if (alphabet.indexOf(finalInfixa.charAt(i)) >= 0) {
                        expPolonesa += finalInfixa.charAt(i);
                    } else if (finalInfixa.charAt(i) == '(') {
                        signalsStack.push(finalInfixa.charAt(i));
                        c++;
                    } else if (finalInfixa.charAt(i) == ')') {
                        while (signalsStack.top() != '(') {
                            expPolonesa += signalsStack.pop();
                        }
                        signalsStack.pop();
                        c--;
                    } 
                    else {
                        if (!signalsStack.isEmpty()) {
                            if (c == 0 && check_priority(signalsStack.top(), finalInfixa.charAt(i))) {
                                expPolonesa += signalsStack.pop();
                            }
                        }
                        
                        signalsStack.push(finalInfixa.charAt(i));
                    }
                }
                
                while (!signalsStack.isEmpty()) {
                    expPolonesa += signalsStack.pop();
                }

                System.out.println("\nExpressão polonesa: " + expPolonesa + "\n");

            } 
            else if (choice == 4) { // calcular expressão e printar todos os valores anteriores
            	
                int aux1 = 0;
                int aux2 = 0;
                int aux3 = 0;
                
                for (int i = 0; i < expPolonesa.length(); i++) {
                	
                    if (alphabet.indexOf(expPolonesa.charAt(i)) >= 0) {
                        for (int j = 0; j < variablesVet.length; j++) {
                            if (expPolonesa.charAt(i) == variablesVet[j]) {
                                operationsStack.push((char) valuesVet[j]);
                            }
                        }
                    } else {
                        aux1 = (int) operationsStack.pop();
                        aux2 = (int) operationsStack.pop();
                        aux3 = (int) calculate(aux1, aux2, expPolonesa.charAt(i));
                        operationsStack.push((char) aux3);
                    }
                }
                System.out.println("\nExpressão polonesa : " + expPolonesa);
                for (int k = 0; k < variablesVet.length; k++) {
                    if (variablesVet[k] == ' ') {
                        break;
                    }
                    System.out.println("Variavel " + variablesVet[k] + " = " + valuesVet[k]);
                }
                System.out.println("Resultado = " + (int) operationsStack.pop());
                System.out.println();
                
            } 
            else if (choice == 5) { // finalizar programa
                System.out.print("\nFinalizando programa!");
                break;
            }
        }
    }
}

//referencias utilizadas: slides do moodle.
