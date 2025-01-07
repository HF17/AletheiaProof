package alet;

import java.util.HashMap;
import java.util.HashSet;

import alet.sets.Set;
import alet.statements.AndStatement;
import alet.statements.EqualsStatement;
import alet.statements.ExistStatement;
import alet.statements.ForStatement;
import alet.statements.ImpStatement;
import alet.statements.RelationStatement;
import alet.statements.Statement;
import alet.variables.FunctionValue;
import alet.variables.MathObject;
import alet.variables.Variable;

public class Aletheia {
	
	static final public boolean verbose_ID_printout = false;
	static final public boolean verbose_truth_state_printout = true;
	
	static Definition surdef;
	public static HashMap<Scope, HashSet<MathObject>> equalities;

	public static void main(String[] args) throws LogicException{
		calculatePropsDefs();
		Definition d = getSurDef();
		System.out.println(d.toVerboseString());
		// FA x € A, EX y € B: x=y
		
		//new ForStatement(null, new ExistStatement(null, new EqualsStatement(null, null)))
		
		Proposition p = getSurProp();
		
		p.assumeSetupConditions();
		System.out.println(p);
		proveSurProp(p, p.setup_variables, p.setup_variables_names);
	}
	
	public static void proveSurProp(Proposition p, HashMap<MathObject, Statement> setup_variables, HashMap<String, MathObject> setup_variables_names) throws LogicException{
		
		Scope scope = Scope.getMainScope().children.get(new Scopebrick(p));
		System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSS " + scope);
		System.out.println("setup_variables_names: "+setup_variables_names.toString());
		MathObject g = setup_variables_names.get("g");
		ForStatement fa = new ForStatement(setup_variables_names.get("C"), scope);
		MathObject c0 = fa.createVariable("c0");
		System.out.println(String.format("c0: %s", c0));
		RelationStatement s = (RelationStatement) g.properties.get(0);
		System.out.println(String.format("s is %s", s));
		ForStatement inf = (ForStatement) s.infer();
		System.out.println(String.format("inf: %s", inf));
		
		ExistStatement inf2 = (ExistStatement) inf.infer(c0);
		System.out.println(String.format("inf2: %s", inf2));
		
		MathObject b0 = inf2.infer("b0");
		EqualsStatement inf3 = (EqualsStatement) b0.properties.get(0);
		System.out.println(String.format("inf3: %s", inf3));
		System.out.println(String.format("b0: %s", b0));
		
		MathObject f = setup_variables_names.get("f");
		RelationStatement s2 = (RelationStatement) f.properties.get(0);
		MathObject a0 = ((ExistStatement) ((ForStatement) s2.infer()).infer(b0)).infer("a0");
		
		EqualsStatement inf4 = (EqualsStatement) a0.properties.get(0);
		System.out.println(String.format("inf4: %s", inf4));
		System.out.println(String.format("a0: %s", a0));
		
		EqualsStatement eq = new EqualsStatement(new FunctionValue(g, 
				new FunctionValue(f, a0)), c0, scope);
		
		Equals.deduceEquation(inf3, eq);
		System.out.println("eq: "+eq);
		
		EqualsStatement funeq = FunctionComposition.getEquals(f, g, a0);
		System.out.println("funeq: "+funeq);
		
		EqualsStatement eq2 = new EqualsStatement(new FunctionValue(new FunctionComposition(f, g), a0), c0, scope);
		Equals.deduceEquation(eq, eq2);
		System.out.println("eq2: "+eq2);
		
		ExistStatement ex = new ExistStatement(setup_variables_names.get("A"), scope);
		Variable a1 = ex.createVariable("a1");
		ex.setStatement(new EqualsStatement(new FunctionValue(new FunctionComposition(f, g), a1), c0, scope));
		ex.prove(a0, eq2);
		System.out.println("ex: "+ex);
		
		fa.setStatement(ex);
		fa.prove(ex);
		
		Definition d = getSurDef();
		RelationStatement relstat = new RelationStatement(d, new FunctionComposition(f, g), scope);
		HashMap<String, MathObject> rel_obj_subs = new HashMap<String, MathObject>();
		rel_obj_subs.put("A", setup_variables_names.get("A"));
		rel_obj_subs.put("B", setup_variables_names.get("C"));
		rel_obj_subs.put("f", new FunctionComposition(f, g));
		relstat.prove(rel_obj_subs);
		System.out.println("fa: "+fa);
		System.out.println("prop statement: "+p.statement);
		

	}
	
	public static Definition getSurDef() throws LogicException {
		if (surdef == null) 
			surdef = createSurDef();
		return surdef;
	}
	
