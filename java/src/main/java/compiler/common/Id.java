package compiler.common;

import java.util.Objects;


public class Id {
    String id;
    public Id(String id) {
        this.id = id;
    }

    public String generateLabel() {
    	
    	id = "_" + id;
        return id;
    }

	@Override
    public String toString() {
        return id;
    }
    static int x = -1;
    
    public static Id gen() {
        x++;
        return new Id("v" + x);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Id id = (Id) obj;
        return this.id.equals(id.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
