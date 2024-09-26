package compiler.frontend;

import java.util.*;


import compiler.common.*;
import compiler.common.expression.*;
import compiler.common.expression.Float;

public class K_normalization implements ObjVisitor<Exp>{


    @Override
    public Unit visit(Unit e) {
        return e;
    }

    public Bool visit(Bool e){
        return e;
    }

    public Int visit(Int e){
        return e;
    }

    public Float visit(Float e){
        return e;
    }

    public Let visit(Not e){
        
        Exp k = e.e.accept(this);
        Type t = Type.gen();  
        Id id = Id.gen();
        Let x1 = new Let(id, t, k, new Not(new Var(id)));
        return x1;
    }

    @Override
    public Let visit(Neg e) {
        
        Exp x = e.e.accept(this);
        Type t = Type.gen();
        Id id = Id.gen();
        return new Let(id, t, x, new Neg(new Var(id)));
    }

    public Let visit(Add e){
        
    	
        Exp k1 = e.e1.accept(this);
        Exp k2 = e.e2.accept(this);
        Id id1 = Id.gen();
        Id id2 = Id.gen();
        Type type1 = Type.gen();
        Type type2 = Type.gen();
        Add add = new Add(new Var(id1), new Var(id2));
        Let x2 = new Let(id1, type1, k2, add);
        Let x1 = new Let(id2, type2, k1, x2);
        return x1;
    }

    public Exp visit(Sub e){
        
        Exp k1 = e.e1.accept(this);
        Exp k2 = e.e2.accept(this);
        Id id1 = Id.gen();
        Id id2 = Id.gen();
        Type type1 = Type.gen();
        Type type2 = Type.gen();
        Sub sub = new Sub(new Var(id1), new Var(id2));
        Let x2 = new Let(id1, type1, k1, sub);
        Let x1 = new Let(id2, type2, k2, x2);
        return x1;
    }

    @Override
    public Exp visit(FNeg e) {
     
        Exp x = e.e.accept(this);
        Type t = Type.gen();
        Id id = Id.gen();
        Let x1 = new Let(id, t, x, new FNeg(new Var(id)));
        return x1;
    }

    @Override
    public Exp visit(FAdd e) {
        
        Exp k1 = e.e1.accept(this);
        Exp k2 = e.e2.accept(this);
        Id id1 = Id.gen();
        Id id2 = Id.gen();
        Type type1 = Type.gen();
        Type type2 = Type.gen();
        FAdd fadd = new FAdd(new Var(id1), new Var(id2));
        Let x2 = new Let(id1, type1, k1, fadd);
        Let x1 = new Let(id2, type2, k2, x2);
        return x1;
    }

    @Override
    public Exp visit(FSub e) {
     
        Exp k1 = e.e1.accept(this);
        Exp k2 = e.e2.accept(this);
        Id id1 = Id.gen();
        Id id2 = Id.gen();
        Type type1 = Type.gen();
        Type type2 = Type.gen();
        FSub fsub = new FSub(new Var(id1), new Var(id2));
        Let x2 = new Let(id1, type1, k1, fsub);
        Let x1 = new Let(id2, type2, k2, x2);
        return x1;
    }

    @Override
    public Exp visit(FMul e) {

        Exp k1 = e.e1.accept(this);
        Exp k2 = e.e2.accept(this);
        Id id1 = Id.gen();
        Id id2 = Id.gen();
        Type type1 = Type.gen();
        Type type2 = Type.gen();
        FMul fmul = new FMul(new Var(id1), new Var(id2));
        Let x2 = new Let(id1, type1, k1, fmul);
        Let x1 = new Let(id2, type2, k2, x2);
        return x1;
    }

    @Override
    public Exp visit(FDiv e) {
      
         
        Exp k1 = e.e1.accept(this);
        Exp k2 = e.e2.accept(this);
        Id id1 = Id.gen();
        Id id2 = Id.gen();
        Type type1 = Type.gen();
        Type type2 = Type.gen();
        FDiv fdiv = new FDiv(new Var(id1), new Var(id2));
        Let x2 = new Let(id1, type1, k1, fdiv);
        Let x1 = new Let(id2, type2, k2, x2);
        return x1;
    }

