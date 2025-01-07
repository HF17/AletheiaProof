package alet;


import alet.variables.MathObject;

public class FunctionCodomain extends MathObject {
	
	MathObject function;

	@Override
	public String toString() {
		return String.format("Codomain(%s)", function);
	}
	
	public FunctionCodomain(MathObject function) {
		super("FunctionCodomain", function.scope);
		this.function = function;
	}
	
	

}
