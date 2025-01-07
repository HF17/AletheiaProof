package alet.statements;

import java.util.HashMap;
import java.util.Objects;

import alet.LogicException;
import alet.Scope;
import alet.variables.MathObject;
import alet.variables.Variable;

public class NegStatement extends Statement {

	Statement statement;
	
	public NegStatement(Statement statement, Scope scope) {
		super(scope);
		this.statement = statement;
	}
	
	public void prove() throws LogicException{
		if (statement.truthstate == STATE_UNPROVEN)
			throw new LogicException();
		setToProved();
		copy_assumptions(statement);
	}
	
	public Statement infer(MathObject obj) throws LogicException{
		if (truthstate == STATE_UNPROVEN)
			throw new LogicException();
			
	}
	
//	@Override
//	public void substitute(HashMap<MathObject, MathObject> newVars) {
//		statement.substitute(newVars);	
//	}
//	
//	@Override
//	public Statement deepcopy(HashMap<Variable, Variable> variables) {
//		return new NegStatement(statement.deepcopy(variables));
//	}
	
	@Override
	public Statement deepcopy_and_substitute(HashMap<Variable, Variable> bounded_variables,
			HashMap<MathObject, MathObject> unbounded_variables, Scope newscope) throws LogicException{
		return new NegStatement(statement.deepcopy_and_substitute(bounded_variables, unbounded_variables, newscope), newscope);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(statement);
	}
}
