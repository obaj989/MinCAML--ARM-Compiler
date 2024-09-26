package compiler.frontend;

import java.util.List;

import compiler.common.Exp;
import compiler.common.Id;
import compiler.common.Type;

public class Closure {

	public class Tuple_l<Id, Type> {
		private Id l;
		private Type t;

		public Tuple_l(Id id, Type type) {
			this.l = id;
			this.t = type;
		}

		public Id getId() {
			return l;
		}

		public Type getType() {
			return t;
		}
	}

	public class Tuple_var<Id, Type> {
		private Id id;
		private Type type;

		public Tuple_var(Id id, Type type) {
			this.id = id;
			this.type = type;
		}

		public Id getId() {
			return id;
		}

		public Type getType() {
			return type;
		}
	}

	public class ClosureFunDef {
		Tuple_l<Id, Type> name;
		List<Tuple_var<Id, Type>> args;
		List<Tuple_var<Id, Type>> formal_fv;
		Exp body;

		public ClosureFunDef(Tuple_l<Id, Type> name, List<Tuple_var<Id, Type>> args,
				List<Tuple_var<Id, Type>> formal_fv, Exp body) {
			this.name = name;
			this.args = args;
			this.formal_fv = formal_fv;
			this.body = body;

		}

		// get free variables
		public List<Tuple_var<Id, Type>> getFreeVariables() {
			return this.formal_fv;
		}

		// set free variables
		public void setFreeVariables(List<Tuple_var<Id, Type>> formal_fv) {
			this.formal_fv = formal_fv;
		}

		public Exp getBody() {
			return this.body;
		}

		public List<Tuple_var<Id, Type>> getArgs() {
			return this.args;
		}

		public Tuple_l<Id, Type> getName() {
			return this.name;
		}

	}

}