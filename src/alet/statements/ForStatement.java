package alet.statements;

import java.util.HashMap;
import java.util.Objects;

import alet.Aletheia;
import alet.LogicException;
import alet.Scope;
import alet.sets.Set;
import alet.variables.MathObject;
import alet.variables.Variable;

public class ForStatement extends Statement{

	MathObject domain;
	Statement statement;
	Variable variable;
		
	public ForStatement(MathObject domain, Scope scope) {
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
	
//	@Override
//	boolean prove(Variable var) {
//		if (var.quantor) {
//			if (domain.obviousIsSubset(var.parentset)) {
//				return statement.prove
//			}
//		}
//	}
	
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
//		ForStatement fa = new ForStatement(newdomain);
//		fa.variable = fa.createVariable(variable.name);
//		variables.put(variable, fa.variable);
//		fa.setStatement(statement.deepcopy(variables));
//		return fa;
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
		ForStatement fa = new ForStatement(newdomain, newscope);
		fa.variable = fa.createVariable(variable.name);
		bounded_variables.put(variable, fa.variable);
		fa.setStatement(statement.deepcopy_and_substitute(bounded_variables, unbounded_variables, newscope));
		return fa;
	}

	public void prove(Statement obj_statement) throws LogicException{
		// TODO add domain check
//		if (!variable.properties.contains(statement))
//			throw new LogicException();
		if (scope.isInside(obj_statement.scope) && statement.equals(obj_statement)) {
			setToProved();
			copy_assumptions(statement);
		}else
			throw new LogicException();
		
		
	}
	
	public Statement infer(MathObject obj) throws LogicException{
		// TODO: check if obj in domain und schwebene sets implementieren (als domain = null ?)
		if ((truthstate == STATE_PROVEN || is_assumed) && obj.scope.isInside(scope)) {
			HashMap<MathObject, MathObject> subsmap = new HashMap<MathObject, MathObject>();
			
			subsmap.put(variable, obj);
			Statement stat_copy = statement.deepcopy_and_substitute(subsmap);
			stat_copy.setToProved();
			stat_copy.copy_assumptions(this);
			return stat_copy;
		}
		throw new LogicException();
			
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ForStatement) {
			ForStatement obj_ = (ForStatement) obj;
			return statement.equals(obj_.statement) && domain.equals(obj_.domain);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return (Aletheia.verbose_truth_state_printout) ? String.format("∀{%s}%s€%s:[%s]", gts(), variable, domain, statement)
				: String.format("∀%s€%s:[%s]", variable, domain, statement);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(domain, statement);
	}

}
