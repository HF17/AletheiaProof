package alet;

import alet.sets.Set;
import alet.variables.MathObject;
import alet.variables.Variable;

public class Function extends MathObject{

	Set domain;
	Set codomain;
	/**
	 * Defined by expression tree or by statement (e.g. f maps x to k_x)
	 */
	int assignment_type;
	
	/**
	 * by expression tree
	 */
	public static int DIRECT_FUNCTION = 0;
	/**
	 * by statement
	 */
	public static int INDIRECT_FUNCTION = 1;
	
	public Function(String name, Set domain, Set codomain) {
		super(name);
		this.domain = domain;
		this.codomain = codomain;
	}
	
	public void setExpressionTree() {
		
	}
	
	public void setImplicitMapping() {
			
	}
}
