package alet;

import java.util.ArrayList;
import java.util.HashMap;

import alet.statements.Statement;
import alet.variables.MathObject;

public class PropertyManager {

	static HashMap<MathObject, ArrayList<Statement>> properties = new HashMap<MathObject, ArrayList<Statement>>();
	
	public static void addProperty(MathObject obj, Statement statement) {
		if (!properties.containsKey(obj))
			properties.put(obj, new ArrayList<Statement>());
		properties.get(obj).add(statement);
	}
	
	public static boolean hasProperty(MathObject obj, Statement statement) throws LogicException {
		if (properties.containsKey(obj) && properties.get(obj).contains(statement)) {
			return true;
		}else {
			if (obj.scope.parent == null)
				return false;
			return hasProperty(obj.deepcopy_and_substitute(obj.scope.parent), statement);
		}

	}
	
	public static void main(String[] args) {
		
	}
}
