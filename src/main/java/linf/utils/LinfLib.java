package linf.utils;

public abstract class LinfLib {
    private static int labelCount = 0;
    private static int funLabelCount = 0;

    public static void reset() {
        labelCount = 0;
        funLabelCount = 0;
    }

    public static String freshLabel() {
        return "label" + (labelCount++) + ":\n";
    }

    public static String freshFunLabel() {
        return "fLabel" + (funLabelCount++) + ":\n";
    }

    public static String followChain(int distance) {
        if (distance > 1) {
            return "lw $al 2($fp)\n" +
                    "lw $al 2($al)\n".repeat(distance - 1);
        } else {
            return "lw $al 2($fp)\n";
        }

    }
}
