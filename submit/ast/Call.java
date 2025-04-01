/*
 * Code formatter project
 * CS 4481
 */
package submit.ast;

import java.util.ArrayList;
import java.util.List;

import submit.MIPSResult;
import submit.RegisterAllocator;
import submit.SymbolTable;

/**
 *
 * @author edwajohn
 */
public class Call implements Expression, AbstractNode {

    private final String id;
    private final List<Expression> args;
    private final String SYSCALL = "syscall\n";
    private final String NEWLINE = "la $a0 newline\nli $v0 4\n";

    public Call(String id, List<Expression> args) {
        this.id = id;
        this.args = new ArrayList<>(args);
    }

    @Override
    public void toCminus(StringBuilder builder, String prefix) {
        builder.append(id).append("(");
        for (Expression arg : args) {
            arg.toCminus(builder, prefix);
            builder.append(", ");
        }
        if (!args.isEmpty()) {
            builder.setLength(builder.length() - 2);
        }
        builder.append(")");
    }

    @Override
    public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable,
            RegisterAllocator regAllocator) {
        code.append(String.format("# %s\n", id));
        if (this.id.equals("println")) {
            return print(code, data, symbolTable, regAllocator);
        }

        // MIPSResult result = MIPSResult.createRegisterResult();

        // return result;
        return null;
    }

    private MIPSResult print(StringBuilder code, StringBuilder data, SymbolTable symbolTable,
            RegisterAllocator regAllocator) {
        Expression expr = args.remove(0);
        MIPSResult mips = expr.toMIPS(code, data, symbolTable, regAllocator);
        String reg = mips.getRegister();
        VarType type = mips.getType();

        switch (type) {
            case INT:
                code.append(String.format("move $a0 %s\n", reg));
                code.append(String.format("li $v0 1\n"));
                break;
            case CHAR:
                code.append(String.format("move $a0 %s\n", reg));
                code.append(String.format("li $v0 11\n"));
                break;
            case BOOL:
                code.append(String.format("move $a0 %s\n", reg));
                code.append(String.format("li $v0 1\n"));
                break;
            case STRING:
                code.append(String.format("la $a0 %s\n", mips.getAddress()));
                code.append(String.format("li $v0 4\n"));
                break;
            default:
                break;
        }

        code.append(SYSCALL);
        code.append(NEWLINE);
        code.append(SYSCALL);
        regAllocator.clearAll();
        return MIPSResult.createVoidResult();
    }

}
