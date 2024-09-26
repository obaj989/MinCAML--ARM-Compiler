package compiler.common.expression;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import compiler.common.*;

public class ClosureFunDef {
	final Id id;
	final Type type;
	final List<Id> args;
	final Exp body;
	Set<Id> freeVariables;
	boolean isTopLevel;

	public ClosureFunDef(Id id, Type t, List<Id> args, Exp body, Set<Id> freeVars) {
		this.id = id;
		this.type = t;
		this.args = args;
		this.body = body;
		this.freeVariables = new HashSet<>();
		this.isTopLevel = false; // Default to false, set to true when determined
	}

	// Getter and setter for isTopLevel
	public boolean isTopLevel() {
		return isTopLevel;
	}

	public void setTopLevel(boolean isTopLevel) {
		this.isTopLevel = isTopLevel;
	}
	
	public Set<Id> getFreeVariables(){
		return this.freeVariables;
	}

	// Add a method to set free variables
	public void setFreeVariables(Set<Id> freeVariables) {
		this.freeVariables = freeVariables;
	}

	public Exp getBody() {
		// TODO Auto-generated method stub
		return this.body;
	}
	
	public List<Id> getArgs(){
		return this.args;
	}
	
	public Id getId() {
		return this.id;
	}
	
	public Type getType() {
		return this.type;
	}
}