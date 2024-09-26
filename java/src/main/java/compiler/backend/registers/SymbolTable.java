package compiler.backend.registers;

import java.util.*;


public class SymbolTable {
    private List<Scope> scopeStack; // Stack of scopes
    private int currentStackOffset; // Current stack offset for local variables
    private int framePointerOffset;
    private final int VARIABLE_SIZE = 4;// Offset from FP for the current function

    public SymbolTable() {
        this.scopeStack = new ArrayList<>();
        this.currentStackOffset = VARIABLE_SIZE;
        this.framePointerOffset = -1;
    }

    // Enter a new scope (e.g., at the beginning of a function)
    public void enterScope() {
    	this.framePointerOffset ++;
        scopeStack.add(new Scope(this.framePointerOffset));
    }

    // Exit the current scope (e.g., at the end of a function)
    public void exitScope() {
        scopeStack.remove(scopeStack.size() - 1);
        this.framePointerOffset --;
    }

    // Add a symbol to the current scope
    public void addSymbolToCurrentScope(String name, String type) {
        Scope currentScope = scopeStack.get(scopeStack.size() - 1);
        Symbol symbol = new Symbol(name, type);
        symbol.setStackOffset(currentStackOffset);
        currentScope.addSymbol(symbol);
        currentStackOffset += VARIABLE_SIZE/* Calculate the size of the symbol's storage */;
    }

    // Check if a symbol exists in the current scope or any enclosing scopes
    public boolean containsSymbol(String name) {
        for (int i = scopeStack.size() - 1; i >= 0; i--) {
            if (scopeStack.get(i).containsSymbol(name)) {
                return true;
            }
        }
        return false;
    }

    // Retrieve the type of a symbol
    public String getSymbolType(String name) {
        for (int i = scopeStack.size() - 1; i >= 0; i--) {
            Scope currentScope = scopeStack.get(i);
            if (currentScope.containsSymbol(name)) {
                return currentScope.getSymbol(name).getType();
            }
        }
        throw new RuntimeException("Symbol not found: " + name);
    }

    // Retrieve the stack offset of a symbol (for local variables)
    public int getSymbolStackOffset(String name) {
        for (int i = scopeStack.size() - 1; i >= 0; i--) {
            Scope currentScope = scopeStack.get(i);
            if (currentScope.containsSymbol(name)) {
                return currentScope.getSymbol(name).getStackOffset();
            }
        }
        throw new RuntimeException("Symbol not found: " + name);
    }
    
    public Scope getScopeOfSymbol(String name) {
    	for (int i = scopeStack.size() - 1; i >= 0; i--) {
            Scope currentScope = scopeStack.get(i);
            if (currentScope.containsSymbol(name)) {
                return currentScope;
            }
        }
        throw new RuntimeException("Symbol not found in any scope: " + name);
    }

    // Other methods for symbol management and scope handling can be added here
}
