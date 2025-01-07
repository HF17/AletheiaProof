package alet.statements;

import java.util.HashMap;
import java.util.Objects;

import alet.Aletheia;
import alet.Equals;
import alet.LogicException;
import alet.Scope;
import alet.sets.Set;
import alet.variables.MathObject;
import alet.variables.Variable;

public class ExistStatement extends Statement {

	MathObject domain;
	Statement statement;
	Variable variable;
	
	
	public ExistStatement(MathObject domain, Scope scope) {
		super(scope);
		this.domain = domain;
	}
	
	
	public void setStatement(Statement statement) {
		this.statement = statement;
	}
	
	public Variable createVariable(String name) {
		variable = new Variable(name, scope);
		variable.quantor = true;
		variable.parentset = domain;
		return variable;
	}
	
	public void prove(MathObject obj, Statement obj_statement) throws LogicException{
		HashMap<MathObject, MathObject> subs = new HashMap<MathObject, MathObject>();
		subs.put(variable, obj);
		Statement stat_subs = statement.deepcopy_and_substitute(subs);
		if (scope.isInside(obj_statement.scope) && stat_subs.equals(obj_statement)) {
			setToProved();
			copy_assumptions(statement);
		}else
			throw new LogicException();
		
	}
	
	public MathObject infer(String name) throws LogicException{
		if (truthstate == STATE_UNPROVEN)
			throw new LogicException();
		MathObject obj = new MathObject(name, scope);
		HashMap<MathObject, MathObject> subsmap = new HashMap<MathObject, MathObject>();
		subsmap.put(variable, obj);
		Statement stat_copy = statement.deepcopy_and_substitute(subsmap);
		stat_copy.setToProved();
		stat_copy.copy_assumptions(this);
		obj.properties.add(stat_copy);
		return obj;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ExistStatement) {
			ExistStatement obj_ = (ExistStatement) obj;
			return statement.equals(obj_.statement) && domain.equals(obj_.domain)
					&& variable.equals(obj_.variable);
		}
		return false;
	}
	 
	
//	@Override
//	public void substitute(HashMap<MathObject, MathObject> newVars) {
//		statement.substitute(newVars);	
//		if (newVars.containsKey(domain))
//			domain = newVars.get(domain);
//		else
//			domain.substitute(newVars);
//	}
//	
//	@Override
//	public Statement deepcopy(HashMap<Variable, Variable> variables) {
//		MathObject newdomain;
//		if (variables.containsKey(domain)) {
//			newdomain = variables.get(domain);
//		}else {
//			newdomain = domain.deepcopy(variables);}
//		
//		ExistStatement ex = new ExistStatement(newdomain);
//		ex.variable = ex.createVariable(variable.name);
//		variables.put(variable, ex.variable);
//		ex.setStatement(statement.deepcopy(variables));
//		return ex;
//	}
	
	@Override
	public Statement deepcopy_and_substitute(HashMap<Variable, Variable> bounded_variables,
			HashMap<MathObject, MathObject> unbounded_variables, Scope newscope) throws LogicException{
		MathObject newdomain;
		if (bounded_variables.containsKey(domain)) {
			newdomain = bounded_variables.get(domain);		
		}else {
			if (unbounded_variables.containsKey(domain)) {
				newdomain = unbounded_variables.get(domain);
			}else {
				newdomain = domain.deepcopy_and_substitute(bounded_variables, unbounded_variables, newscope);
			}
		}
		ExistStatement ex = new ExistStatement(newdomain, newscope);
		ex.variable = ex.createVariable(variable.name);
		bounded_variables.put(variable, ex.variable);
		ex.setStatement(statement.deepcopy_and_substitute(bounded_variables, unbounded_variables, newscope));
		return ex;
	}
	
	@Override
	public String toString() {
		return (Aletheia.verbose_truth_state_printout) ? String.format("∃{%s}%s€%s:[%s]", gts(), variable, domain, statement)
				: String.format("∃%s€%s:[%s]", variable, domain, statement);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(domain, statement, variable);
	}
	

}
