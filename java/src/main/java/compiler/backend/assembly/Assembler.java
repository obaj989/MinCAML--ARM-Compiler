package compiler.backend.assembly;

import compiler.backend.registers.Register;

public class Assembler {

    public static String Prologue(int size) {
        return COMMENT("Start: Prologue") +
               SUB(Register.SP, Register.SP, 4) +
               Store(Register.FP, Register.SP) +
               MOV(Register.FP, Register.SP) +
               SUB(Register.SP, Register.SP, size) +
               COMMENT("End: Prologue");
    }
    
    public static String Epilogue() {
        return COMMENT("Start: Epilogue") +
               MOV(Register.SP, Register.FP) +
               Load(Register.FP, Register.SP) +
               ADD(Register.SP, Register.SP, 4) +
               COMMENT("End: Epilogue");
    }

    // Stack operations
    public static String Push(Register r) {
        return SUB(Register.SP, Register.SP, 4) + Store(r, Register.SP);
    }

    public static String Pop(int size) {
        return ADD(Register.SP, Register.SP, size);
    }

    // Add a comment
    public static String COMMENT(String comment) {
        return "\n\t@" + comment;
    }

    // Move data
    public static String MOV(Register target, Register source) {
        return "\n\tmov\t" + target + ", " + source;
    }

    public static String MOV(Register target, int val) {
        return "\n\tmov\t" + target + ", #" + val;
    }

    // Arithmetic operations
    public static String ADD(Register outputReg, Register r1, Register r2) {
        return "\n\tadd\t" + outputReg + ", " + r1 + ", " + r2;
    }

    public static String ADD(Register outputReg, Register reg, int val) {
        return "\n\tadd\t" + outputReg + ", " + reg + ", #" + val;
    }

    public static String SUB(Register outputReg, Register r1, Register r2) {
        return "\n\tsub\t" + outputReg + ", " + r1 + ", " + r2;
    }

    public static String SUB(Register outputReg, Register reg, int val) {
        return "\n\tsub\t" + outputReg + ", " + reg + ", #" + val;
    }

    // Memory operations
    public static String Store(Register source, Register target) {
        return "\n\tstr\t" + source + ", [" + target + "]";
    }

    public static String Store(Register source, Register target, int offset) {
        return "\n\tstr\t" + source + ", [" + target + ", #" + offset + "]";
    }

    public static String Load(Register target, Register source) {
        return "\n\tldr\t" + target + ", [" + source + "]";
    }

    public static String Load(Register target, Register source, int offset) {
        return "\n\tldr\t" + target + ", [" + source + ", #" + offset + "]";
    }

    // Branching
    public static String BranchToLabel(String label) {
        return "\n\tbl\t" + label + "\n";
    }

    // Additional assembly utilities can be added here
}
