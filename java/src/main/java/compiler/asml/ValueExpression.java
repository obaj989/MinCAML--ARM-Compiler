package compiler.asml;

public class ValueExpression extends Expression {
	private int value;

	public ValueExpression(int value) {
		this.setValue(value);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public void accept(ASMLVisitor v) {
		v.visit(this);
	}

}
