package compiler.common.expression;

import java.util.*;

import compiler.common.Exp;
import compiler.common.ObjVisitor;
import compiler.common.Visitor;



public class Unit extends Exp {
    public <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
