package compiler.common.expression;

import compiler.common.Exp;
import compiler.common.ObjVisitor;
import compiler.common.Visitor;

public class Float extends Exp {
	public float f;

	public Float(float f) {
		this.f = f;
	}

	public <E> E accept(ObjVisitor<E> v) {
		return v.visit(this);
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
