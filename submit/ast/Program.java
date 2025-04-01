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
public class Program implements AbstractNode {

    private ArrayList<Declaration> declarations;

    public Program(List<Declaration> declarations) {
        this.declarations = new ArrayList<>(declarations);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        toCminus(builder, "");
        return builder.toString();
    }

    @Override
    public void toCminus(StringBuilder builder, String prefix) {
        for (Declaration declaration : declarations) {
            declaration.toCminus(builder, "");
        }
    }

    @Override
    public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable,
            RegisterAllocator regAllocator) { 
        data.append(String.format("newline:\t.asciiz \"\\n\"\n"));
 
        for(Declaration d : declarations){
            AbstractNode e = (AbstractNode)d;
            e.toMIPS(code, data, symbolTable, regAllocator);
        }
        
        code.append("addi $sp $sp 0\n").append("li $v0 10\n").append("syscall\n");
        return MIPSResult.createVoidResult();
    }

}
