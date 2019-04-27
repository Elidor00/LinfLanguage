package ast;

class ComplexExtdIntValue extends ComplexExtdValue {
    ComplexExtdIntValue(String value) {
        setValue(value);
        setType(new ComplexExtdIntType());
    }
}
