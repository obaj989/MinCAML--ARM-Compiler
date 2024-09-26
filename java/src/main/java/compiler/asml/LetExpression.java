package compiler.asml;

public class LetExpression extends Expression {
	private String variableName;
	private Expression assignment;
	private Expression body;

	public LetExpression(String variableName, Expression assignment, Expression body) {
		this.setVariableName(variableName);
		this.setAssignment(assignment);
		this.setBody(body);
	}

	public void accept(ASMLVisitor v) {
		v.visit(this);
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public Expression getBody() {
		return body;
	}

	public void setBody(Expression body) {
		this.body = body;
	}

	public Expression getAssignment() {
		return assignment;
	}

	public void setAssignment(Expression assignment) {
		this.assignment = assignment;
	}
}
