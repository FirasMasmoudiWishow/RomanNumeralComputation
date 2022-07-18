package com.wishow;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private Utils() {
        throw new UnsupportedOperationException("Utility Class can not be instantiated.");
    }

    /**
     * Evaluate a regular expression
     * @param stringToEvaluate String to evaluate
     * @param regExp The regular expression to apply
     * @return true if the regular expression matches false otherwise
     */
    public static boolean evaluateRegExp(String stringToEvaluate, String regExp) {
        Pattern p;
        Matcher m;
        p = Pattern.compile(regExp);
        m =  p.matcher(stringToEvaluate);
        return m.matches();
    }
}
