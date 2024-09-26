package compiler.frontend;

import java.util.ArrayList;
import java.util.List;

import compiler.common.*;
import compiler.common.expression.*;
import compiler.common.expression.Float;

class ClosureVisitor implements ObjVisitor<Exp> {
	private List<ClosureFunDef> topLevelFunctions = new ArrayList<>();
	private Exp restOfProg = new Unit();
	private boolean isInFunctionBody = false;

	public List<ClosureFunDef> getTopLevelFunctions() {
		return this.topLevelFunctions;
	}

	public Exp getRestOfProg() {
		return this.restOfProg;
	}

	@Override
	public Exp visit(Unit e) {

		if (!isInFunctionBody) {
			this.restOfProg = e;
		}
		return new Unit();
	}

	@Override
	public Exp visit(Bool e) {

		if (!isInFunctionBody) {
			this.restOfProg = e;
		}

		return new Bool(e.b);
	}

	@Override
	public Exp visit(Int e) {

		if (!isInFunctionBody) {
			this.restOfProg = e;
		}
		return new Int(e.i);
	}

	@Override
	public Exp visit(Float e) {

		if (!isInFunctionBody) {
			this.restOfProg = e;
		}
		return new Float(e.f);
	}

	@Override
	public Exp visit(Not e) {

		Exp e1 = e.e.accept(this);
		if (!isInFunctionBody) {
			this.restOfProg = new Not(e1);
		}
		return new Not(e1);
	}

	@Override
	public Exp visit(Neg e) {

		Exp e1 = e.e.accept(this);
		if (!isInFunctionBody) {

			this.restOfProg = new Neg(e1);
		}
		return new Neg(e1);
	}

	@Override
	public Exp visit(Add e) {

		Exp e1 = e.e1.accept(this);
		Exp e2 = e.e2.accept(this);
		if (!isInFunctionBody) {
			this.restOfProg = new Add(e1, e2);
		}
		return new Add(e1, e2);
	}

	@Override
	public Exp visit(Sub e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(FNeg e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(FAdd e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(FSub e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(FMul e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(FDiv e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(Eq e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(LE e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(If e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(Let e) {

		Exp e1 = e.e1.accept(this);
		Exp e2 = e.e2.accept(this);
		this.restOfProg = new Let(e.id, e.t, e1, e2);
		return new Let(e.id, e.t, e1, e2);
	}

	@Override
	public Exp visit(Var e) {
		if (!isInFunctionBody) {
			this.restOfProg = new Var(e.id);
		}
		return new Var(e.id);
	}

	@Override
	public Exp visit(LetRec e) {

		isInFunctionBody = true;

		// we add the function to the topLevelFunctions
		ClosureFunDef closureFunDef = new ClosureFunDef(e.fd.getId(), e.fd.getType(), e.fd.getArgs(), e.fd.e, null);

		topLevelFunctions.add(closureFunDef);

		// we visit the body of the function
		Exp body = e.e.accept(this);

		isInFunctionBody = false;

		// we create a new function with the body visited
		// ClosureFunDef newClosureFunDef = new ClosureFunDef(e.fd.getId(),
		// e.fd.getType(), e.fd.getArgs(), e.fd.e, body);
		return new LetRec(e.fd, body);
	}

	@Override
	public Exp visit(App e) {

		Exp e1 = e.e.accept(this);
		List<Exp> args = new ArrayList<Exp>();
		for (Exp exp : e.es) {
			args.add(exp.accept(this));
		}
		this.restOfProg = new App(e1, args);

		return new App(e1, args);
	}

	@Override
	public Exp visit(Tuple e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(LetTuple e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(Array e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(Get e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exp visit(Put e) {
		// TODO Auto-generated method stub
		return null;
	}

	public Exp visit(ClosureFunDef closureFunDef) {
		if (!closureFunDef.isTopLevel()) {
			closureFunDef.setTopLevel(true);
			identifyAndSetFreeVariables(closureFunDef);
			topLevelFunctions.add(closureFunDef);
		}
		return null;
	}

	private void identifyAndSetFreeVariables(ClosureFunDef closureFunDef) {
		// Implement logic to identify and set free variables for funDef
		// This will likely involve traversing funDef.e and checking variable usage
	}

}
