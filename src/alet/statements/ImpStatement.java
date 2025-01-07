package alet.statements;

import java.util.HashMap;
import java.util.Objects;

import alet.LogicException;
import alet.Scope;
import alet.Scopebrick;
import alet.variables.MathObject;
import alet.variables.Variable;

public class ImpStatement extends Statement {

	Statement left;
	Statement right;
	
	public ImpStatement(Statement statement1, Statement statement2, Scope scope) {
		super(scope);
		setData(statement1, statement2);
	}
	
	public ImpStatement(Scope scope) {
		super(null);
		this.scope = scope.createChild(new Scopebrick(this));
	}
	
	public void setData(Statement statement1, Statement statement2) {
		this.left = statement1;
		this.right = statement2;
	}
	
	
	public void prove() throws LogicException {
		
		if (Statement.assumptionScopeProvableFrom(left, right)) {
			// copy all assumptions from right to the imp statement
			for (Statement stat : right.assumed_statements) {
				if (stat != left)
					assumed_statements.add(stat);
			}
			setToProved();
		}else
			throw new LogicException();

	}
	
	public Statement infer() throws LogicException{
		if (truthstate == STATE_UNPROVEN)
			throw new LogicException();
		
		if (left.truthstate != STATE_UNPROVEN) {
			return right;
		}else
			throw new LogicException();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ImpStatement) {
			ImpStatement obj_ = (ImpStatement) obj;
			return left.equals(obj_.left) && right.equals(obj_.right);
		}
		return false;
	}
	
//	@Override
//	public void substitute(HashMap<MathObject, MathObject> newVars) {
//		left.substitute(newVars);
//		right.substitute(newVars);	
//	}
//	
//	@Override
//	public Statement deepcopy(HashMap<Variable, Variable> variables) {
//		return new ImpStatement(right.deepcopy(variables), left.deepcopy(variables));
//	}
	
	@Override
	public Statement deepcopy_and_substitute(HashMap<Variable, Variable> bounded_variables,
			HashMap<MathObject, MathObject> unbounded_variables, Scope newscope) throws LogicException{
		return new ImpStatement(right.deepcopy_and_substitute(bounded_variables, unbounded_variables, newscope), 
				left.deepcopy_and_substitute(bounded_variables, unbounded_variables, newscope), newscope);
	}
	
	@Override
	public String toString() {
		return String.format("[%s => %s]", left, right);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(left, right);
	}
}
