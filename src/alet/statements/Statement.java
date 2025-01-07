package alet.statements;

import java.util.ArrayList;
import java.util.HashMap;

import alet.LogicException;
import alet.Scope;
import alet.variables.MathObject;
import alet.variables.Variable;

public abstract class Statement {
//	ArrayList<Variable> variables;
	public int truthstate = STATE_UNPROVEN;
	public boolean is_assumed = false;
	public Scope scope;
	
	public static final int STATE_UNPROVEN = 0;
	//public static final int STATE_ASSUMED = 1;
	public static final int STATE_PROVEN = 2;
	
	public ArrayList<Statement> assumed_statements = new ArrayList<Statement>();
	
	public static int global_id = 0;
	public int ID;
	
	//abstract public void prove() throws LogicException;
	
	/**
	 * some statements can be abstract like in a definition or proposition. When its invoked or 
	 * you try to prove it you need to substitute the real variables.
	 * @param newVars
	 */
	abstract public Statement deepcopy_and_substitute(HashMap<Variable, Variable> bounded_variables, 
			HashMap<MathObject, MathObject> unbounded_variables, Scope newscope) throws LogicException;
	
	public Statement deepcopy_and_substitute(HashMap<MathObject, MathObject> unbounded_variables, Scope newscope) throws LogicException{
		return deepcopy_and_substitute(new HashMap<Variable, Variable>(), unbounded_variables, newscope);
	}
	
	public Statement deepcopy_and_substitute(HashMap<MathObject, MathObject> unbounded_variables) throws LogicException{
		return deepcopy_and_substitute(unbounded_variables, scope);
	}
	
	public Statement deepcopy_and_substitute() throws LogicException{
		return deepcopy_and_substitute(new HashMap<MathObject, MathObject>());
	}
	
//	abstract public Statement deepcopy(HashMap<Variable, Variable> variables);
//	public Statement deepcopy() {
//		return deepcopy(new HashMap<Variable, Variable>());
//	}
	
	public Statement(Scope scope) {
		global_id += 1;
		ID = global_id;
		this.scope = scope;
	}
	
	/*
	 * Get truth state
	 */
	public String gts() {
		if (truthstate == STATE_PROVEN) {
			if (is_assumed)
				return "PROVEN+ASSUMED";
			return "PROVEN";
		}else {
			if (is_assumed)
				return "ASSUMED";
			return "UNPROVEN";
		}

	}
	
	public void assume() {
		//truthstate = STATE_ASSUMED;
		is_assumed = true;
		System.out.println(String.format("I assume %s", this));
	}
	
	public void setToProved() {
		truthstate = STATE_PROVEN;
	}
	
	static public boolean assumptionScopeProvableFrom(Statement source, Statement target) {
		// TODO: muss source wirklich assumed sein, wäre proved nicht auch okay? JO
		// TODO: rework this, ich meine die scopes sind jetzt ja ganz anders
		for (Statement assumption : target.assumed_statements) {
			if (assumption != source && !source.assumed_statements.contains(assumption)) {
				return false;
			}
		}
		return source.is_assumed || target.truthstate == STATE_PROVEN;
	}
	
	public void copy_assumptions(Statement proved_me) {
		// TODO: wahrscheinlich habne noch nicht alle statements copy assumption in prove drinne
		for (Statement stat : proved_me.assumed_statements) {
			if (!assumed_statements.contains(stat))
				assumed_statements.add(stat);
		}
		if (proved_me.is_assumed)
			assumed_statements.add(proved_me);

	}

}
