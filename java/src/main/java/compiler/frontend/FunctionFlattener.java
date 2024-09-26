package compiler.frontend;

import java.util.ArrayList;
import java.util.List;

import compiler.common.*;
import compiler.common.expression.*;

class FunctionFlattener {
	private List<ClosureFunDef> topLevelFunctions;

	public FunctionFlattener() {
		this.topLevelFunctions = new ArrayList<>();
	}

	public void flattenFunctions(Exp node) {
		// Implement the flattening logic here
	}
}
