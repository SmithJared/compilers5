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
public class While implements Statement {

  private final Expression expression;
  private final Statement statement;

  public While(Expression expression, Statement statement) {
    this.expression = expression;
    this.statement = statement;
  }

  @Override
  public void toCminus(StringBuilder builder, String prefix) {
    builder.append(prefix).append("while (");
    expression.toCminus(builder, prefix);
    builder.append(")\n");
    if (statement instanceof CompoundStatement) {
      statement.toCminus(builder, prefix);
    } else {
      statement.toCminus(builder, prefix + " ");
    }

  }

  @Override
  public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable,
        RegisterAllocator regAllocator) {
    // TODO Auto-generated method stub
    return null;
  }
}
