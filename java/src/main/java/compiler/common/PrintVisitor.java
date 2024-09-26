package compiler.common;

import java.util.*;

import compiler.common.expression.Float;
import compiler.common.expression.*;
import compiler.common.expression.Unit;

public class PrintVisitor implements Visitor {
	public void visit(Unit e) {
	}

	public void visit(Bool e) {
	}

	public void visit(Int e) {
	}

	public void visit(Float e) {
		String s = String.format("%.2f", e.f);
	}

	public void visit(Not e) {
		e.e.accept(this);
	}

	public void visit(Neg e) {
		e.e.accept(this);
	}

	public void visit(Add e) {
		e.e1.accept(this);
		e.e2.accept(this);
	}

	public void visit(Sub e) {
		e.e1.accept(this);
		e.e2.accept(this);
	}

	public void visit(FNeg e) {
		e.e.accept(this);
	}

	public void visit(FAdd e) {
		e.e1.accept(this);
		e.e2.accept(this);
	}

	public void visit(FSub e) {
		e.e1.accept(this);
		e.e2.accept(this);
	}

	public void visit(FMul e) {
		e.e1.accept(this);
		e.e2.accept(this);
	}

	public void visit(FDiv e) {
		e.e1.accept(this);
		e.e2.accept(this);
	}

	public void visit(Eq e) {
		e.e1.accept(this);
		e.e2.accept(this);
	}

	public void visit(LE e) {
		e.e1.accept(this);
		e.e2.accept(this);
	}

	public void visit(If e) {
		e.e1.accept(this);
		e.e2.accept(this);
		e.e3.accept(this);
	}

	public void visit(Let e) {

		e.e1.accept(this);
		e.e2.accept(this);
	}

	public void visit(Var e) {
	}

	// print sequence of identifiers
	static <E> void printInfix(List<E> l, String op) {
		if (l.isEmpty()) {
			return;
		}
		Iterator<E> it = l.iterator();
		while (it.hasNext()) {
		}
	}

	// print sequence of Exp
	void printInfix2(List<Exp> l, String op) {
		if (l.isEmpty()) {
			return;
		}
		Iterator<Exp> it = l.iterator();
		it.next().accept(this);
		while (it.hasNext()) {
			it.next().accept(this);
		}
	}

	public void visit(LetRec e) {
		printInfix(e.fd.args, " ");
		e.fd.e.accept(this);
		e.e.accept(this);
	}

	public void visit(App e) {
		e.e.accept(this);
		printInfix2(e.es, " ");
	}

	public void visit(Tuple e) {
		printInfix2(e.es, ", ");
	}

	public void visit(LetTuple e) {
		printInfix(e.ids, ", ");
		e.e1.accept(this);
		e.e2.accept(this);
	}

	public void visit(Array e) {
		e.e1.accept(this);
		e.e2.accept(this);
	}

	public void visit(Get e) {
		e.e1.accept(this);
		e.e2.accept(this);
	}

	public void visit(Put e) {
		e.e1.accept(this);
		e.e2.accept(this);
		e.e3.accept(this);
	}
}
