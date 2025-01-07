package alet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * dieses scope wird nicht dynamisch on the fly gemacht, sondern ist eher wie boxproof
 * Hier muss auch immer gecheckt werden ob man objekte aus den richtigen scopes verwenden um sachen zu beweisen
 * oder zu konstruieren
 */
public class Scope {
	
	public static Scope mainScope;
	
	Scopebrick axiom;
	Scope parent;
	HashMap<Scopebrick, Scope> children = new HashMap<Scopebrick, Scope>();
	ArrayList<String> introduced_names = new ArrayList<String>();
	
	private Scope(Scopebrick axiom) {
		this.axiom = axiom;
	}
	
	public boolean isInside(Scope other) {
		if (this == other)
			return true;
		if (parent == other)
			return true;
		else if (parent != null)
			return parent.isInside(other);
		return false;
	}
	
	public Scope createChild(Scopebrick axiom) {
		Scope s = new Scope(axiom);
		s.parent = this;
		children.put(axiom, s);
		return s;
	}
	
	static public Scope getMainScope() {
		if (mainScope != null)
			return mainScope;
		mainScope = new Scope(null);
		return mainScope;
	}
	
	public void addName(String name) throws LogicException{
		if (introduced_names.contains(name))
			throw new LogicException();
		introduced_names.add(name);
	}
	
	static public Scope getSmallestScope_(Scope scope1, Scope scope2) throws LogicException{
		if (scope1.isInside(scope2))
			return scope1;
		else if (scope2.isInside(scope1))
			return scope2;
		throw new LogicException();
	}
	
	static public Scope getSmallestScope(Scope ... scopes ) throws LogicException{
		Scope scope = getSmallestScope_(scopes[0], scopes[1]);
		for (int i = 2; i < scopes.length; i++) {
			scope = getSmallestScope_(scope, scopes[i]);
		}
		return scope;
	}
	
	@Override
		public String toString() {
			if (axiom != null)
				return axiom.toString();
			return "MAIN SCOPE";
		}
}
