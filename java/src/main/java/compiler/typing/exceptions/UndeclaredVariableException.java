package compiler.typing.exceptions;

import compiler.common.expression.Var;

public class UndeclaredVariableException extends RuntimeException {
	
    public UndeclaredVariableException(Var expr) {
        super("\u001B[31m Error! Undeclared variable " + expr.id.toString() + " is being used. !\u001B[0m");
    }
}
