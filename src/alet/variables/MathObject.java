package alet.variables;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import alet.Aletheia;
import alet.Definition;
import alet.EqualityInfo;
import alet.LogicException;
import alet.PropertyManager;
import alet.Scope;
import alet.statements.RelationStatement;
import alet.statements.Statement;

/**
 * The MathObject was initially the Variable class, but I realized that a constant variable should be able
 * to collect properties just like a variable - picture an auxiliary function in a proof. So MathObject wasa created
 * to generalize Variable to include more or less everything of interest.
 */
public class MathObject {

	public final static int TYPE_OTHER = 0;
	public final static int TYPE_FUNCTION = 1;
	public final static int TYPE_CONTAINER = 2;
	public final static int TYPE_FUNCTIONVALUE = 3;
	
	public final static int variable_TYPE_CONST = 1;
	public final static int variable_TYPE_FORALL = 2;
	
	public int variable_type;
	
	public String name;
	public Scope scope;
//	/**
//	 * substituted statements with the concrete variable instances
//	 */
//	public ArrayList<Statement> properties;
	/**
	 * what are the special abilities of this math object? Can it map?
	 * Can it contain? Otherwise?
	 */
	public int type;
	public int ID;
	static public int global_ID = 0;
	
	
	public MathObject(String name, Scope scope) {
		this.name = name;
		global_ID += 1;
		ID = global_ID;
		this.scope = scope;
	}
	
	public static MathObject createForAllVariable(String name, Scope scope) {
		MathObject obj = new MathObject(name, scope);
		
		return obj;
	}
	
	public void assumeDefinition(Definition definition, HashMap<String, MathObject> setup_variables_remap) throws LogicException{
		setup_variables_remap.put(definition.mainvariable.name, this);
		RelationStatement stat = new RelationStatement(definition.copy_and_substitute(setup_variables_remap, scope), this, scope);
		stat.assume();
		PropertyManager.addProperty(this, stat);
		//new RelationStatement(new Definition(name, null, null, null, stat_copy), null), null)

	}
	
	@Override
	public String toString() {
		return (Aletheia.verbose_ID_printout) ? String.format("%s{%s}", name, ID) : String.format("%s", name);
	}

//	public void substitute(HashMap<MathObject, MathObject> newVars) {
//	}
	
	public MathObject deepcopy_and_substitute(HashMap<Variable, Variable> bounded_variables, 
			HashMap<MathObject, MathObject> unbounded_variables, Scope newscope) throws LogicException {
		//return new MathObject(name);
		return new MathObject(name, newscope);
	}
	
	public MathObject deepcopy_and_substitute(Scope newscope) throws LogicException {
		return deepcopy_and_substitute(new HashMap<Variable, Variable>(), new HashMap<MathObject, MathObject>(), newscope);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MathObject) {
			MathObject obj2 = (MathObject) obj;
			return name.equals(obj2.name) && scope.equals(obj2.scope);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name, scope);
	}
	
}
