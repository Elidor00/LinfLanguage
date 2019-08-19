package lvm.utils;

import lvm.LVM;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import static lvm.utils.Strings.*;

public class Registers {

    public static final Map<String, Function<LVM, Integer>> GET_REGISTER_VALUE = new HashMap<>();
    public static final Map<String, BiFunction<LVM, Integer, Integer>> SET_REGISTER_VALUE = new HashMap<>();
    public static final HashMap<Integer, String> INT_TO_STRING_REGISTER = new HashMap<>();
    public static final HashMap<String, Integer> REGISTER_TO_INT = new HashMap<>();
    private static final Function<LVM, Integer> ACC_REGISTER_VALUE = LVM::getA0;
    private static final Function<LVM, Integer> TMP_REGISTER_VALUE = LVM::getT1;
    private static final Function<LVM, Integer> SP_REGISTER_VALUE = LVM::getSp;
    private static final Function<LVM, Integer> FP_REGISTER_VALUE = LVM::getFp;
    private static final Function<LVM, Integer> AL_REGISTER_VALUE = LVM::getAl;
    private static final Function<LVM, Integer> RA_REGISTER_VALUE = LVM::getRa;
    private static final Function<LVM, Integer> IP_REGISTER_VALUE = LVM::getIp;
    private static final BiFunction<LVM, Integer, Integer> ACC_REGISTER_VALUE_SET = LVM::setA0;
    private static final BiFunction<LVM, Integer, Integer> TMP_REGISTER_VALUE_SET = LVM::setT1;
    private static final BiFunction<LVM, Integer, Integer> SP_REGISTER_VALUE_SET = LVM::setSp;
    private static final BiFunction<LVM, Integer, Integer> FP_REGISTER_VALUE_SET = LVM::setFp;
    private static final BiFunction<LVM, Integer, Integer> AL_REGISTER_VALUE_SET = LVM::setAl;
    private static final BiFunction<LVM, Integer, Integer> RA_REGISTER_VALUE_SET = LVM::setRa;
    private static final BiFunction<LVM, Integer, Integer> IP_REGISTER_VALUE_SET = LVM::setIp;

    static {

        GET_REGISTER_VALUE.put(ACC, ACC_REGISTER_VALUE);
        GET_REGISTER_VALUE.put(TMP, TMP_REGISTER_VALUE);
        GET_REGISTER_VALUE.put(SP, SP_REGISTER_VALUE);
        GET_REGISTER_VALUE.put(FP, FP_REGISTER_VALUE);
        GET_REGISTER_VALUE.put(AL, AL_REGISTER_VALUE);
        GET_REGISTER_VALUE.put(RA, RA_REGISTER_VALUE);
        GET_REGISTER_VALUE.put(IP, IP_REGISTER_VALUE);

        SET_REGISTER_VALUE.put(ACC, ACC_REGISTER_VALUE_SET);
        SET_REGISTER_VALUE.put(TMP, TMP_REGISTER_VALUE_SET);
        SET_REGISTER_VALUE.put(SP, SP_REGISTER_VALUE_SET);
        SET_REGISTER_VALUE.put(FP, FP_REGISTER_VALUE_SET);
        SET_REGISTER_VALUE.put(AL, AL_REGISTER_VALUE_SET);
        SET_REGISTER_VALUE.put(RA, RA_REGISTER_VALUE_SET);
        SET_REGISTER_VALUE.put(IP, IP_REGISTER_VALUE_SET);

        INT_TO_STRING_REGISTER.put(0, ACC);
        INT_TO_STRING_REGISTER.put(1, TMP);
        INT_TO_STRING_REGISTER.put(2, SP);
        INT_TO_STRING_REGISTER.put(3, FP);
        INT_TO_STRING_REGISTER.put(4, AL);
        INT_TO_STRING_REGISTER.put(5, RA);
        INT_TO_STRING_REGISTER.put(6, IP);

        REGISTER_TO_INT.put(ACC, 0);
        REGISTER_TO_INT.put(TMP, 1);
        REGISTER_TO_INT.put(SP, 2);
        REGISTER_TO_INT.put(FP, 3);
        REGISTER_TO_INT.put(AL, 4);
        REGISTER_TO_INT.put(RA, 5);
        REGISTER_TO_INT.put(IP, 6);

    }
}
