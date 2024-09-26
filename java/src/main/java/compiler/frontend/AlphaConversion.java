package compiler.frontend;

import java.util.*;

import compiler.common.Exp;
import compiler.common.Id;
import compiler.common.ObjVisitor;
import compiler.common.expression.*;
import compiler.common.expression.Float;

public class AlphaConversion implements ObjVisitor<Exp> {

	private Map<Id, Id> map = new HashMap<>(); // map from old to new variables

	@Override
	public Exp visit(Unit e) {

		return new Unit();
	}

	@Override
	public Exp visit(Bool e) {

		return new Bool(e.b);

	}

	@Override
	public Exp visit(Int e) {

		return new Int(e.i);
	}

	@Override
	public Exp visit(Float e) {

		return new Float(e.f);
	}

	@Override
	public Exp visit(Not e) {
		return new Not(e.e.accept(this));
	}

	@Override
	public Exp visit(Neg e) {
		return new Neg(e.e.accept(this));
	}

	@Override
	public Exp visit(Add e) {
		Exp newE1 = e.e1.accept(this);
		Exp newE2 = e.e2.accept(this);

		// Return a new Add expression with the transformed operands
		return new Add(newE1, newE2);

	}
    @Override
    public Exp visit(Sub e) {
        
        Exp newE1 = e.e1.accept(this);
        Exp newE2 = e.e2.accept(this);

        return new Sub(newE1, newE2);
    }


    @Override
    public Exp visit(FNeg e){

        Exp newE = e.e.accept(this);
        return new FNeg(newE);

    }


    @Override
    public Exp visit(FAdd e) {
        
        Exp newE1 = e.e1.accept(this);
        Exp newE2 = e.e2.accept(this);

        return new FAdd(newE1, newE2);    
    }


    @Override
    public Exp visit(FSub e) {
        Exp newE1 = e.e1.accept(this);
        Exp newE2 = e.e2.accept(this);

        return new FSub(newE1, newE2);
    }


    @Override
    public Exp visit(FMul e) {
        Exp newE1 = e.e1.accept(this);
        Exp newE2 = e.e2.accept(this);

        return new FMul(newE1, newE2);
    }

    @Override
    public Exp visit(FDiv e) {
        Exp newE1 = e.e1.accept(this);
        Exp newE2 = e.e2.accept(this);

        return new FDiv(newE1, newE2);
    }

    @Override
    public Exp visit(Eq e) {
        Exp newE1 = e.e1.accept(this);
        Exp newE2 = e.e2.accept(this);
        return new Eq(newE1, newE2);
    }


    @Override
    public Exp visit(LE e) {
        Exp newE1 = e.e1.accept(this);
        Exp newE2 = e.e2.accept(this);
        return new LE(newE1, newE2);
        
    }


    @Override
    public Exp visit(If e) {
        Exp newE1 = e.e1.accept(this); // ε(x) = ε(y)
        Exp newE2 = e.e2.accept(this); // αε(M1)
        Exp newE3 = e.e3.accept(this); // αε(M2)
        return new If(newE1, newE2, newE3);

    }


	@Override
	public Exp visit(Let e) {

		Id newVar = Id.gen();

		map.put(e.id, newVar);

		// Recursively apply alpha conversion to the subexpressions
		Exp newE1 = e.e1.accept(this);
		Exp newE2 = e.e2.accept(this);

		// Create and return a new Let expression with the new variable name
		return new Let(newVar, e.t, newE1, newE2);
	}

	@Override
	public Exp visit(Var e) {

		if (map.containsKey(e.id)) {
			return new Var(map.get(e.id));
		} else {
			return new Var(e.id);
		}

	}

	@Override
	public Exp visit(LetRec e) {

		// we rename the arguments of the function
		List<Id> newArgs = new ArrayList<>();
		for (Id arg : e.fd.args) {
			Id newArg = Id.gen();
			map.put(arg, newArg);
			newArgs.add(newArg);
		}

		// recursively apply alpha conversion to the subexpressions
		Exp newE = e.fd.e.accept(this);

		FunDef newFd = new FunDef(e.fd.id, e.fd.type, newArgs, newE);

		Exp newE2 = e.e.accept(this);

		return new LetRec(newFd, newE2);

	}

	@Override
	public Exp visit(App e) {

		// recursively apply alpha conversion to the subexpressions
		Exp newE = e.e.accept(this);

		List<Exp> newEs = new ArrayList<>();
		for (Exp exp : e.es) {
			newEs.add(exp.accept(this));
		}

		return new App(newE, newEs);

	}

	@Override
	public Exp visit(Tuple e) {
		return new Unit();

	}

	@Override
	public Exp visit(LetTuple e) {
		return new Unit();
	}

	@Override
	public Exp visit(Array e) {
		return new Unit();
	}

	@Override
	public Exp visit(Get e) {
		return new Unit();
	}

	@Override
	public Exp visit(Put e) {
		return new Unit();
	}

	public Exp visit(ClosureFunDef closureFunDef) {
		// TODO Auto-generated method stub
		return null;
	}

}
