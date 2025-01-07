package alet.variables;

import java.util.ArrayList;
import java.util.HashMap;

import alet.Aletheia;
import alet.Scope;
import alet.sets.Set;

/**
 * 1) Variables can be used to establish FA quantors.
 * 2) Variables can be volatile, e.g. a auxiliary function in a proof 
 */
public class Variable extends MathObject {
	
	public Variable(String name, Scope scope) {
		super(name, scope);
	}
	public boolean quantor;
	public MathObject parentset;
	
	@Override
	public MathObject deepcopy_and_substitute(HashMap<Variable, Variable> bounded_variables, 
			HashMap<MathObject, MathObject> unbounded_variables, Scope newscope) {
		scope = newscope;
		return this;
		//return new Variable(name, newscope);
	}
	
	@Override
	public String toString() {
		return (Aletheia.verbose_ID_printout) ? name + "{"+ID+"}" : name;
	}
	
}
