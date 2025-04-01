/*
 * Code formatter project
 * CS 4481
 */
package submit.ast;

import submit.MIPSResult;
import submit.RegisterAllocator;
import submit.SymbolTable;

/**
 *
 * @author edwajohn
 */
public class CharConstant implements Expression {

    private final char value;

    public CharConstant(char value) {
        this.value = value;
    }

    public void toCminus(StringBuilder builder, final String prefix) {
        builder.append("'").append(value).append("'");
    }

    @Override
    public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable,
            RegisterAllocator regAllocator) {
        // TODO Auto-generated method stub
        return null;
    }

}
