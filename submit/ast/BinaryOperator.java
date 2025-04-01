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
public class BinaryOperator implements Expression, AbstractNode {

    private final Expression lhs, rhs;
    private final BinaryOperatorType type;

    public BinaryOperator(Expression lhs, BinaryOperatorType type, Expression rhs) {
        this.lhs = lhs;
        this.type = type;
        this.rhs = rhs;
    }

    public BinaryOperator(Expression lhs, String type, Expression rhs) {
        this.lhs = lhs;
        this.type = BinaryOperatorType.fromString(type);
        this.rhs = rhs;
    }

    @Override
    public void toCminus(StringBuilder builder, String prefix) {
        lhs.toCminus(builder, prefix);
        builder.append(" ").append(type).append(" ");
        rhs.toCminus(builder, prefix);
    }

    @Override
    public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable,
            RegisterAllocator regAllocator) {
        MIPSResult lhsResult = lhs.toMIPS(code, data, symbolTable, regAllocator);
        if(lhsResult.getRegister() == null){
            String id = lhsResult.getAddress();
            String 
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
        }


        MIPSResult rhsResult = rhs.toMIPS(code, data, symbolTable, regAllocator);
        String leftReg = lhsResult.getRegister();
        String rightReg = rhsResult.getRegister();

        System.out.println("lhs:" + lhs + "result" + lhsResult + "rhs:" + rhs + "result" + rhsResult);
        // If value is in memory then load it first
        if(leftReg == null){
            leftReg = lhsResult.getAddress();
            code.append(String.format("lw %s 0(%s)", ))
        }

        switch (type) {
            case PLUS:
                code.append(String.format("add %s %s %s\n", leftReg, leftReg, rightReg));
                break;
            case MINUS:   
                code.append(String.format("sub %s %s %s\n", leftReg, leftReg, rightReg));
                break;
            case TIMES:   
                code.append(String.format("mult %s %s\n", leftReg, rightReg));
                code.append(String.format("mflo %s\n", leftReg));
                break;
            case DIVIDE:   
                code.append(String.format("div %s %s\n", leftReg, rightReg));
                code.append(String.format("mflo %s\n", leftReg));
                break;
            default:
                return null;
        }

        regAllocator.clear(rightReg);
        return MIPSResult.createRegisterResult(leftReg, lhsResult.getType());
    }

}
