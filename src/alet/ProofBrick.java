package alet;

public enum ProofBrick {

	APPLY_STATEMENT,
	APPLY_THEOREM,
	APPLY_DEFINITION,
	/**
	 * Let x € S
	 */
	INIT_VARIABLE,
	/** 
	 * casual constant like 0,1,R or function or container
	 */
	INIT_CONSTANT, 
	/**
	 * let y = f(x)
	 */
	INIT_FUNCTIONVALUE,
	INIT_FUNCTIONVALUE3;
	
	
	private ProofBrick() {
		// TODO Auto-generated constructor stub
	}
}

