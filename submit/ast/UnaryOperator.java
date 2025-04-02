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
public class UnaryOperator implements Expression {

    private final UnaryOperatorType type;
    private final Expression expression;

    public UnaryOperator(String type, Expression expression) {
        this.type = UnaryOperatorType.fromString(type);
        this.expression = expression;
    }

    @Override
    public void toCminus(StringBuilder builder, String prefix) {
        builder.append(type);
        expression.toCminus(builder, prefix);
    }

    @Override
    public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable,
            RegisterAllocator regAllocator) {
        MIPSResult exprMips = expression.toMIPS(code, data, symbolTable, regAllocator);
        String exprReg = exprMips.getRegister();
        switch(type) {
            case NOT: {
                code.append(String.format("sub %s $zero %s", exprReg, exprReg));
                return exprMips;
            }
            case NEG: {}
            case DEREF: {}
            case QUESTION: {}
            default:
                return exprMips;
        }
    }

}