    @Override
    public Exp visit(Eq e) {
        Exp k1 = e.e1.accept(this);
        Exp k2 = e.e2.accept(this);
        Id id1 = Id.gen();
        Id id2 = Id.gen();
        Type type1 = Type.gen();
        Type type2 = Type.gen();
        Eq eq = new Eq(new Var(id1), new Var(id2));
        Let x2 = new Let(id1, type1, k1, eq);
        Let x1 = new Let(id2, type2, k2, x2);
        return x1;
    }

    @Override
    public Exp visit(LE e) {
        Exp k1 = e.e1.accept(this);
        Exp k2 = e.e2.accept(this);
        Id id1 = Id.gen();
        Id id2 = Id.gen();
        Type type1 = Type.gen();
        Type type2 = Type.gen();
        LE le = new LE(new Var(id1), new Var(id2));
        Let x2 = new Let(id1, type1, k1, le);
        Let x1 = new Let(id2, type2, k2, x2);
        return x1;    
    }

    @Override
    public Exp visit(Let e) {
    
    	Exp k1 = e.e1.accept(this);
		Exp k2 = e.e2.accept(this);
    	
    	Let let = new Let(e.id, e.t, k1, k2);
    	return let;
    	
    }

    @Override
    public Var visit(Var e) {

        return e;
    }

    @Override
    public Exp visit(LetRec e) {

    	Exp km = e.fd.e.accept(this);
    	Exp kn = e.e.accept(this);
    	FunDef fundef = new FunDef(e.fd.id, e.fd.type, e.fd.args, km);
    	LetRec letrec = new LetRec(fundef, kn);
        return letrec;
    }

    @Override
    public Exp visit(App e) {
    	
    	List<Exp> normalized = new ArrayList<Exp>();
    	List<Id> normalized_ids = new ArrayList<Id>();
    	List<Exp> normalized_vars = new ArrayList<Exp>();
    	
    	Exp current;
    	
    	if(Var.class.isInstance(e.e)) {
    		
        	for(Exp ex_i : e.es) {
        		Exp k_ex_i = ex_i.accept(this);
        		normalized.add(k_ex_i);
        		Id new_id = Id.gen();
        		normalized_ids.add(new_id);
        		normalized_vars.add(new Var(new_id));
        		
        	}  
        	current = new App(e.e, normalized_vars);
        	
        	for(int i = normalized.size() - 1; i>=0; i--) {
        		current = new Let(normalized_ids.get(i), Type.gen(), normalized.get(i), current);
        	}
        	
        	return current;
        	
    	}
    	else {
        	Id x1_id = Id.gen();
        	Exp x = e.e.accept(this);
        	
        	for(Exp ex_i : e.es) {
        		Exp k_ex_i = ex_i.accept(this);
        		normalized.add(k_ex_i);
        		Id new_id = Id.gen();
        		normalized_ids.add(new_id);
        		normalized_vars.add(new Var(new_id));
        		
        	}    
        	current = new App(new Var(x1_id), normalized_vars);
        	
        	for(int i = normalized.size() - 1; i>=0; i--) {
        		current = new Let(normalized_ids.get(i), Type.gen(), normalized.get(i), current);
        	}
        	
        	return new Let(x1_id, Type.gen(), x, current);
        	
    	}
    }

    @Override
    public Exp visit(Tuple e) {
    	
    	int length_es = e.es.size();
    	if (length_es == 0) {
    		
    	}
    	
    	List<Exp> ex_knorm = new ArrayList<Exp>();
    	for(Exp exp_k : e.es) {
    		Exp exp_kform = exp_k.accept(this);
    		ex_knorm.add(exp_k.accept(this)); 		
    	}
    	Exp current = new Tuple(ex_knorm);
    	
    	for(int i = length_es - 1; i>=0; i--) {
    		current = new Let(Id.gen(), Type.gen(), ex_knorm.get(i), current);
    	}
    	
        return current;
    }

    @Override
    public Exp visit(LetTuple e) {
    	
    	Exp m_kform = e.e1.accept(this);
    	Exp n_kform = e.e2.accept(this);
    	LetTuple let_tuple = new LetTuple(e.ids, e.ts, m_kform, n_kform);
        return let_tuple;
    }

    @Override
    public Exp visit(Array e) {
    	
    	Exp x_norm = e.e1.accept(this);
    	Exp y_norm = e.e2.accept(this);
    	Id x_id = Id.gen();
    	Id y_id = Id.gen();
    	Type x_type = Type.gen();
    	Type y_type = Type.gen();
    	Array array = new Array(new Var(x_id), new Var(y_id));
    	Let y = new Let(y_id, y_type, y_norm, array);
    	Let x = new Let(x_id, x_type, x_norm, y);
        return x;
    }

