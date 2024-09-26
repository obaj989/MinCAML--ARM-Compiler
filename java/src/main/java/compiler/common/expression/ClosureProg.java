package compiler.common.expression;

import java.util.List;

import compiler.common.*;

public class ClosureProg {

	final List<ClosureFunDef> funDefs;
	final Exp e;

	public ClosureProg(List<ClosureFunDef> funDefs, Exp e) {
		this.funDefs = funDefs;
		this.e = e;

	}

	public Exp getMainBody() {
		// TODO Auto-generated method stub
		return this.e;
	}
}