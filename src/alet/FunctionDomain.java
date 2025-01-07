package alet;


import alet.variables.MathObject;

public class FunctionDomain extends MathObject {
	
	MathObject function;

	public FunctionDomain(MathObject function) {
		super("FunctionDomain", function.scope);
		this.function = function;
	}
	
	@Override
	public String toString() {
		return String.format("Domain(%s)", function);
	}

}
