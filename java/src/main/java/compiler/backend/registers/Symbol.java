package compiler.backend.registers;

public class Symbol {
    private String name;
    private String type;
    private int stackOffset; // Offset from the frame pointer (for local variables)

    public Symbol(String name, String type) {
        this.name = name;
        this.type = type;
        this.stackOffset = 0; // Initialize to 0, can be adjusted during stack allocation
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getStackOffset() {
        return stackOffset;
    }

    public void setStackOffset(int offset) {
        this.stackOffset = offset;
    }
}
