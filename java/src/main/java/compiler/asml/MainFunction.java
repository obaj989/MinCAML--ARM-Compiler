package compiler.asml;

import java.util.ArrayList;
import java.util.List;

public class MainFunction extends Expression {
	private List<Expression> expressions;

	public MainFunction() {
		this.expressions = new ArrayList<>();
	}

	public void addExpression(Expression expression) {
		expressions.add(expression);
	}

	public void accept(ASMLVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("let _ =\n");
		for (Expression expression : expressions) {
			sb.append("  ");
			sb.append(expression.toString());
			sb.append("\n");
		}
		return sb.toString();
	}

	public List<Expression> getExpressions() {
		return expressions;
	}
}
