package compiler.asml;

public class BinaryOperation extends Expression {
	private String operator;
	private Expression leftOperand;
	private Expression rightOperand;

	public BinaryOperation(String operator, Expression leftOperand, Expression rightOperand) {
		this.setOperator(operator);
		this.setLeftOperand(leftOperand);
		this.setRightOperand(rightOperand);
	}

	public void accept(ASMLVisitor v) {
		v.visit(this);
	}

	public Expression getLeftOperand() {
		return leftOperand;
	}

	public void setLeftOperand(Expression leftOperand) {
		this.leftOperand = leftOperand;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Expression getRightOperand() {
		return rightOperand;
	}

	public void setRightOperand(Expression rightOperand) {
		this.rightOperand = rightOperand;
	}
}
