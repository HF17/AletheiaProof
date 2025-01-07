package alet;

import alet.variables.Variable;

public class EqualityInfo {

	Function onself; 
	Function onother; 
	Variable other;
	
	public EqualityInfo(Function onself, Function onother, Variable other) {
		this.onself = onself;
		this.onother = onother;
		this.other = other;
	}
	
//	@Override
//	public boolean equals(Object obj) {
//		if (obj instanceof EqualityInfo) {
//			EqualityInfo other = (EqualityInfo) obj;
//			return (onself==other.) and () and ();
//		}
//		return super.equals(obj);
//	}
	
}
