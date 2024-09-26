package compiler.common.expression;


import compiler.common.Exp;
import compiler.common.Id;
import compiler.common.Type;
import compiler.common.ObjVisitor;
import compiler.common.Visitor;

public class Var extends Exp {
	public final Id id;

	public Var(Id id) {
		this.id = id;
	}

	public <E> E accept(ObjVisitor<E> v) {
		return v.visit(this);
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
