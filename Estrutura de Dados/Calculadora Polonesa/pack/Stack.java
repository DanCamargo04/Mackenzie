package pack;

public class Stack {

    private static final int MAX_SIZE = 20;
    private int stackTop;
    private char elementVector[];

    public Stack(int size) { 
        this.elementVector = new char[size];
        this.stackTop = -1;
    }

    public Stack() {
        this(MAX_SIZE);
    }

    public boolean isEmpty() {
        return this.stackTop == -1;
    }

    public boolean isFull() {
        return this.stackTop == this.elementVector.length - 1;
    }

    public void push(char e) {
        if (!this.isFull())
            this.elementVector[++this.stackTop] = e;
        else
            System.out.println("ERRO - Pilha cheia!");
    }

    public char pop() {
        if (!this.isEmpty())
            return this.elementVector[this.stackTop--];
        else {
            System.out.println("ERRO - Pilha vazia!");
            return '\0';
        }
    }

    public char top() {
        if (!this.isEmpty())
            return this.elementVector[this.stackTop];
        else {
            System.out.println("ERRO - Pilha vazia!");
            return '\0';
        }
    }

    public int sizeElements() {
        return stackTop + 1;
    }
}

//Referencias utilizadas: slides do moodle.