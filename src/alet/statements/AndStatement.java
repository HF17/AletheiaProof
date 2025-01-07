package alet.statements;

import java.util.HashMap;
import java.util.Objects;

import alet.Aletheia;
import alet.LogicException;
import alet.Scope;
import alet.variables.MathObject;
import alet.variables.Variable;

public class AndStatement extends Statement {

	Statement statement1;
	Statement statement2;
	
	public AndStatement(Statement statement1, Statement statement2, Scope scope) {
		super(scope);
		this.statement1 = statement1;
		this.statement2 = statement2;
	}
	
	public void prove() throws LogicException{
		if (statement1.truthstate == STATE_UNPROVEN || statement2.truthstate == STATE_UNPROVEN)
			throw new LogicException();
		setToProved();
		copy_assumptions(statement1);
		copy_assumptions(statement2);
	}
	
	public Statement infer1() throws LogicException{
		if (truthstate == STATE_UNPROVEN)
			throw new LogicException();
		return statement1;
	}
	
	public Statement infer2() throws LogicException{
		if (truthstate == STATE_UNPROVEN)
			throw new LogicException();
		return statement2;
	}

//	@Override
//	public void substitute(HashMap<MathObject, MathObject> newVars) {
//		statement1.substitute(newVars);
//		statement2.substitute(newVars);	
//	}
//	
//	@Override
//	public Statement deepcopy(HashMap<Variable, Variable> variables) {
//		return new AndStatement(statement1.deepcopy(variables), statement2.deepcopy(variables));
//	}
	
	@Override
	public Statement deepcopy_and_substitute(HashMap<Variable, Variable> bounded_variables,
			HashMap<MathObject, MathObject> unbounded_variables, Scope newscope) throws LogicException{
		return new AndStatement(statement1.deepcopy_and_substitute(bounded_variables, unbounded_variables, newscope), 
				statement2.deepcopy_and_substitute(bounded_variables, unbounded_variables, newscope), newscope);
	}
	
	@Override
	public String toString() {
		return (Aletheia.verbose_truth_state_printout) ? String.format("[%s] &&{%s} [%s]", statement1, gts(), statement2) : String.format("[%s] && [%s]", statement1, statement2);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AndStatement) {
			AndStatement obj_ = (AndStatement) obj;
			return statement1.equals(obj_.statement1) && statement2.equals(obj_.statement2);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(statement1, statement2);
	}
}
