package utils;

import java.util.HashMap;
import java.util.LinkedList;

public class Environment {
    // List of hash tables
    private LinkedList<HashMap<String, STentry>> symbolsTable = new LinkedList<HashMap<String, STentry>>();

    public int nestingLevel = -1;

    /**
     * Adds variable with the given id to existence
     *
     * @param id
     */
    public void addName(String id, STentry val) {
        symbolsTable.peek().put(id, val);
    }

    /**
     * Inserts a new scope into the environment.
     * When a scope is inserted old scope is clone so previous defined
     * variables still exist
     */
    public void openScope() {
        symbolsTable.push(new HashMap<>());
        nestingLevel++;
    }

    /**
     * Drops the current scope and returns to the outer scope
     * removing all changes and additions done within this scope
     */
    public void closeScope() {
        symbolsTable.pop();
        nestingLevel--;
    }

    /**
     * Given an id determines if the variable belongs to the environment
     * this is to check the scopes from inner to outer looking for the variable
     *
     * @param id
     */
    public boolean containsName(String id) {
        for (int i = nestingLevel; i > -1; i--) {
            if (symbolsTable.get(i).containsKey(id)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Given an id determines its type inside the environment
     *
     * @param id
     */
    public STentry getStEntry(String id) {
        for (int i = nestingLevel; i > -1; i--) {
            HashMap<String, STentry> hm = symbolsTable.get(i);
            if (hm.containsKey(id)) {
                return hm.get(id);
            }
        }
        return null;
    }

    /**
     * Remove the variable with the given id from the first scope that contains it
     * notice that if the variable exists in an outer scope it will have
     * that value
     *
     * @param id
     */
    public void deleteName(String id) {
        for (int i = nestingLevel; i > -1; i--) {
            HashMap<String, STentry> scope = symbolsTable.get(i);
            if (scope.containsKey(id)) {
                scope.remove(id);
                return;
            }
        }
    }


}
