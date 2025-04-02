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
        MIPSResult rhsResult = rhs.toMIPS(code, data, symbolTable, regAllocator);
        String leftReg = lhsResult.getRegister();
        String rightReg = rhsResult.getRegister();

        System.out.println("lhs:" + lhs + "result" + lhsResult + "rhs:" + rhs + "result" + rhsResult);

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
