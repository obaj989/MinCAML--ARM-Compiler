package compiler.asml;

import java.util.*;

public class PrintVisitorASML implements ASMLVisitor {

	@Override
	public void visit(ValueExpression e) {
	}

	@Override
	public void visit(BinaryOperation e) {

		String op = e.getOperator();
		e.getLeftOperand().accept(this);
		e.getRightOperand().accept(this);

	}

	// print sequence of Expression
	void printSequence(List<Expression> expressions, String op) {

		if (expressions.size() == 0) {
			return;
		}

		Iterator<Expression> it = expressions.iterator();
		it.next().accept(this);
		while (it.hasNext()) {
			it.next().accept(this);
		}
	}

	// print sequence of identifiers
	static <E> void printInfix(List<E> l, String op) {

		if (l.isEmpty()) {
			return;
		}

		Iterator<E> it = l.iterator();
		while (it.hasNext()) {
			System.out.println(op + it.next());
		}
	}

	@Override
	public void visit(CallExpression e) {
		printSequence(e.getArguments(), " ");

	}

	@Override
	public void visit(VariableExpression e) {
	}

	@Override
	public void visit(LetExpression e) {
		e.getAssignment().accept(this);
		e.getBody().accept(this);

	}

	@Override
	public void visit(ConditionalExpression e) {
	}

	@Override
	public void visit(MainFunction e) {
	}

	@Override
	public void visit(FunctionCallExpression e) {
	}

	@Override
	public void visit(FunctionDefinition e) {
		printInfix(e.getParameters(), " ");
		printSequence(e.getBody(), " ");

	}

}
