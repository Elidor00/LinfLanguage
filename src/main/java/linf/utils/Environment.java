package linf.utils;

import linf.expression.IDValue;
import linf.type.LinfType;

import java.util.HashMap;
import java.util.LinkedList;

public class Environment {
    public int nestingLevel = -1;
    int offset = 0;
    // List of hash tables
    private LinkedList<HashMap<String, STentry>> symbolsTable = new LinkedList<>();

    /**
     * Adds variable with the given id to existence
     *
     * @param id
     */
    public void addName(String id, STentry val) {
        symbolsTable.peek().put(id, val);
    }

    public HashMap<String, STentry> local() {
        if (nestingLevel <= -1) {
            return null;
        } else {
            return new HashMap<>(symbolsTable.peek());
        }
    }

    public boolean isLocalName(String id) {
        return symbolsTable.peek().containsKey(id);
    }

    public boolean isLocalName(IDValue id) {
        return isLocalName(id.toString());
    }

    /**
     * Inserts a new scope into the environment.
     */
    private void openScope() {
        symbolsTable.push(new HashMap<>());
        nestingLevel++;
    }

    public void openScope(HashMap<String, STentry> scope) {
        if (scope == null) {
            openScope();
        } else {
            symbolsTable.push(scope);
            nestingLevel++;
        }
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
     * Given an id iterates over the scopes list and returns the first scope containing
     * that name.
     *
     * @param id
     */

    private int lookupName(String id, int nestingLevel) {
        for (int i = 0; i <= nestingLevel; i++) {
            HashMap<String, STentry> scope = symbolsTable.get(i);
            if (scope.containsKey(id)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Given an id determines if the variable belongs to the environment
     * this is to check the scopes from inner to outer looking for the variable
     *
     * @param id
     */

    public boolean containsName(String id) {
        return getStEntry(id) != null;
    }

    /**
     * Given an id determines its type inside the environment
     *
     * @param id
     */
    private STentry getStEntry(String id, int nl) {
        int here = lookupName(id, nl);
        if (here > -1) {
            STentry entry = symbolsTable.get(here).get(id);
            if (entry.getType().isDeleted()) {
                return getStEntry(id, here - 1);
            } else {
                return entry;
            }
        } else {
            return null;
        }
    }

    public STentry getStEntry(String id) {
        return getStEntry(id, nestingLevel);
    }

    public STentry getStEntry(IDValue id) {
        return getStEntry(id.toString());
    }

    /**
     * Remove the variable with the given id from the first scope that contains it
     * notice that if the variable exists in an outer scope it will have
     * that value
     *
     * @param id
     */
    public void deleteName(String id) {
        int here = lookupName(id, nestingLevel);
        if (here > -1) {
            LinfType type = symbolsTable.get(here).get(id).getType();
            type.setDeleted(true);
        }
    }
}
