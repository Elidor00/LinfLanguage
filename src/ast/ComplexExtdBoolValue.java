package ast;

public class ComplexExtdBoolValue extends ComplexExtdValue {
    ComplexExtdBoolValue(String value) {
        setValue(value);
        setType(new ComplexExtdBoolType());
    }
}
