package compiler.frontend;

import java.util.*;

import compiler.common.*;
import compiler.common.expression.*;
import compiler.common.expression.Float;


public class NestedLet implements ObjVisitor<Exp> {
    
    @Override
    public Exp visit(Unit e){

        return new Unit();
    }

    @Override
    public Exp visit(Bool e){

        return new Bool(e.b);

    }

    @Override
    public Exp visit(Int e){

        return new Int(e.i);
    }

    @Override
    public Exp visit(Float e){

        return new Float(e.f);
    }

    @Override
    public Exp visit(Not e){
        return new Not(e.e.accept(this));
    }

    @Override
    public Exp visit(Neg e){
        return new Neg(e.e.accept(this));
    }

    @Override
    public Exp visit(Add e) {
        
        Exp leftNormalized = e.e1.accept(this);
        Exp rightNormalized = e.e2.accept(this);

        Id tempLeftId, tempRightId;
        Let tempLeftLet = null, tempRightLet = null;

        if (!(leftNormalized instanceof Var || leftNormalized instanceof Int)) {
            tempLeftId = Id.gen();
            tempLeftLet = new Let(tempLeftId, null, leftNormalized, new Var(tempLeftId));
        } else {
            tempLeftId = null; // No need for a temporary variable
        }

        if (!(rightNormalized instanceof Var || rightNormalized instanceof Int)) {
            tempRightId = Id.gen();
            tempRightLet = new Let(tempRightId, null, rightNormalized, new Var(tempRightId));
        } else {
            tempRightId = null; // No need for a temporary variable
        }

        if (tempLeftLet != null && tempRightLet != null) {
            return new Let(tempLeftId, null, tempLeftLet, new Let(tempRightId, null, tempRightLet, new Add(new Var(tempLeftId), new Var(tempRightId))));
        } else if (tempLeftLet != null) {
            return new Let(tempLeftId, null, tempLeftLet, new Add(new Var(tempLeftId), rightNormalized));
        } else if (tempRightLet != null) {
            return new Let(tempRightId, null, tempRightLet, new Add(leftNormalized, new Var(tempRightId)));
        } else {
            return new Add(leftNormalized, rightNormalized);
        }


    }

    @Override
    public Exp visit(Sub e) {
        
        Exp leftNormalized = e.e1.accept(this);
        Exp rightNormalized = e.e2.accept(this);

        Id tempLeftId, tempRightId;
        Let tempLeftLet = null, tempRightLet = null;

        if (!(leftNormalized instanceof Var || leftNormalized instanceof Int)) {
            tempLeftId = Id.gen();
            tempLeftLet = new Let(tempLeftId, null, leftNormalized, new Var(tempLeftId));
        } else {
            tempLeftId = null; // No need for a temporary variable
        }

        if (!(rightNormalized instanceof Var || rightNormalized instanceof Int)) {
            tempRightId = Id.gen();
            tempRightLet = new Let(tempRightId, null, rightNormalized, new Var(tempRightId));
        } else {
            tempRightId = null; // No need for a temporary variable
        }

        if (tempLeftLet != null && tempRightLet != null) {
            return new Let(tempLeftId, null, tempLeftLet, new Let(tempRightId, null, tempRightLet, new Sub(new Var(tempLeftId), new Var(tempRightId))));
        } else if (tempLeftLet != null) {
            return new Let(tempLeftId, null, tempLeftLet, new Sub(new Var(tempLeftId), rightNormalized));
        } else if (tempRightLet != null) {
            return new Let(tempRightId, null, tempRightLet, new Sub(leftNormalized, new Var(tempRightId)));
        } else {
            return new Sub(leftNormalized, rightNormalized);
        }
    }

    @Override
    public Exp visit(FNeg e){

        Exp newE = e.e.accept(this);
        return new FNeg(newE);
    }

