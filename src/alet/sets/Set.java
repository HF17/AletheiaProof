package alet.sets;

import alet.variables.MathObject;

public class Set extends MathObject{

	public Set(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * ARBITRARY SET
	 * UNION
	 * INTERSECTION
	 * CARTESIAN PRODUCT
	 * POWERSET
	 * SUBSET
	 * 	FUNCTION
	 * 	CONDITION
	 * FUNCTION-SET	
	 */
	int type;
	
	public static int ARBITRARY_SET = 0;
	
	
	public static Set createArbitrarySet(String name) {
		Set s = new Set(name);
		s.type = ARBITRARY_SET;
		return s;
	}
	
	boolean obviousIsSubset(Set superset) {
		return true;
	}
}
