package linf.utils;

import linf.expression.IDValue;
import linf.type.FunType;
import linf.type.LinfType;

import java.util.*;

public class Environment {
    // List of hash tables
    private final LinkedList<HashMap<String, STentry>> symbolsTable = new LinkedList<>();
    private final LinkedList<HashSet<STentry>> deletedIDs = new LinkedList<>();
    private int nestingLevel = -1;
    private int offset = 0;

    private void resetOffset() {
        offset = 0;
    }

    private void restoreOffset() {
        if (symbolsTable.size() > 0) {
            Set<Map.Entry<String, STentry>> entrySet = symbolsTable.peek()
                    .entrySet();
            if (entrySet.size() > 0) {
                offset = entrySet.stream()
                        .map(Map.Entry::getValue)
                        .map(STentry::getOffset)
                        .min(Integer::compare)
                        .get();
            } else {
                resetOffset();
            }
        } else {
            resetOffset();
        }
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
     * Given an id check if is bound in the current scope,
     * otherwise search the father.
     */
    private STentry getStEntry(String id, int nl) {
        int here = lookupName(id, nl);
        if (here > -1) {
            STentry entry = symbolsTable.get(here).get(id);
            if (isDeleted(entry, here)) {
                return getStEntry(id, here - 1);
            } else {
                return entry;
            }
        } else {
            return null;
        }
    }

    public int getNestingLevel() {
        return nestingLevel;
    }

    /**
     * Adds variable with the given id to existence
     *
     * @param id
     */
    public void addName(String id, LinfType type) {
        assert type != null;
        assert id != null;
        STentry entry = new STentry(nestingLevel, offset, type, id);
        symbolsTable.peek().put(id, entry);
        if (!(type instanceof FunType)) {
            offset--;
        }
    }

    public void setReference(STentry par, STentry referred) {
        assert referred != null;
        assert par != null;
        LinfType parType = par.getType();
        LinfType refType = referred.getType();
        if (refType.isReference() &&
                refType.getRefTo() != null &&
                referred.getNestinglevel() != par.getNestinglevel()) {
            setReference(par, referred.getType().getRefTo());
        } else {
            parType.setRefTo(referred);
        }
    }

    public boolean isLocalName(String id) {
        if (symbolsTable.peek().containsKey(id)) {
            STentry entry = symbolsTable.peek().get(id);
            boolean isPrototype = false;
            if (entry.getType() instanceof FunType) {
                isPrototype = ((FunType) entry.getType()).isPrototype();
            }
            return (entry.getNestinglevel() == nestingLevel &&
                    !entry.getType().isDeleted() &&
                    !isPrototype);
        } else {
            return false;
        }
    }

    /**
     * Inserts a new scope into the environment.
     */
    public void openScope(HashMap<String, STentry> scope) {
        symbolsTable.push(Objects.requireNonNullElseGet(scope, HashMap::new));
        deletedIDs.push(new HashSet<>());
        nestingLevel++;
        resetOffset();
    }

    /**
     * Drops the current scope and returns to the outer scope
     * removing all changes and additions done within this scope
     */
    public void closeScope() {
        symbolsTable.pop();
        deletedIDs.pop();
        nestingLevel--;
        restoreOffset();
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

    public STentry getStEntry(String id) {
        return getStEntry(id, nestingLevel);
    }

    public STentry getStEntry(IDValue id) {
        return getStEntry(id.toString());
    }

    /**
     * Remove the variable with the given id from the first scope that contains it.
     */
    public void deleteName(String id) {
        int here = lookupName(id, nestingLevel);
        if (here > -1) {
            STentry entry = symbolsTable.get(here).get(id);
            LinfType type = entry.getType();
            deletedIDs.peek().add(entry);
            type.setDeleted(true);
        }
    }

    public boolean isDeleted(STentry entry) {
        return isDeleted(entry, nestingLevel);
    }

    private boolean isDeleted(STentry entry, int nestingLevel) {
        if (deletedIDs.get(nestingLevel).contains(entry)) {
            return true;
        } else if (nestingLevel > 0) {
            return isDeleted(entry, nestingLevel - 1);
        } else {
            return false;
        }
    }
}
