package com.wishow;

import java.util.AbstractMap;
import java.util.Map;

public final class Constants {

    /**
     * Private constructor because we don't need to instantiate this class
     */
    private Constants() {
    }

    public static final String CHARACTERS_EXPRESSIONS_ALLOWED_RESTRICTION_RULE =
            "The Roman numerals should contain only these characters :  (I, V, X, L, C, D, M) and (VX, LC, DM) are forbidden combinations";
    public static final String THREE_CONSECUTIVE_REPETITION_RULE =
            "The Roman numerals can not have more than 3 consecutive repetitions of these characters : (I, X, C, M)";
    public static final String NO_REPETITION_RULE =
            "The Roman numerals can not have more than 1 repetition on these characters : (V, L, D)";
    public static final String COMPUTATION_RULE =
            "The Roman numerals can not have these two symbols in this order : ";


    public static final Map<Character, Integer> ROMAN_SYMBOLS_TO_INTEGER_MAP = Map.ofEntries(
            new AbstractMap.SimpleEntry<>('I',1),
            new AbstractMap.SimpleEntry<>('V',5),
            new AbstractMap.SimpleEntry<>('X',10),
            new AbstractMap.SimpleEntry<>('L',50),
            new AbstractMap.SimpleEntry<>('C',100),
            new AbstractMap.SimpleEntry<>('D',500),
            new AbstractMap.SimpleEntry<>('M',1000)
    );

}
