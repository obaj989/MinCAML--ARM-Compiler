package compiler.asml;

import java.util.Collections;
import java.util.List;

public class CallExpression extends Expression {
	private String functionName;
	private List<Expression> arguments;

	public CallExpression(String functionName, List<Expression> arguments) {
		this.setFunctionName(functionName);
		this.functionName = functionName;
		if (arguments == null) arguments = Collections.emptyList();
		this.arguments = arguments;
	}

	public void accept(ASMLVisitor v) {
		v.visit(this);
	}

	public List<Expression> getArguments() {
		return arguments;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

}
