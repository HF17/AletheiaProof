package alet.statements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import alet.LogicException;
import alet.Scope;
import alet.Scopebrick;
import alet.variables.MathObject;
import alet.variables.Variable;

public class OrStatement extends Statement {

	Statement statement1;
	Statement statement2;
	
	public OrStatement(Statement statement1, Statement statement2, Scope scope) {
		super(scope);
		setData(statement1, statement2);
	}
	
	public OrStatement (Scope scope) {
		super(null);
		this.scope = scope.createChild(new Scopebrick(this));
	}
	
	public void setData(Statement statement1, Statement statement2) {
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
	
	public void prove1() throws LogicException{
		if (statement1.truthstate == STATE_UNPROVEN)
			throw new LogicException();
		setToProved();
		copy_assumptions(statement1);
	}
	
	public void prove2() throws LogicException{
		if (statement2.truthstate == STATE_UNPROVEN)
			throw new LogicException();
		setToProved();
		copy_assumptions(statement2);
	}
	
	public Statement infer(Statement conclusion_ass1, Statement conclusion_ass2) throws LogicException{
		if (truthstate == STATE_UNPROVEN)
			throw new LogicException();
		if (Statement.assumptionScopeProvableFrom(statement1, conclusion_ass1) && Statement.assumptionScopeProvableFrom(statement2, conclusion_ass2)) {
			conclusion_ass1.assumed_statements = (ArrayList<Statement>) assumed_statements.clone();
			return conclusion_ass1;
		}
		throw new LogicException();
	}
	
	@Override
	public Statement deepcopy_and_substitute(HashMap<Variable, Variable> bounded_variables,
			HashMap<MathObject, MathObject> unbounded_variables, Scope newscope) throws LogicException{
		return new OrStatement(statement1.deepcopy_and_substitute(bounded_variables, unbounded_variables, newscope), 
				statement2.deepcopy_and_substitute(bounded_variables, unbounded_variables, newscope), newscope);
	}
	
//	@Override
//	public void substitute(HashMap<MathObject, MathObject> newVars) {
//		statement1.substitute(newVars);
//		statement2.substitute(newVars);	
//	}
//	
//	@Override
//	public Statement deepcopy(HashMap<Variable, Variable> variables) {
//		return new OrStatement(statement1.deepcopy(variables), statement2.deepcopy(variables));
//	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof OrStatement) {
			OrStatement obj_ = (OrStatement) obj;
			return statement1.equals(obj_.statement1) && statement2.equals(obj_.statement2);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(statement1, statement2);
	}
}
