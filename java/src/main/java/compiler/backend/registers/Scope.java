package compiler.backend.registers;

import java.util.HashMap;

public class Scope {
    private HashMap<String, Symbol> symbolMap;
    private int framePointerOffset;

    public Scope(int framePointerOffset) {
        this.symbolMap = new HashMap<>();
        this.setFramePointerOffset(framePointerOffset);
    }

    public void addSymbol(Symbol symbol) {
        symbolMap.put(symbol.getName(), symbol);
    }

    public boolean containsSymbol(String name) {
        return symbolMap.containsKey(name);
    }

    public Symbol getSymbol(String name) {
        return symbolMap.get(name);
    }

	public int getFramePointerOffset() {
		return framePointerOffset;
	}

	private void setFramePointerOffset(int framePointerOffset) {
		this.framePointerOffset = framePointerOffset;
	}
}
