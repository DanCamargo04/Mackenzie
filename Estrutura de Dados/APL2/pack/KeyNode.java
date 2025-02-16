/*
 *
 * Daniel Rubio Camargo - 10408823
 * Gustavo Matta - 10410154
 *
 */

package pack;

public class KeyNode extends Node{
	
	public KeyNode(String _identifier, String _value) {
		this.identifier = _identifier;
		this.value = _value;
		this.type = NodeType.KEY;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
