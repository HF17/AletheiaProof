package alet.statements;

import java.util.HashMap;
import java.util.Objects;

import alet.Aletheia;
import alet.Equals;
import alet.LogicException;
import alet.Scope;
import alet.variables.FunctionValue;
import alet.variables.MathObject;
import alet.variables.Variable;

public class EqualsStatement extends Statement{

	public MathObject left;
	public MathObject right;
	
	public EqualsStatement(MathObject left, MathObject right, Scope scope) throws LogicException{
		super(Scope.getSmallestScope(scope, left.scope, right.scope));
		this.left = left;
		this.right = right;
	}
	
	public MathObject getOther(MathObject obj) {
		if (obj.equals(left))
			return right;
		return left;
	}
	
	@Override
	public void setToProved() {
		super.setToProved();
		Equals.addEquality(this);
	}
	
	
//	@Override
//	public void substitute(HashMap<MathObject, MathObject> newVars) {
//		
//		if (newVars.containsKey(left)) 
//			left = newVars.get(left);
//		else
//			left.substitute(newVars);
//		
//		if (newVars.containsKey(right)) { 
//			right = newVars.get(right);
//		}else
//			right.substitute(newVars);
//	}
//	
//	@Override
//	public Statement deepcopy(HashMap<Variable, Variable> variables) {
//		MathObject newright = right;
//		MathObject newleft = left;
//		
//		if (variables.containsKey(left)) 
//			newleft = variables.get(left);
//		else if (newleft instanceof FunctionValue)
//			newleft = left.deepcopy(variables);
//		
//		if (variables.containsKey(right)) 
//			newright = variables.get(right);
//		else if (newright instanceof FunctionValue)
//			newright = right.deepcopy(variables);
//		
//		return new EqualsStatement(newleft, newright);
//	}
	
	@Override
	public Statement deepcopy_and_substitute(HashMap<Variable, Variable> bounded_variables, 
			HashMap<MathObject, MathObject> unbounded_variables, Scope newscope) throws LogicException{
		MathObject newleft;
		MathObject newright;
		if (bounded_variables.containsKey(left)) {
			newleft = bounded_variables.get(left);		
		}else {
			if (unbounded_variables.containsKey(left)) {
				newleft = unbounded_variables.get(left);
			}else {
				newleft = left.deepcopy_and_substitute(bounded_variables, unbounded_variables, newscope);
			}
		}
		if (bounded_variables.containsKey(right)) {
			newright = bounded_variables.get(right);		
		}else {
			if (unbounded_variables.containsKey(right)) {
				newright = unbounded_variables.get(right);
			}else {
				newright = right.deepcopy_and_substitute(bounded_variables, unbounded_variables, newscope);
			}
		}
		return new EqualsStatement(newleft, newright, newscope);
	}
	

	@Override
	public String toString() {
		return (Aletheia.verbose_truth_state_printout) ? String.format("%s ={%s} %s", left, gts(), right) 
				: String.format("%s = %s", left, right);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EqualsStatement) {
			EqualsStatement obj_ = (EqualsStatement) obj;
			return left.equals(obj_.left) && right.equals(obj_.right);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(left, right);
	}

}
