package compiler.asml;

import compiler.common.ObjVisitor;
import compiler.common.Visitor;

public class ConditionalExpression extends Expression {
	private Expression condition;
	private Expression thenBranch;
	private Expression elseBranch;

	public void accept(ASMLVisitor v) {
		v.visit(this);
	}

	public Expression getElseBranch() {
		return elseBranch;
	}
	public void setElseBranch(Expression elseBranch) {
		this.elseBranch = elseBranch;
	}
	public Expression getThenBranch() {
		return thenBranch;
	}
	public void setThenBranch(Expression thenBranch) {
		this.thenBranch = thenBranch;
	}
	public Expression getCondition() {
		return condition;
	}
	public void setCondition(Expression condition) {
		this.condition = condition;
	}

}