    @Override
    public Exp visit(FAdd e) {
        
        Exp leftNormalized = e.e1.accept(this);
        Exp rightNormalized = e.e2.accept(this);

        Id tempLeftId, tempRightId;
        Let tempLeftLet = null, tempRightLet = null;

        if (!(leftNormalized instanceof Var || leftNormalized instanceof Float)) {
            tempLeftId = Id.gen();
            tempLeftLet = new Let(tempLeftId, null, leftNormalized, new Var(tempLeftId));
        } else {
            tempLeftId = null; // No need for a temporary variable
        }

        if (!(rightNormalized instanceof Var || rightNormalized instanceof Float)) {
            tempRightId = Id.gen();
            tempRightLet = new Let(tempRightId, null, rightNormalized, new Var(tempRightId));
        } else {
            tempRightId = null; // No need for a temporary variable
        }

        if (tempLeftLet != null && tempRightLet != null) {
            return new Let(tempLeftId, null, tempLeftLet, new Let(tempRightId, null, tempRightLet, new FAdd(new Var(tempLeftId), new Var(tempRightId))));
        } else if (tempLeftLet != null) {
            return new Let(tempLeftId, null, tempLeftLet, new FAdd(new Var(tempLeftId), rightNormalized));
        } else if (tempRightLet != null) {
            return new Let(tempRightId, null, tempRightLet, new FAdd(leftNormalized, new Var(tempRightId)));
        } else {
            return new FAdd(leftNormalized, rightNormalized);
        }
    }

    @Override
    public Exp visit(FSub e) {
        
        Exp leftNormalized = e.e1.accept(this);
        Exp rightNormalized = e.e2.accept(this);

        Id tempLeftId, tempRightId;
        Let tempLeftLet = null, tempRightLet = null;

        if (!(leftNormalized instanceof Var || leftNormalized instanceof Float)) {
            tempLeftId = Id.gen();
            tempLeftLet = new Let(tempLeftId, null, leftNormalized, new Var(tempLeftId));
        } else {
            tempLeftId = null; // No need for a temporary variable
        }

        if (!(rightNormalized instanceof Var || rightNormalized instanceof Float)) {
            tempRightId = Id.gen();
            tempRightLet = new Let(tempRightId, null, rightNormalized, new Var(tempRightId));
        } else {
            tempRightId = null; // No need for a temporary variable
        }

        if (tempLeftLet != null && tempRightLet != null) {
            return new Let(tempLeftId, null, tempLeftLet, new Let(tempRightId, null, tempRightLet, new FSub(new Var(tempLeftId), new Var(tempRightId))));
        } else if (tempLeftLet != null) {
            return new Let(tempLeftId, null, tempLeftLet, new FSub(new Var(tempLeftId), rightNormalized));
        } else if (tempRightLet != null) {
            return new Let(tempRightId, null, tempRightLet, new FSub(leftNormalized, new Var(tempRightId)));
        } else {
            return new FSub(leftNormalized, rightNormalized);
        }
    }


    @Override
    public Exp visit(FMul e) {
        
        Exp leftNormalized = e.e1.accept(this);
        Exp rightNormalized = e.e2.accept(this);

        Id tempLeftId, tempRightId;
        Let tempLeftLet = null, tempRightLet = null;

        if (!(leftNormalized instanceof Var || leftNormalized instanceof Float)) {
            tempLeftId = Id.gen();
            tempLeftLet = new Let(tempLeftId, null, leftNormalized, new Var(tempLeftId));
        } else {
            tempLeftId = null; // No need for a temporary variable
        }

        if (!(rightNormalized instanceof Var || rightNormalized instanceof Float)) {
            tempRightId = Id.gen();
            tempRightLet = new Let(tempRightId, null, rightNormalized, new Var(tempRightId));
        } else {
            tempRightId = null; // No need for a temporary variable
        }

        if (tempLeftLet != null && tempRightLet != null) {
            return new Let(tempLeftId, null, tempLeftLet, new Let(tempRightId, null, tempRightLet, new FMul(new Var(tempLeftId), new Var(tempRightId))));
        } else if (tempLeftLet != null) {
            return new Let(tempLeftId, null, tempLeftLet, new FMul(new Var(tempLeftId), rightNormalized));
        } else if (tempRightLet != null) {
            return new Let(tempRightId, null, tempRightLet, new FMul(leftNormalized, new Var(tempRightId)));
        } else {
            return new FMul(leftNormalized, rightNormalized);
        }
    }


