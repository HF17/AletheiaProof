package alet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import alet.statements.ForStatement;
import alet.statements.Statement;
import alet.variables.Constant;
import alet.variables.MathObject;
import alet.variables.Variable;

public class Definition {

	public String name;
	public MathObject mainvariable; 
	public Statement definition_condition;
	public Statement mainvariable_condition;
	public HashMap<MathObject, Statement> setup_variables;
	public HashMap<String, MathObject> setup_variables_names;
	Scope scope;
	
	/**
	 * stuff like a and b are coprime can be handled as (a,b) is coprime. The
	 * lexer could turn "a and b are coprime" into check if (a,b) is coprime or something 
	 * @param name
	 * @param setup
	 * @param mainvariable
	 * @throws LogicException 
	 */
	public Definition(String name, HashMap<MathObject, Statement> setup_variables, HashMap<MathObject, Statement> setup_const,  
			MathObject mainvariable, Statement definition_condition, Scope scope) throws LogicException {
		this.name = name;
		this.scope = scope;
		setData(setup_variables, setup_const, mainvariable, definition_condition);
	}
	
	public Definition(String name) {
		this.name = name;
		scope = Scope.getMainScope().createChild(new Scopebrick(this));
	}
	
	public void setData(HashMap<MathObject, Statement> setup_variables, HashMap<MathObject, Statement> setup_const,  
			MathObject mainvariable, Statement definition_condition) throws LogicException {
		this.definition_condition = definition_condition;
		if (setup_variables != null && setup_variables.containsKey(mainvariable))
			this.mainvariable_condition = setup_variables.get(mainvariable);
		this.setup_variables = setup_variables;
		this.mainvariable = mainvariable;
		
		setup_variables_names = new HashMap<String, MathObject>();
		if (setup_variables != null) {
			for (MathObject obj : setup_variables.keySet()) {
				setup_variables_names.put(obj.name, obj);
				scope.addName(obj.name);
			}
		}
	}
	
	// f:A -> B,  f(A) = B  ∀b€B ∃a€A: f(a) = b
	// 1) 	mainvariable: f
	//		setup: A,B
	// 2) ∃ set A ∃ set B : domain(f) = A, codomain(f) = B
	// def f lin. funktion: ∃ set A ∃ set B (A vector space, B vector space, f:A->B, f(x+y) = f(x)+f(y))
	
//	public void substitute(MathObject subs) {
//		HashMap<MathObject, MathObject> subs_single_hashmap = new HashMap<MathObject, MathObject>();
//		subs_single_hashmap.put(mainvariable, subs);
//		definition_condition.substitute(subs_single_hashmap);
//	}
	
	@Override
	public String toString() {
		String s = String.format("Definition \"%s\"", name);
		return s;
	}
	
	public String toVerboseString() {
		String s = String.format("Definition \"%s\"{\nmain variable:%s\nsetup:%s\nstatement:%s\n}", name, mainvariable,setup_variables, definition_condition);
		return s;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Definition) {
			Definition obj_ = (Definition) obj;
			return name.equals(obj_.name) && 
					Objects.equals(definition_condition, obj_.definition_condition) &&
					Objects.equals(mainvariable, obj_.mainvariable) &&
					Objects.equals(mainvariable_condition, obj_.mainvariable_condition);
		}
		return false;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(name, mainvariable, definition_condition, mainvariable_condition);
	}
	
	public Definition copy_and_substitute(HashMap<String, MathObject> setup_variables_remap, Scope newscope) throws LogicException{
		HashMap<MathObject, Statement> new_setup_variables = new HashMap<MathObject, Statement>();
		HashMap<MathObject, MathObject> new_setup_substitute = new HashMap<MathObject, MathObject>();
		
		for (MathObject obj : setup_variables.keySet()) {
			MathObject newobj = setup_variables_remap.get(obj.name);
			new_setup_variables.put(newobj, setup_variables.get(obj));
			new_setup_substitute.put(obj, newobj);
		}
		
		Statement new_stat = definition_condition.deepcopy_and_substitute(new_setup_substitute, newscope);
		MathObject newmainvariable = setup_variables_remap.get(mainvariable.name);
		
		return new Definition(name, new_setup_variables, null, newmainvariable, new_stat, scope);

	}
	

}



//public class Definition {
//
//	String mainVariableName;
//	String name;
//	HashMap<MathObject, Statement> setup_variables; 
//	ArrayList<Constant> setup_constants; 
//	MathObject mainvariable; 
//	Statement definition_condition;
//	HashMap<String, MathObject> setup_variables_name;
//	
//	/**
//	 * stuff like a and b are coprime can be handled as (a,b) is coprime. The
//	 * lexer could turn "a and b are coprime" into check if (a,b) is coprime or something 
//	 * @param name
//	 * @param setup
//	 * @param mainvariable
//	 */
//	public Definition(String name, HashMap<MathObject, Statement> setup_variables, ArrayList<Constant> setup_constants, 
//			MathObject mainvariable, Statement definition_condition) {
//		this.name = name;
//		this.setup_variables = setup_variables;
//		this.setup_constants = setup_constants;
//		this.mainvariable = mainvariable;
//		this.definition_condition = definition_condition;
//		if (mainvariable != null)
//			mainVariableName = mainvariable.name;
//		
//		setup_variables_name = new HashMap<String, MathObject>();
//		for (MathObject obj : setup_variables.keySet()) {
//			setup_variables_name.put(obj.name, obj);
//		}
//	}
//	
//	public Statement substitute(HashMap<String, MathObject> currentVariables) {
//		// TODO Auto-generated method stub
//	}
//	
//	@Override
//	public String toString() {
//		String s = String.format("Definition \"%s\"", name);
//		return s;
//	}
//	
//	public String toVerboseString() {
//		String s = String.format("Definition \"%s\"{\nsetup:%s\nconstants:%s\nmain variable:%s\nstatement:%s\n}", name, setup_variables, setup_constants,
//				mainvariable, definition_condition);
//		return s;
//	}
//	
//
//}

