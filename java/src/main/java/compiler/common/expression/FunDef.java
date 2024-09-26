package compiler.common.expression;

import java.util.*;

import compiler.common.Exp;
import compiler.common.Id;
import compiler.common.Type;


public class FunDef {
	public final Id id;
	public Type type;
	public final List<Id> args;
	public final Exp e;

	public  FunDef(Id id, Type t, List<Id> args, Exp e) {
        this.id = id;
        this.type = t;
        this.args = args;
        this.e = e;
    }
	
	public Exp getBody() {
        return e;
    }

    public List<Id> getArgs() {
        return args;
    }

    public Id getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

}