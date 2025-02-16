/*
 *
 * Daniel Rubio Camargo - 10408823
 * Gustavo Matta - 10410154
 *
 */

package pack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandler {
	
	private String path;
	private boolean found = false;
	
	public FileHandler(String _path) {
		this.path = _path;
	}
	
	public boolean getFound() {
		return this.found;
	}
	
	public List<String> getStringFromFile() {
		
		File file = new File(this.path);
		Scanner scanner = null;
		
		List<String> strings = new ArrayList<>();
		
		try {
			scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				strings.add(scanner.nextLine());
			}
			found = true;
		}
		catch(IOException e) {
			System.out.println("Arquivo não encontrado!\n");
		}
		finally {
			if(scanner != null) {
				scanner.close();
			}
		}
		
		return strings;
	}
	
	public static void saveToFile(String fileName, BinaryTree tree) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            saveNode(writer, tree.root, 0);
        }
    }

    private static void saveNode(BufferedWriter writer, Node node, int level) throws IOException {
        if (node == null) {
            return;
        }

        for (int i = 0; i < level; i++) {
            writer.write("\t");
        }

        if (node.getType() == NodeType.KEY) {
            writer.write(node.getIdentifier() + " = " + node.getValue() + "\n");
        } else {
            writer.write(node.getIdentifier() + " (\n");
            saveNode(writer, node.getLeft(), level + 1);
            saveNode(writer, node.getRight(), level + 1);

            for (int i = 0; i < level; i++) {
                writer.write("\t");
            }

            writer.write(")\n");
        }
    }
}