	// TODO jede Definition nur einmal erstellen
	public static Definition createSurDef() throws LogicException {
//		String name = "surjective";
//		Set A = Set.createArbitrarySet("A");
//		Set B = Set.createArbitrarySet("B");
//		new Function(A, A);
//		MathObject mainvariable = new MathObject("f");
//		mainvariable.type = MathObject.TYPE_FUNCTION;
//		HashMap<MathObject, Statement> setup_variables;
//		Statement definition_condition;
//		new Definition(name, setup_variables, null, mainvariable, definition_condition);
	
		
		
		
//		String name = "surjective";
//		MathObject f = new MathObject("f");
//		
//		ForStatement fa = new ForStatement(new FunctionCodomain(f));
//		Variable y = fa.createVariable("y");
//		ExistStatement ex = new ExistStatement(new FunctionDomain(f));
//		Variable x = ex.createVariable("x");
//		EqualsStatement eq = new EqualsStatement(new FunctionValue(f, x), y);
//		ex.setStatement(eq);
//		fa.setStatement(ex);
//		ForStatement definition_condition = fa;
//		
//		Statement variable_conditions = new HashMap<MathObject, MathObject>();
//		
//		return new Definition(name,  f, definition_condition);
//		
//		
//
//}
	
	
//	public static Definition getSurDef() {
////			String name = "surjective";
////			Set A = Set.createArbitrarySet("A");
////			Set B = Set.createArbitrarySet("B");
////			new Function(A, A);
////			MathObject mainvariable = new MathObject("f");
////			mainvariable.type = MathObject.TYPE_FUNCTION;
////			HashMap<MathObject, Statement> setup_variables;
////			Statement definition_condition;
////			new Definition(name, setup_variables, null, mainvariable, definition_condition);
//		
		String name = "surjective";
		Definition def = new Definition(name);
		Scope scope = Scope.getMainScope().children.get(new Scopebrick(def));
		MathObject A = new MathObject("A", scope);
		MathObject B = new MathObject("B", scope); // Sollte domain ein Set sein als instance? oder doch lieber on the fly checking, ich meine man könnte ja f(x) haben, wie soll man da wissen das der output ein set ist
		MathObject mainvariable = new MathObject("f", scope);
		
		HashMap<MathObject, Statement> setup_variables = new HashMap<MathObject, Statement>();
		setup_variables.put(A, new RelationStatement(new Definition_IsSet(), A, scope));
		setup_variables.put(B, new RelationStatement(new Definition_IsSet(), B, scope));
		setup_variables.put(mainvariable, new RelationStatement(new Definition_IsFunction(A, B), mainvariable, scope));
		
		ForStatement fa = new ForStatement(B, scope);
		Variable y = fa.createVariable("y");
		ExistStatement ex = new ExistStatement(A, scope);
		Variable x = ex.createVariable("x");
		EqualsStatement eq = new EqualsStatement(new FunctionValue(mainvariable, x), y, scope);
		ex.setStatement(eq);
		fa.setStatement(ex);
		Statement definition_condition = fa;
		
		def.setData(setup_variables, null, mainvariable, definition_condition);
		return def;
	}		
		
		// A = {(f,g) | Im(f) < Domain(g)}
		// B = set of all functions
		// φ:A->B, φ((f,g)) = f o g

	
	public static Proposition getSurProp() throws LogicException{
		String name = "surjective composition";
		Proposition prop = new Proposition(name);
		Scope scope = Scope.getMainScope().children.get(new Scopebrick(prop));
		MathObject A = new MathObject("A", scope);
		MathObject B = new MathObject("B", scope);
		MathObject C = new MathObject("C", scope);
		MathObject f = new MathObject("f", scope);
		MathObject g = new MathObject("g", scope);
		
		HashMap<MathObject, Statement> setup_variables = new HashMap<MathObject, Statement>();
		Definition surdef = getSurDef();
		setup_variables.put(A, new RelationStatement(new Definition_IsSet(), A, scope));
		setup_variables.put(B, new RelationStatement(new Definition_IsSet(), B, scope));
		setup_variables.put(C, new RelationStatement(new Definition_IsSet(), C, scope));
		setup_variables.put(f, new RelationStatement(surdef, f, scope));
		setup_variables.put(g, new RelationStatement(surdef, g, scope));
		
		HashMap<String, MathObject> f_setup = new HashMap<String, MathObject>();
		f_setup.put("A", A);
		f_setup.put("B", B);
		HashMap<String, MathObject> g_setup = new HashMap<String, MathObject>();
		g_setup.put("A", B);
		g_setup.put("B", C);
		f.assumeDefinition(surdef, f_setup);
		g.assumeDefinition(surdef, g_setup);
		Definition deffi = ((RelationStatement) g.properties.get(0)).definition;
		System.out.println("here: "+deffi.definition_condition );

		//AndStatement p = new AndStatement(new RelationStatement(getSurDef(), f), new RelationStatement(getSurDef(), g));
		RelationStatement q = new RelationStatement(getSurDef(), new FunctionComposition(f, g), scope); 
		
		//ImpStatement statement = new ImpStatement(p,q);
		
		prop.setData(setup_variables, null, q);
		return prop;
	}

	private static void calculatePropsDefs() {
		
//		String name = "is a set";
//		MathObject mainvariable = new MathObject("A");
//		HashMap<MathObject, Statement> setup_variables = new HashMap<MathObject, Statement>();
//		setup_variables.put(mainvariable, null);
//		Statement definition_condition;
//		new Definition(name, setup_variables, null, mainvariable, definition_condition);
		
	}
}
