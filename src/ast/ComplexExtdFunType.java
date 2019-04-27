package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;
import java.util.List;

public class ComplexExtdFunType extends ComplexExtdType {
    private List<ComplexExtdType> parTypes = new ArrayList<>();

    public List<ComplexExtdType> getParTypes() {
        return parTypes;
    }

    public void addParType(ComplexExtdType type){
        this.parTypes.add(type);
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public ComplexExtdType checkType(Environment env) {
        return null;
    }
}
