package compiler.backend.registers;

public class Register {
    private final String name;
    private int value;
    private boolean isAvailable;
    
    
    public static final Register R0=new Register("r0");
	public static final Register R1=new Register("r1");
	public static final Register R2=new Register("r2");
	public static final Register R3=new Register("r3");
	public static final Register R4=new Register("r4");
	public static final Register R5=new Register("r5");
	public static final Register R6=new Register("r6");
	public static final Register R7=new Register("r7");
	public static final Register R8=new Register("r8");
	public static final Register R9=new Register("r0");
	public static final Register R10=new Register("r10");
	public static final Register R11=new Register("r11");
	public static final Register R12=new Register("r12");
	public static final Register R13=new Register("r13");
	public static final Register R14=new Register("r14");
	public static final Register R15=new Register("r15");


	
	public static final Register FP=R11;
	public static final Register SP=R13;
	public static final Register LR=R14;
	public static final Register PC=R15;

    public Register(String name) {
        this.name = name;
        this.isAvailable = true;
    }

    public void allocate(int value) {
        this.value = value;
        this.isAvailable = false;
    }

    public void deallocate() {
        this.isAvailable = true;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public String toString()
	{
	
		if(this==FP)
			return "fp";
		if(this==SP)
			return "sp";
		if(this==LR)
			return "lr";
		if(this==PC)
			return "pc";
		return name; 
	}
	
}
