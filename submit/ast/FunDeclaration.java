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
public class FunDeclaration implements Declaration, AbstractNode {

    private final VarType returnType;
    private final String id;
    private final ArrayList<Param> params;
    private final Statement statement;

    public FunDeclaration(VarType returnType, String id, List<Param> params,
            Statement statement) {
        this.returnType = returnType;
        this.id = id;
        this.params = new ArrayList<>(params);
        this.statement = statement;
    }

    public void toCminus(StringBuilder builder, final String prefix) {
        String rt = (returnType != null) ? returnType.toString() : "void";
        builder.append("\n").append(rt).append(" ");
        builder.append(id);
        builder.append("(");

        for (Param param : params) {
            param.toCminus(builder, prefix);
            builder.append(", ");
        }
        if (!params.isEmpty()) {
            builder.delete(builder.length() - 2, builder.length());
        }
        builder.append(")\n");
        statement.toCminus(builder, prefix);
    }

    @Override
    public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable,
            RegisterAllocator regAllocator) {
                
        // String midComment = String.format("# Entering a new scope.\n%s# Update the stack pointer.\n", symbolTable.toString());
        
        // String outroComment = "# Exiting scope.\n";
        
        code.append(String.format("\n# code for %s\n%s:\n", id, id));
        code.append("addi $sp $sp -0\n");
        // Function label
        // mipsCode.append(id).append(":\n");

        // Prologue: Save return address and frame pointer
        // mipsCode.append(" sw $ra, 0($sp)\n");
        // mipsCode.append(" sw $fp, -4($sp)\n");
        // mipsCode.append(" addiu $sp, $sp, -8\n");
        // mipsCode.append(" move $fp, $sp\n");

        AbstractNode m = (AbstractNode) statement;
        m.toMIPS(code, data, symbolTable, regAllocator);
  
        // Epilogue: Restore frame pointer and return address
        // mipsCode.append(" move $sp, $fp\n");
        // mipsCode.append(" lw $fp, -4($sp)\n");
        // mipsCode.append(" lw $ra, 0($sp)\n");
        // mipsCode.append(" addiu $sp, $sp, 8\n");
        // mipsCode.append(" jr $ra\n");

        return MIPSResult.createVoidResult();
    }

}
