package compiler.asml;

import java.util.ArrayList;
import java.util.List;

public class Program {
    private List<Expression> expressions;

    public Program() {
        this.expressions = new ArrayList<>();
    }

    public void addExpression(Expression expression) {
        expressions.add(expression);
    }

	public List<Expression> getExpressions() {
		return expressions;
	}

}
