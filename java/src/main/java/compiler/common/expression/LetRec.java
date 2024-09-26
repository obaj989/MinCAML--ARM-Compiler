package compiler.common.expression;

import compiler.common.Exp;
import compiler.common.ObjVisitor;
import compiler.common.Visitor;

public class LetRec extends Exp {
	public final FunDef fd;
	public final Exp e;

	public LetRec(FunDef fd, Exp e) {
		this.fd = fd;
		this.e = e;
	}

	public <E> E accept(ObjVisitor<E> v) {
		return v.visit(this);
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}