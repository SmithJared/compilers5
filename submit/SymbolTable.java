package submit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * Code formatter project
 * CS 4481
 */
/**
 *
 */
public class SymbolTable {

    private final HashMap<String, SymbolInfo> table;
    private SymbolTable parent;
    private final List<SymbolTable> children;
    private int labelCounter = 0;

    private int sp;
    private int size;
    private HashMap<String, Integer> offsets;
    private boolean valFlag;

    public SymbolTable() {
        table = new HashMap<>();
        parent = null;
        children = new ArrayList<>();
        sp = 0;
        size = 0;
        offsets = new HashMap<>();

        table.put("println", new SymbolInfo("println", null, true)); // Add the println function to the global
        table.put("return", new SymbolInfo("return", null, false)); // Add the println function to the global
    }

    public void addSymbol(String id, SymbolInfo symbol) {
        table.put(id, symbol);
        this.size += 4;
        int offset = -this.size;
        offsets.put(id, offset);
    }

    public void wantsAddr(){
        valFlag = true;
    }

    public boolean doesReturnAddr(){
        boolean ret = valFlag;
        valFlag = false;
        return ret;
    }

    public int size() {
        return this.size;
    }

    public void setStackPointer(int sp) {
        this.sp = sp;
    }

    // If has has been sized
    public int getOffset(String id) {
        if (offsets.containsKey(id)) {
            return offsets.get(id);
        }
        return -1;
    }

    /**
     * Returns null if no symbol with that id is in this symbol table or an
     * ancestor table.
     *
     * @param id
     * @return
     */
    public SymbolInfo find(String id) {
        if (table.containsKey(id)) {
            return table.get(id);
        }
        if (parent != null) {
            return parent.find(id);
        }
        return null;
    }

    /**
     * Returns a unique label for the symbol table.
     * 
     * @return string - the unique label for the symbol table.
     */
    public String getUniqueLabel() {
        String uniqueLabel = String.format("datalabel%d", labelCounter);
        labelCounter++; // Increment the counter for the next unique label
        return uniqueLabel; // Return the current unique label
    }

    /**
     * Returns the new child.
     *
     * @return
     */
    public SymbolTable createChild() {
        SymbolTable child = new SymbolTable();
        children.add(child);
        child.parent = this;
        return child;
    }

    public SymbolTable getParent() {
        return parent;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("# Symbols in SymbolTable: \n");
        for (String key : table.keySet()) {
            builder.append("#  ").append(key).append(": ").append(table.get(key)).append("\n");
        }
        if (parent != null) {
            builder.append("Parent: ").append(parent.toString());
        }
        return builder.toString();
    }

    public String currentTableToString() {
        StringBuilder builder = new StringBuilder();
        builder.append("# Symbols in SymbolTable: \n");
        for (String key : table.keySet()) {
            builder.append("#  ").append(key).append(": ").append(table.get(key)).append("\n");
        }
        return builder.toString();
    }

}
