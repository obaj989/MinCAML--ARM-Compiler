package compiler.frontend;

import compiler.common.Id;
import compiler.common.Type;

public class Variable {
	 private Id id;    // Represents the identifier of the argument
	 private Type type; // Represents the type of the argument

	 public Variable(Id id, Type type) {
	     this.id = id;
	     this.type = type;
	 }

	 // Getters
	 public Id getId() {
	     return id;
	 }

	 public Type getType() {
	     return type;
	 }
}
