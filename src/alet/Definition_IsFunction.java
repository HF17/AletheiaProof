package alet;

import alet.sets.Set;
import alet.variables.MathObject;

public class Definition_IsFunction extends Definition {

	public Definition_IsFunction(MathObject domain, MathObject codomain) { // oder doch Definition_IsFunction(MathObject domain, MathObject codomain) ?
		super("Definition_IsFunction", null, null, null, null);
	}
	
	@Override
	public String toString() {
		return "Definition_IsFunction";
	}

}
