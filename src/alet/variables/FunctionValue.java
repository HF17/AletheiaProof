package alet.variables;

import java.util.HashMap;
import java.util.Objects;

import alet.Aletheia;
import alet.Function;
import alet.LogicException;
import alet.Scope;
import alet.statements.Statement;

public class FunctionValue extends MathObject {

	public MathObject function;
	public MathObject object;
	
	public boolean function_is_root;
	
	public FunctionValue(MathObject function, MathObject object) throws LogicException{
		super(function.toString() + " + "+object.toString(), Scope.getSmallestScope(function.scope, object.scope));
		this.function = function;
		this.object = object;
		System.out.println("FUNCTIONVALUE "+this.toString()+" "+scope);
	}
	
	@Override
	public String toString() {
		return (Aletheia.verbose_ID_printout) ? String.format("%s(%s) {%s}", function,object, 
				ID) : String.format("%s(%s)", function,object);
	}
	
//	@Override
//	public void substitute(HashMap<MathObject, MathObject> newVars) {
//		if (newVars.containsKey(object))
//			object = newVars.get(object);
//		else
//			object.substitute(newVars);
//		
//		if (newVars.containsKey(function))
//			function = newVars.get(function);
//		else
//			function.substitute(newVars);
//	}
	
	@Override
	public MathObject deepcopy_and_substitute(HashMap<Variable, Variable> bounded_variables, 
			HashMap<MathObject, MathObject> unbounded_variables, Scope newscope) throws LogicException{
		MathObject newobject = null;
		MathObject newfunction = null;
		
		if (bounded_variables.containsKey(object)) {
			newobject = bounded_variables.get(object);		
		}else {
			if (unbounded_variables.containsKey(object)) {
				newobject = unbounded_variables.get(object);
			}else {
				newobject = object.deepcopy_and_substitute(bounded_variables, unbounded_variables, newscope);
				System.out.println("sys");
			}
		}
		if (bounded_variables.containsKey(function)) {
			newfunction = bounded_variables.get(function);		
		}else {
			if (unbounded_variables.containsKey(function)) {
				newfunction = unbounded_variables.get(function);
			}else {
				newfunction = function.deepcopy_and_substitute(bounded_variables, unbounded_variables, newscope);
			}
		}
		System.out.println("newfunction: "+newfunction);
		System.out.println("newobject: "+newobject);
		return new FunctionValue(newfunction, newobject);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FunctionValue) {
			FunctionValue obj2 = (FunctionValue) obj;
			return name.equals(obj2.name) && scope.equals(obj2.scope) &&
					function.equals(obj2.function) && object.equals(obj2.object);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name, scope, function, object);
	}
	
	
	// f(x)  x could be a variable or g(y), so x is a term. f could also be H(z) or something
	

}
