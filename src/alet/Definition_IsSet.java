package alet;

import java.util.ArrayList;
import java.util.HashMap;

import alet.statements.Statement;
import alet.variables.Constant;
import alet.variables.MathObject;

public class Definition_IsSet extends Definition {

	public Definition_IsSet() {
		super("Definition_IsSet", null, null, null, null);
	}
	
	@Override
	public String toString() {
		return "Definition_IsSet";
	}

}
