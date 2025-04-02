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
public class Assignment implements Expression, Node {

    private final Mutable mutable;
    private final AssignmentType type;
    private final Expression rhs;

    public Assignment(Mutable mutable, String assign, Expression rhs) {
        this.mutable = mutable;
        this.type = AssignmentType.fromString(assign);
        this.rhs = rhs;
    }

    public void toCminus(StringBuilder builder, final String prefix) {
        mutable.toCminus(builder, prefix);
        if (rhs != null) {
            builder.append(" ").append(type.toString()).append(" ");
            rhs.toCminus(builder, prefix);
        } else {
            builder.append(type.toString());

        }
    }

    @Override
    public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable,
            RegisterAllocator regAllocator) {
        // TODO: Apply to other operators
        symbolTable.wantsAddr();
        MIPSResult mutMips = mutable.toMIPS(code, data, symbolTable, regAllocator);
        code.append(String.format("# Compute rhs for assignment %s\n", type));
        MIPSResult exprMips = rhs.toMIPS(code, data, symbolTable, regAllocator);
                
        String mutAddrReg = mutMips.getAddress();
        String exprReg = exprMips.getRegister();

        code.append("# complete assignment statement with store\n");
        code.append(String.format("sw %s 0(%s)\n", exprReg, mutAddrReg));
        regAllocator.clear(exprReg);
        regAllocator.clear(mutAddrReg);

        return MIPSResult.createVoidResult();
    }
}
