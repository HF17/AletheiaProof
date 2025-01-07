package alet;

import java.util.Objects;

import alet.statements.Statement;

public class Scopebrick {

	Proposition proposition;
	Statement statement;
	Definition definition;
	
	public Scopebrick(Proposition proposition) {
		this.proposition = proposition;
	}
	
	public Scopebrick(Statement statement) {
		this.statement = statement;
	}
	
	public Scopebrick(Definition definition) {
		this.definition = definition;
	}
	
	// TODO überlegen ob man equals mit == ersetzen könnte
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Scopebrick)	{
			Scopebrick new_name = (Scopebrick) obj;
			return Objects.equals(proposition, new_name.proposition) && 
					Objects.equals(definition, new_name.definition) &&
					Objects.equals(statement, new_name.statement);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(proposition, statement, definition);
	}
	
	@Override
	public String toString() {
		if (proposition != null)
			return proposition.name;
		if (definition != null)
			return definition.name;
		if (statement != null)
			return statement.toString();
		return "NULL";
	}	
}
