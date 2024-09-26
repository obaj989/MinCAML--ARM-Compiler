package compiler.typing;

import compiler.common.Type;

public class Equation {
    public Type t1 ;
    public Type t2 ;

    public Equation (Type t1, Type t2) {
        this.t1 = t1 ;
        this.t2 = t2 ;
    }
}
