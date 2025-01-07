package alet;

import java.util.Hashtable;
import java.util.TreeSet;
import java.util.function.BiFunction;

import alet.statements.EqualsStatement;
import alet.statements.Statement;
import alet.variables.FunctionValue;
import alet.variables.MathObject;

public class FunctionComposition extends MathObject {
	
	MathObject function1;
	MathObject function2;

	/**
	 * function2(function1(x))
	 * @param function1
	 * @param function2
	 */
	public FunctionComposition(MathObject function1, MathObject function2) throws LogicException{
		super("FunctionComposition", Scope.getSmallestScope(function1.scope, function2.scope));
		this.function1 = function1;
		this.function2 = function2;
	}
	
	@Override
	public String toString() {
		return String.format("(%s o %s)", function1, function2);
	}
	
	/**
	 * function2(funcion1(obj))
	 * @param function1
	 * @param function2
	 * @param obj
	 * @return
	 * @throws LogicException
	 */
	public static EqualsStatement getEquals(MathObject function1, MathObject function2, MathObject obj) throws LogicException{
		// TODO add function check aka supply proof that the mathobjs are functions and compatible
		FunctionComposition funcom = new FunctionComposition(function1, function2);
		EqualsStatement eq = new EqualsStatement(new FunctionValue(funcom, obj), 
				new FunctionValue(function2, new FunctionValue(function1, obj)), 
				Scope.getSmallestScope(function1.scope, function2.scope, obj.scope));
		eq.setToProved();
		return eq;

	}

}