    @Override
    public Exp visit(FDiv e) {
        
        Exp leftNormalized = e.e1.accept(this);
        Exp rightNormalized = e.e2.accept(this);

        Id tempLeftId, tempRightId;
        Let tempLeftLet = null, tempRightLet = null;

        if (!(leftNormalized instanceof Var || leftNormalized instanceof Float)) {
            tempLeftId = Id.gen();
            tempLeftLet = new Let(tempLeftId, null, leftNormalized, new Var(tempLeftId));
        } else {
            tempLeftId = null; // No need for a temporary variable
        }

        if (!(rightNormalized instanceof Var || rightNormalized instanceof Float)) {
            tempRightId = Id.gen();
            tempRightLet = new Let(tempRightId, null, rightNormalized, new Var(tempRightId));
        } else {
            tempRightId = null; // No need for a temporary variable
        }

        if (tempLeftLet != null && tempRightLet != null) {
            return new Let(tempLeftId, null, tempLeftLet, new Let(tempRightId, null, tempRightLet, new FDiv(new Var(tempLeftId), new Var(tempRightId))));
        } else if (tempLeftLet != null) {
            return new Let(tempLeftId, null, tempLeftLet, new FDiv(new Var(tempLeftId), rightNormalized));
        } else if (tempRightLet != null) {
            return new Let(tempRightId, null, tempRightLet, new FDiv(leftNormalized, new Var(tempRightId)));
        } else {
            return new FDiv(leftNormalized, rightNormalized);
        }
    }

    @Override
    public Exp visit(Eq e) {
        return new Eq(e.e1.accept(this), e.e2.accept(this));
    }


    @Override
    public Exp visit(LE e) {
        return new LE(e.e1.accept(this), e.e2.accept(this));   
    }

    
    @Override
    public Exp visit(If e) {
        
        Exp newE1 = e.e1.accept(this);
        Exp newE2 = e.e2.accept(this);
        Exp newE3 = e.e3.accept(this);
        return new If(newE1, newE2, newE3);
    }

    @Override
    public Exp visit(Let e) {
        
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);

        if (e1 instanceof Let) {
            Let let = (Let) e1;
            Let newLet = new Let(let.id, let.t, let.e1, new Let(e.id, e.t, let.e2, e2));
            return newLet.accept(this);
        } else {
            return new Let(e.id, e.t, e1, e2);
        }
        
    }

    @Override
    public Exp visit(Var e) {

        return new Var(e.id);
    }

    @Override
    public Exp visit(LetRec e) {
        
        // we don't need to do anything here, we only need to visit the body
        return new LetRec(e.fd, e.e.accept(this));
    }

    @Override
    public Exp visit(App e) {
        
        Exp func = e.e.accept(this);

        // Create a list to hold the normalized arguments
        List<Exp> normalizedArgs = new ArrayList<>();
    
        // Temporary variables for complex arguments
        List<Let> tempLets = new ArrayList<>();
    
        // Process each argument
        for (Exp arg : e.es) {
            Exp normalizedArg = arg.accept(this);
    
            // Check if the argument is a simple expression (variable or constant)
            if (normalizedArg instanceof Var || normalizedArg instanceof Int || normalizedArg instanceof Float) {
                normalizedArgs.add(normalizedArg);
            } else {
                // If the argument is a complex expression, create a temporary variable
                Id tempId = Id.gen();
                Let tempLet = new Let(tempId, null, normalizedArg, new Var(tempId));
                tempLets.add(tempLet);
                normalizedArgs.add(new Var(tempId));
            }
        }
    
        // Construct the final App expression with normalized arguments
        App finalApp = new App(func, normalizedArgs);
    
        // If temporary variables were introduced, nest the Let expressions
        Exp result = finalApp;
        for (int i = tempLets.size() - 1; i >= 0; i--) {
            Let tempLet = tempLets.get(i);
            result = new Let(tempLet.id, null, tempLet.e1, result);
        }
    
        return result;
    }

    @Override
    public Exp visit(Tuple e) {
        return new Unit();
        
    }

    @Override
    public Exp visit(LetTuple e) {
        return new Unit();
    }

    @Override
    public Exp visit(Array e) {
        return new Unit();
    }

    @Override
    public Exp visit(Get e) {
        return new Unit();
    }

    @Override
    public Exp visit(Put e) {
        return new Unit();
    }

	public Exp visit(ClosureFunDef closureFunDef) {
		// TODO Auto-generated method stub
		return null;
	}



}
