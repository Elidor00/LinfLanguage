package lvm.utils;

public class Strings {

    public static final String ACC = "$a0";
    public static final String TMP = "$t1";
    public static final String SP = "$sp";
    public static final String FP = "$fp";
    public static final String AL = "$al";
    public static final String RA = "$ra";
    public static final String IP = "$ip";
    
    private static int labelCount = 0;
    private static int funLabelCount = 0;

    public static void reset(){
        labelCount = 0;
        funLabelCount = 0;
    }

    public static String freshLabel() {
        return "label" + (labelCount++) + ":\n";
    }

    public static String freshFunLabel() {
        return "fLabel" + (funLabelCount++) + ":\n";
    }

}
