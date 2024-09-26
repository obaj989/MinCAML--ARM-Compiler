package compiler.common.expression;

import java.util.*;

import compiler.common.Exp;
import compiler.common.ObjVisitor;
import compiler.common.Visitor;


public class Tuple extends Exp {
	public final List<Exp> es;

	public Tuple(List<Exp> es) {
        this.es = es;
    }

    public   <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }

    public  void accept(Visitor v) {
        v.visit(this);
    }
}