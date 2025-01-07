package alet;

import java.util.ArrayList;
import java.util.HashMap;

import alet.statements.Statement;
import alet.variables.Constant;
import alet.variables.MathObject;


public class Proposition {

	public String name;
	public HashMap<MathObject, Statement> setup_variables; 
	public ArrayList<Constant> setup_constants; 
	public Statement statement;
	public HashMap<String, MathObject> setup_variables_names;
	public Scope scope;
	
	/**
	 * stuff like a and b are coprime can be handled as (a,b) is coprime. The
	 * lexer could turn "a and b are coprime" into check if (a,b) is coprime or something 
	 * @param name
	 * @param setup
	 * @param mainvariable
	 */
//	public Proposition(String name, HashMap<MathObject, Statement> setup_variables, ArrayList<Constant> setup_constants, 
//			Statement statement) {
//		this.name = name;
//		setData(setup_variables, setup_constants, statement);
//	}
	
	public Proposition(String name) {
		this.name = name;
		scope = Scope.getMainScope().createChild(new Scopebrick(this));
	}
	
	public void setData(HashMap<MathObject, Statement> setup_variables, ArrayList<Constant> setup_constants, 
			Statement statement) throws LogicException {
		this.setup_variables = setup_variables;
		this.setup_constants = setup_constants;
		this.statement = statement;
		
		setup_variables_names = new HashMap<String, MathObject>();
		if (setup_variables != null) {
			for (MathObject obj : setup_variables.keySet()) {
				setup_variables_names.put(obj.name, obj);
				scope.addName(obj.name);
			}
		}
	}
	
	public void assumeSetupConditions() {
		if (setup_variables != null) {
			for (MathObject obj: setup_variables.keySet()) {
				Statement statement = setup_variables.get(obj);
				statement.assume();
				obj.properties.add(statement);
			}
		}

	}
	
	public void substitute(HashMap<String, MathObject> currentVariables) {
	}
	
	@Override
	public String toString() {
		String s = String.format("Proposition \"%s\"{\nsetup:%s\nconstants:%s\nstatement:%s\n}", name, setup_variables, setup_constants,
				 statement);
		return s;
	}
	

}
