/*
 *
 * Daniel Rubio Camargo - 10408823
 * Gustavo Matta - 10410154
 *
 */

package pack;

public class ScopeNode extends Node{
	
	private int parentScope;
	
	public ScopeNode(String _identifier, String _value, int _parentScope) {
		this.identifier = _identifier;
		this.value = _value;
		this.type = NodeType.SCOPE;
		this.parentScope = _parentScope;
	}
	
	public int getParentScope() {
		return this.parentScope;
	}
	
}
