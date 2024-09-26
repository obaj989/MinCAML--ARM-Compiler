package compiler.asml;

import compiler.common.ObjVisitor;
import compiler.common.Visitor;

public abstract class Expression {

	public  abstract void accept(ASMLVisitor v);
}

