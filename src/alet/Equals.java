package alet;

import java.util.ArrayList;
import java.util.HashMap;

import alet.statements.EqualsStatement;
import alet.variables.FunctionValue;
import alet.variables.MathObject;

public class Equals {

	static public HashMap<MathObject, ArrayList<EqualsStatement>> equalities = new HashMap<MathObject, ArrayList<EqualsStatement>>();
	static private Scope newscope;
	
	static public void addEquality(EqualsStatement eq){
		if (!equalities.containsKey(eq.left))
			equalities.put(eq.left, new ArrayList<EqualsStatement>());
		if (!equalities.containsKey(eq.right))
			equalities.put(eq.right, new ArrayList<EqualsStatement>());
		equalities.get(eq.left).add(eq);
		equalities.get(eq.right).add(eq);
	}
	
	/**
	 * going from equation 1 to equation 2. For now assume that proven and conjecture at least share one side, i.e. right(proven) = right(conjecture)
	 * or right(proven) = left(conjecture) or left(proven)=...
	 */
	static public void deduceEquation(EqualsStatement proven, EqualsStatement conjecture) throws LogicException{
		System.out.println("\nDeduce Equation!");
		System.out.println("proven: "+proven);
		System.out.println("conjecture: "+conjecture);
//		System.out.println("equalities: ");
//		for (MathObject obj : equalities.keySet()) {
//			System.out.println(obj+" -> "+equalities.get(obj));
//		}

//		if ( 	  	(checkSubstitutionability(proven.left, conjecture.left, proven.scope) && checkSubstitutionability(proven.right, conjecture.right, proven.scope))
//				|| 	(checkSubstitutionability(proven.left, conjecture.right, proven.scope) && checkSubstitutionability(proven.right, conjecture.left, proven.scope))
//				|| 	(checkSubstitutionability(proven.right, conjecture.left, proven.scope) && checkSubstitutionability(proven.left, conjecture.right, proven.scope))
//				|| 	(checkSubstitutionability(proven.right, conjecture.right, proven.scope) && checkSubstitutionability(proven.left, conjecture.left, proven.scope))) {
		if ( 		orientedEquationIsSubstitutable(proven.left, conjecture.left, proven.right, conjecture.right, proven.scope)
				||  orientedEquationIsSubstitutable(proven.left, conjecture.right, proven.right, conjecture.left, proven.scope)) {
			conjecture.setToProved();
			conjecture.scope = newscope;
		}else {
			throw new LogicException();
		}
		
	}
	
	public static boolean orientedEquationIsSubstitutable(MathObject left1, MathObject right1, MathObject left2, MathObject right2, Scope scope) throws LogicException{
		newscope = scope;
		return exprIsSubstitutable(left1, right1) && exprIsSubstitutable(left2, right2);
	}
	
	public static boolean exprIsSubstitutable(MathObject exprtree1, MathObject exprtree2) throws LogicException{
		// TODO implementation for all the other subclasses extending MathObject like FunctionComposition
		System.out.println(String.format("issubable %s %s", exprtree1, exprtree2));
		if (exprtree1 instanceof FunctionValue && exprtree2 instanceof FunctionValue) {
			FunctionValue exprtree1_ = (FunctionValue) exprtree1;
			FunctionValue exprtree2_ = (FunctionValue) exprtree2;
			if (exprIsSubstitutable(exprtree1_.object, exprtree2_.object) && exprIsSubstitutable(exprtree1_.function, exprtree2_.function)) {
				return true;
			}
			
		}else if (!(exprtree1 instanceof FunctionValue) && !(exprtree2 instanceof FunctionValue)){
			if (exprtree1.equals(exprtree2)) {
				return true;
			}
		}
		
		if (equalities.containsKey(exprtree1)) {
			for (EqualsStatement eq : equalities.get(exprtree1)){
				if (eq.getOther(exprtree1).equals(exprtree2)) {
					System.out.println(String.format("%s -> %s", exprtree1, exprtree2));
					newscope = Scope.getSmallestScope(newscope, eq.scope);
					System.out.println("true");
					return true;
				}
			}
		}
		return false;

	}
	
//	public static boolean checkSubstitutionability(MathObject exprtree1, MathObject exprtree2, Scope scope1) throws LogicException{
//		// TODO mehr scope überlegungen. Errors oder minscope?
//		ArrayList<EqualsSubstituion> differences = getDifference(exprtree1, exprtree2);
//		newscope = scope1;
//		
//		// exprtree1 == exprtree2
//		if (differences.size() == 0)
//			return true;
//		
////		System.out.println(String.format("checkSubstitutionability %s %s %s", exprtree1, exprtree2, differences));
//		
//		// s.source --> s.target
//		for (EqualsSubstituion s : differences) {
//			
//			boolean can_substitute = false;
//			if (equalities.containsKey(s.source)) {
//				
//				for (EqualsStatement eq : equalities.get(s.source)) {
//					if (eq.getOther(s.source).equals(s.target)) {
//						newscope = Scope.getSmallestScope(newscope, eq.scope);
//						can_substitute = true;
//						System.out.println(s.source + " -> "+s.target);
//						break;
//					}
//				}
//			}
//			
//			if (can_substitute == false)
//				return false;
//		}
//		
//		return true;
//
//	}
//	
//	public static ArrayList<EqualsSubstituion> getDifference(MathObject obj1, MathObject obj2) {
//		ArrayList<EqualsSubstituion> list = new ArrayList<EqualsSubstituion>();
//		getDifference_(obj1, obj2, list);
//		return list;
//	}
//	
//	/**
//	 * 
//	 * @param obj1
//	 * @param obj2
//	 * @return null if same
//	 */
//	public static void getDifference_(MathObject obj1, MathObject obj2, ArrayList<EqualsSubstituion> list) {
//		if (obj1 instanceof FunctionValue && obj2 instanceof FunctionValue) {
//			FunctionValue obj1_ = (FunctionValue) obj1;
//			FunctionValue obj2_ = (FunctionValue) obj2;
//			getDifference_(obj1_.object, obj2_.object, list);
//			getDifference_(obj1_.function, obj2_.function, list);
//			
//		}else if (obj1 instanceof FunctionValue || obj2 instanceof FunctionValue) {
//			list.add(new EqualsSubstituion(obj1, obj2));
//			
//		}else {
//			if (!obj1.equals(obj2))
//				list.add(new EqualsSubstituion(obj1, obj2));
//		}
//	}
	
	
}


//class EqualsSubstituion{
//	MathObject source;
//	MathObject target;
//	public EqualsSubstituion(MathObject source, MathObject target) {
//		this.source = source;
//		this.target = target;
//	}
//	
//	@Override
//	public String toString() {
//		return source.toString() + " -> " + target.toString();
//	}
//	
//}


