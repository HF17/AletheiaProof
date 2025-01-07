package alet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

import alet.sets.Set;
import alet.statements.Statement;
import alet.variables.MathObject;
import alet.variables.Variable;

public class ScopeOld {

	static public ArrayList<ScopeOld> scopes = new ArrayList<ScopeOld>();
	
	HashSet<Scopebrick> bricks;
	
	public ScopeOld() {
		bricks = new HashSet<Scopebrick>();
	}
	
	public void addBrick(Scopebrick brick) {
		bricks.add(brick);
	}
	
	/**
	 * Can prove implication P -> Q iff scope(Q) <= score(P).
	 * Call like Q.isInside(P)
	 * @param other
	 * @return
	 */
	public boolean isInside(ScopeOld other) {
		return other.bricks.containsAll(bricks);
	}
	
	/**
	 * Checks if a scope with the concent of current + extra allready exists.
	 * If so return it otherwise create it.
	 * @param current
	 * @param extra
	 * @return
	 */
	static public ScopeOld getScope(ScopeOld current, Scopebrick extra) {
		ScopeOld scope = new ScopeOld();
		if (current != null) {
			if (current.bricks.contains(extra))
				return current;
			scope.bricks = (HashSet<Scopebrick>) current.bricks.clone();
		}
		scope.addBrick(extra);
		
		if (scopes.contains(scope)) {
			return scopes.get(scopes.lastIndexOf(scope));
		}else {
			return scope;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ScopeOld) {
			ScopeOld new_name = (ScopeOld) obj;
			return bricks.equals(new_name.bricks);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(bricks);
	}
	
	public static void main(String[] args) {
		Statement stat = new Statement() {
			
			@Override
			public Statement deepcopy_and_substitute(HashMap<Variable, Variable> bounded_variables,
					HashMap<MathObject, MathObject> unbounded_variables) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		Scopebrick st = new Scopebrick(stat);
		ScopeOld s = getScope(null, st);
		System.out.println(s);
		System.out.println(getScope(s, new Scopebrick(stat)));
	}
}
