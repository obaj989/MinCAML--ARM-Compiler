package compiler.common.expression;

import java.util.*;

import compiler.common.Exp;
import compiler.common.Id;
import compiler.common.ObjVisitor;
import compiler.common.Type;
import compiler.common.Visitor;

public class LetTuple extends Exp {
	public final List<Id> ids;
	public final List<Type> ts;
	public final Exp e1;
	public final Exp e2;

	public LetTuple(List<Id> ids, List<Type> ts, Exp e1, Exp e2) {
		this.ids = ids;
		this.ts = ts;
		this.e1 = e1;
		this.e2 = e2;
	}

	public <E> E accept(ObjVisitor<E> v) {
		return v.visit(this);
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
