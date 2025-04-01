/*
 * Code formatter project
 * CS 4481
 */
package submit.ast;

import java.util.ArrayList;

import submit.SymbolTable;

/**
 *
 * @author edwajohn
 */
public interface Statement extends Node {
    public static CompoundStatement empty() { return new CompoundStatement(new ArrayList<>(), new SymbolTable()); }

}
