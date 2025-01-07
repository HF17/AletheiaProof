package alet.statements;

import java.util.HashMap;
import java.util.Objects;

import alet.Aletheia;
import alet.Definition;
import alet.LogicException;
import alet.PropertyManager;
import alet.Scope;
import alet.variables.FunctionValue;
import alet.variables.MathObject;
import alet.variables.Variable;

public class RelationStatement extends Statement{

	public Definition definition;
	public MathObject object;
	
	public RelationStatement(Definition definition, MathObject object, Scope scope) {
		super(scope);
		this.definition = definition;
		this.object = object;
	}
	
	public void prove(HashMap<String, MathObject> setup_variables_names) throws LogicException{
		HashMap<MathObject, MathObject> subsmap = new HashMap<MathObject, MathObject>();
		subsmap.put(definition.mainvariable, object);
		System.out.println("rel scope: "+scope);
		for (String obj_name : setup_variables_names.keySet()) {
			MathObject new_obj = setup_variables_names.get(obj_name);
			//definition.setup_variables_names.get(obj_name).scope = scope;
			subsmap.put(definition.setup_variables_names.get(obj_name), new_obj);
			System.out.println(String.format("%s -> %s | %s %s", 
					definition.setup_variables_names.get(obj_name), new_obj,
					definition.setup_variables_names.get(obj_name).scope, new_obj.scope));
		}
		
		Statement stat_copy = definition.definition_condition.deepcopy_and_substitute(subsmap, scope);
		
		if (!PropertyManager.hasProperty(object, stat_copy))
			throw new LogicException("RelationStatement cant be proven because object doesnt contain the statement");
		
		setToProved();
		// get the statement from the object. This statement has the same statement structure, but with assumptions!
		scope = 
		Statement stat = object.properties.get(object.properties.lastIndexOf(definition.definition_condition)); 
		copy_assumptions(stat);
	}
	
	public Statement infer() throws LogicException{
		if (truthstate == STATE_PROVEN || is_assumed) {
			//Statement condition = definition.definition_condition.deepcopy();
			Statement condition = definition.definition_condition;
//			HashMap<MathObject, MathObject> subs = new HashMap<MathObject, MathObject>();
			
//			subs.put(definition.mainvariable, object);
//			condition.substitute(subs);
			condition.setToProved();
			condition.copy_assumptions(this);
			return condition;
		}
		throw new LogicException();
		
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RelationStatement) {
			RelationStatement obj_ = (RelationStatement) obj;
			return obj_.definition.equals(definition);
		}
		return false;
	}

	
	
	@Override
	public Statement deepcopy_and_substitute(HashMap<Variable, Variable> bounded_variables,
			HashMap<MathObject, MathObject> unbounded_variables, Scope newscope) throws LogicException {
		// TODO sollte ich bei def auch was substituieren??? zum beispiel das setup?
		// könnte das originale setup unbrachbar werden nachdem ich hier substituiere?
		MathObject newobject;
		if (bounded_variables.containsKey(object)) {
			newobject = bounded_variables.get(object);		
		}else {
			if (unbounded_variables.containsKey(object)) {
				newobject = unbounded_variables.get(object);
			}else {
				newobject = object.deepcopy_and_substitute(bounded_variables, unbounded_variables, newscope);
			}
		}
		return new RelationStatement(definition, newobject, newscope);
	}
	
//	@Override
//	public void substitute(HashMap<MathObject, MathObject> newVars) {
//		if (newVars.containsKey(object)) 
//			object = newVars.get(object);
//		else
//			object.substitute(newVars);
//		
//	}
//	
//	public Statement deepcopy(HashMap<Variable, Variable> variables) {
//		MathObject newobject = object;
//		if (variables.containsKey(object))
//			newobject = variables.get(object);
//		else if (newobject instanceof FunctionValue)
//			newobject = object.deepcopy(variables);
//		return new RelationStatement(definition, newobject);
//	}	
	
	@Override
	public String toString() {
		return (Aletheia.verbose_truth_state_printout) ? String.format("%s ~{%s} %s", object, gts(), definition) : String.format("%s ~ %s", object, definition);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(definition, object);
	}


}
