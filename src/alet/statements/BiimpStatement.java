package alet.statements;

import java.util.HashMap;
import java.util.Objects;

import alet.LogicException;
import alet.Scope;
import alet.variables.MathObject;
import alet.variables.Variable;

public class BiimpStatement extends Statement {

	Statement statement1;
	Statement statement2;
	
	public BiimpStatement(Statement statement1, Statement statement2, Scope scope) {
		super(scope);
		this.statement1 = statement1;
		this.statement2 = statement2;
	}
	
	public boolean prove() throws LogicException{
		// TODO
		if (statement1.truthstate == STATE_PROVEN && statement2.truthstate == STATE_UNPROVEN)
			throw new LogicException();
		setToProved();
		copy_assumptions(statement1);
		copy_assumptions(statement2);
	}
	
//	@Override
//	public void substitute(HashMap<MathObject, MathObject> newVars) {
//		statement1.substitute(newVars);
//		statement2.substitute(newVars);	
//	}
//	
//	@Override
//	public Statement deepcopy() {
//		return new BiimpStatement(statement1, statement2);
//	}
	
	@Override
	public Statement deepcopy_and_substitute(HashMap<Variable, Variable> bounded_variables,
			HashMap<MathObject, MathObject> unbounded_variables, Scope newscope) throws LogicException{
		return new BiimpStatement(statement1.deepcopy_and_substitute(bounded_variables, unbounded_variables, newscope), 
				statement2.deepcopy_and_substitute(bounded_variables, unbounded_variables, newscope), newscope);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BiimpStatement) {
			BiimpStatement obj_ = (BiimpStatement) obj;
			return statement1.equals(obj_.statement1) && statement2.equals(obj_.statement2);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(statement1, statement2);
	}
}
