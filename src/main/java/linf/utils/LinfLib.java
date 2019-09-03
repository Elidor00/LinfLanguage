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

    private static String followChain(int distance, int linkOffset) {
        if (distance > 1) {
            return "lw $al " + linkOffset + "($fp)\n" +
                    "lw $al 2($al)\n".repeat(distance - 1);
        } else {
            return "lw $al 2($fp)\n";
        }
    }

    public static String followAl(int distance) {
        return followChain(distance, 2);
    }

    public static String followCl(int distance) {
        return followChain(distance, 3);
    }

    public static String pushAl(int distance) {
        if (distance > 0) {
            return "lw $al 2($fp)\n" +
                    "lw $al 2($al)\n".repeat(distance - 1) +
                    "push $al\n";
        } else {
            return "push $fp\n";
        }
    }
}
