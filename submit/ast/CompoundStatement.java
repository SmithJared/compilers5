/*
 * Code formatter project
 * CS 4481
 */
package submit.ast;

import java.util.List;

import submit.MIPSResult;
import submit.RegisterAllocator;
import submit.SymbolTable;

/**
 *
 * @author edwajohn
 */
public class CompoundStatement implements Statement, AbstractNode {

    private final List<Statement> statements;
    private final SymbolTable symTable;

    public CompoundStatement(List<Statement> statements, SymbolTable symbolTable) {
        this.statements = statements;
        this.symTable = symbolTable;
    }

    @Override
    public void toCminus(StringBuilder builder, String prefix) {
        builder.append(prefix).append("{\n");
        for (Statement s : statements) {
            s.toCminus(builder, prefix + "  ");
        }
        builder.append(prefix).append("}\n");
    }

    @Override
    public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable, RegisterAllocator regAllocator) {
        this.symTable.setStackPointer(-symbolTable.size());
        
        for(Statement s : statements){
            s.toMIPS(code, data, this.symTable, regAllocator);
        }


        return MIPSResult.createVoidResult();
    }
}
