package compiler.backend.registers;


public class SymbolTableEntry {
	
    private String name;
    private int offset;
    private Register register; // Register assignment (after register allocation)
    
    public SymbolTableEntry(String name, int offset, Register register) {
    	this.name = name;
    	this.offset = offset;
    	this.register = register;
    }
    
    public SymbolTableEntry(String name, int offset) {
    	this.name = name;
    	this.offset = offset;
    }
    
    public SymbolTableEntry(String name, Register register) {
    	this.name = name;
    	this.register = register;
    }
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public Register getRegister() {
		return register;
	}
	public void setRegister(Register register) {
		this.register = register;
	}

}
