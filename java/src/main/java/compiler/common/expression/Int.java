package compiler.common.expression;

import compiler.common.Exp;
import compiler.common.ObjVisitor;
import compiler.common.Visitor;

public class Int extends Exp {
	public final int i;

	public Int(int i) {
		this.i = i;
	}

	public <E> E accept(ObjVisitor<E> v) {
		return v.visit(this);
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