    @Override
    public Exp visit(Get e) {

    	Exp x1_knorm = e.e1.accept(this);
    	Exp x2_knorm = e.e2.accept(this);
    	Id x1_id = Id.gen();
    	Id x2_id = Id.gen();
    	Type x1_type = Type.gen();
    	Type x2_type = Type.gen();
    	Get get = new Get(new Var(x1_id), new Var(x2_id));
    	Let x2 = new Let(x2_id, x2_type, x2_knorm, get);
    	Let x1 = new Let(x1_id, x1_type, x1_knorm, x2);
        return x1;
    }

    @Override
    public Exp visit(Put e) {

    	Id x1_id = Id.gen();
    	Id x2_id = Id.gen();
    	Id x3_id = Id.gen();
    	Type x1_type = Type.gen();
    	Type x2_type = Type.gen();
    	Type x3_type = Type.gen();
    	Put put = new Put(new Var(x1_id), new Var(x2_id), new Var(x3_id));
    	Let x3 = new Let(x3_id, x3_type, e.e3.accept(this), put);
    	Let x2 = new Let(x2_id, x2_type, e.e2.accept(this), x3);
    	Let x1 = new Let(x1_id, x1_type, e.e1.accept(this), x2);
    	return x1;
    }

	@Override
	public Exp visit(If e){

        Exp result = new Unit();

        // Exp e1 = e.e1.accept(this); 
        if (Eq.class.isInstance(e.e1)) { // check if M1 = M2
            Eq eq = (Eq) e.e1;
            Exp e2 = eq.e1.accept(this); // x
            Id id2 = Id.gen();
            Type t2 = Type.gen();
            Exp e3 = eq.e2.accept(this); // y
            Id id3 = Id.gen();
            Type t3 = Type.gen();

            Exp e4 = e.e2.accept(this); // K(N1)
            Exp e5 = e.e3.accept(this); // K(N2)

            If if1 = new If(new Eq(e2,e3), e4, e5);
            Let let2 = new Let(id3,t3,e3,if1); // let y = K(M2) in if x = y then K(N1) else K(N2)
            Let let1 = new Let(id2,t2,e2,let2); // let x = K(M1) in let y = K(M2) in if x = y then K(N1) else K(N2)
            result = let1;
        }
        else if (Not.class.isInstance(e.e1)){ // it could be "not(eq)" which is "!=", or "not(LE)" which is ">"
            Not not = (Not) e.e1;
            if (Eq.class.isInstance(not.e)){ // M1 != M2
                Eq eq = (Eq) not.e;
                If if1 = new If(eq, e.e3, e.e2);
                result = if1.accept(this); // K(if M1 = M2 then N2 else N1)
            }
            else if (LE.class.isInstance(not.e)){ // M1 > M2
                LE le = (LE) not.e;
                If if1 = new If(le, e.e3, e.e2); // if M1 <= M2 then N2 else N1
                result = if1.accept(this); // K(if M1 = M2 then N2 else N1)
            }
        }
        else if (LE.class.isInstance(e.e1)) {// M1 <= M2

            LE le = (LE) e.e1;
            Exp e2 = le.e1.accept(this); // x
            Id id2 = Id.gen();
            Type t2 = Type.gen();
            Exp e3 = le.e2.accept(this); // y
            Id id3 = Id.gen();
            Type t3 = Type.gen();

            Exp e4 = e.e2.accept(this); // K(N1)
            Exp e5 = e.e3.accept(this); // K(N2)

            If if1 = new If(new LE(e2,e3), e4, e5);
            Let let2 = new Let(id3,t3,e3,if1); // let y = K(M2) in if x <= y then K(N1) else K(N2)
            Let let1 = new Let(id2,t2,e2,let2); // let x = K(M1) in let y = K(M2) in if x <= y then K(N1) else K(N2)
            result = let1;

        }
        else if (Bool.class.isInstance(e.e1)){ // if M is true or false
            Bool bool = (Bool) e.e1;
            Eq eq = new Eq(bool, new Bool(false)); // M = false
            If if1 = new If(eq, e.e3, e.e2); // if M = false then N2 else N1
            result = if1.accept(this); // K(if M = false then N2 else N1)
        }

        return result;
        
    }


}