package compiler.asml;

public class VariableExpression extends Expression {
	private String name;

	public VariableExpression(String name) {
		this.setName(name);
	}

	public void accept(ASMLVisitor v) {
		v.visit(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
