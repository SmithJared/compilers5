/*
 * Code formatter project
 * CS 4481
 */
package submit.ast;

import submit.MIPSResult;
import submit.RegisterAllocator;
import submit.SymbolInfo;
import submit.SymbolTable;

/**
 *
 * @author edwajohn
 */
public class Mutable implements Expression, Node {

    private final String id;
    private final Expression index;

    public Mutable(String id, Expression index) {
        this.id = id;
        this.index = index;
    }

    public String getId() {
        return id;
    }

    @Override
    public void toCminus(StringBuilder builder, String prefix) {
        builder.append(id);
        if (index != null) {
            builder.append("[");
            index.toCminus(builder, prefix);
            builder.append("]");
        }
    }

    @Override
    public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable,
            RegisterAllocator regAllocator) {
        // TODO: Implement Arrays
        if(index != null){
            return null;
        }
        
        code.append(String.format("# Get %s's offset from $sp from the symbol table and initialize %s's address with it. We'll add $sp later.\n", id, id));
        String reg = regAllocator.getAny();
        int offset = symbolTable.getOffset(id);
        code.append(String.format("li %s %d\n", reg, offset));

        code.append("# Add the stack pointer address to the offset.\n");
        code.append(String.format("add %s %s $sp\n", reg, reg));
        symbolTable.idInRegister(reg, id); 

        if(stored){// Value had already been stored
            code.append(String.format("# Load the value of %s.", id));
        }

        return MIPSResult.createAddressResult(id, null);
    }

}
