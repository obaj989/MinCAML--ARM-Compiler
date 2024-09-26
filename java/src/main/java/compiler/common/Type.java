package compiler.common;

import java.util.ArrayList;

public class Type {
    private static int x = 0;

    public static Type gen() {
        return new TVar("?" + x++);
    }

    
    public static class TBool extends Type {
        @Override
        public String toString() {
            return ("TBool") ;
        }
     }

    public static class TInt extends Type { 
        @Override
        public String toString() {
            return ("TInt") ;
        }
    }

    public static class TFloat extends Type {
        @Override
        public String toString() {
            return ("TFloat") ;
        }

     }

    public static class TFun extends Type { 
        public Type returnType ;
        public ArrayList<Type> params ;

        @Override
        public String toString() {
            return ("TFun") ;
        }
    }

    public static class TUnit extends Type {
        @Override
        public String toString() {
            return ("TUnit") ;
        }
     }

    public static class TTuple extends Type {
        @Override
        public String toString() {
            return ("TTuple") ;
        }
     }

    public static class TArray extends Type { 
        @Override
        public String toString() {
            return ("TArray") ;
        }
    }

    public static class TString extends Type { 
        @Override
        public String toString() {
            return ("TString") ;
        }
    }

    public static class TVar extends Type {
        String v;

        public TVar(String v) {
            this.v = v;
        }

        @Override
        public String toString() {
            return v; 
        }
    }
}
