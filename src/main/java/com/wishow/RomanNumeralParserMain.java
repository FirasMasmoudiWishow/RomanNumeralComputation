package com.wishow;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import static com.wishow.Constants.*;
import static com.wishow.Utils.evaluateRegExp;

@Slf4j
public class RomanNumeralParserMain {
    public static void main(String[] args) {
        log.info("Main method started");
        RomanParsingResult romanParsingResult = parseRomanNumeral("XXIX", "CXVII");
        log.info(romanParsingResult.toString());
    }

    /**
     * parse a Roman Numeral in order to validate it and in case it is a valid calculate his Integer value taking into
     * consideration the vinculum possibility (the part of the Roman Numeral that is under a horizontal line)
     * @param romanNumeral Roman numeral without vinculum
     * @param vinculumRomanNumeral Roman numeral with vinculum
     * @return RomanParsingResult (can not be null)
     */
    public static RomanParsingResult parseRomanNumeral(@NonNull String romanNumeral, String vinculumRomanNumeral) {
        log.info("Parsing : " + romanNumeral);
        final RomanParsingResult romanNumeralValidationResult = validateRomanNumeral(romanNumeral);
        if (romanNumeralValidationResult != null) {
            return romanNumeralValidationResult;
        }
        final RomanParsingResult vinculumRomanNumeralValidationResult = vinculumRomanNumeral != null ?
                validateRomanNumeral(vinculumRomanNumeral) : null;
        if (vinculumRomanNumeralValidationResult != null) {
            return vinculumRomanNumeralValidationResult;
        }
        return computeRomanNumeral(romanNumeral, vinculumRomanNumeral);
    }

    /**
     * compute Roman Numeral (takes into consideration the vinculum : horizontal Line above some Roman Symbols)
     * @param romanNumeral Roman numeral without vinculum
     * @param vinculumRomanNumeral Roman numeral with vinculum
     * @return RomanParsingResult (can not be null)
     */
    private static RomanParsingResult computeRomanNumeral(String romanNumeral, String vinculumRomanNumeral) {
        RomanParsingResult simpleRomanNumeralResult = computeRomanNumeralWithoutVinculum(romanNumeral);
        RomanParsingResult vinculumRomanNumeralResult;
        if (vinculumRomanNumeral == null) {
            return simpleRomanNumeralResult;
        }
        vinculumRomanNumeralResult = computeRomanNumeralWithoutVinculum(vinculumRomanNumeral);
        boolean finalResultValidity = simpleRomanNumeralResult.getIsValid() && vinculumRomanNumeralResult.getIsValid();

        return RomanParsingResult.builder()
                .result(finalResultValidity ? simpleRomanNumeralResult.getResult() + vinculumRomanNumeralResult.getResult() * 1000 : null)
                .isValid(finalResultValidity)
                .build();
    }

    /**
     * Compute Roman Numeral Without taking into consideration the vinculum (horizontal Line above some Roman Symbols)
     * @param romanNumeral Roman Numeral
     * @return RomanParsingResult (can not be null)
     */
    public static RomanParsingResult computeRomanNumeralWithoutVinculum(String romanNumeral) {
        int result = 0;
        for (int i = 0; i < romanNumeral.length(); i++) {
            Integer currentSymbolValue = ROMAN_SYMBOLS_TO_INTEGER_MAP.get(romanNumeral.charAt(i));
            Integer previousSymbolValue = i > 0 ? ROMAN_SYMBOLS_TO_INTEGER_MAP.get(romanNumeral.charAt(i - 1)) : null;
            // Validation while processing in order to optimize execution time
            if (previousSymbolValue != null && currentSymbolValue > 10 * previousSymbolValue) {
                return RomanParsingResult.builder()
                        .isValid(false)
                        .nonValidityReason(COMPUTATION_RULE + romanNumeral.charAt(i - 1) + romanNumeral.charAt(i))
                        .build();
            }
            if (i > 0 && currentSymbolValue > previousSymbolValue) {
                result += currentSymbolValue - 2 * previousSymbolValue;
            } else {
                result += currentSymbolValue;
            }
        }
        return RomanParsingResult.builder()
                .result(result)
                .isValid(true)
                .build();
    }

    /**
     * Execute a list of form validation on the roman numeral in order to detect if there is a rule
     * violation from the list below :
     * * Contains only a combination of these characters (I, V, X, L, C, D, M)
     * * VX, LC and DM are forbidden combinations
     * * (I, X, C, M) can not be repeated more than 3 times
     * * (V, L, D) can not be repeated more than once
     * @param romanNumeral the Roman Numeral to validate
     * @return null if there is no violations otherwise return a RomanParsingResult nonValid and containing
     * the reason of the non validation
     */
    public static RomanParsingResult validateRomanNumeral(String romanNumeral) {
        log.info(romanNumeral + " form validation");
        String validationMessage = null;
        if (!evaluateRegExp(romanNumeral, "(?!(VX|LC|DM))([IVXLCDM]*)")) {
            validationMessage = CHARACTERS_EXPRESSIONS_ALLOWED_RESTRICTION_RULE;
        }
        if (evaluateRegExp(romanNumeral, "^([I]{4,})$")
                || evaluateRegExp(romanNumeral, "^([X]{4,})$")
                || evaluateRegExp(romanNumeral, "^([C]{4,})$")
                || evaluateRegExp(romanNumeral, "^([M]{4,})$")) {
            validationMessage = validationMessage == null ? THREE_CONSECUTIVE_REPETITION_RULE :
                validationMessage + ", " + THREE_CONSECUTIVE_REPETITION_RULE;
        }
        if (evaluateRegExp(romanNumeral, "^([IXCM]*)([VLD]{2,})([IXCM]*)$")) {
            validationMessage = validationMessage == null ? NO_REPETITION_RULE :
                    validationMessage + ", " + NO_REPETITION_RULE;
        }

        if (validationMessage != null) {
            return RomanParsingResult.builder()
                    .isValid(false)
                    .nonValidityReason(validationMessage)
                    .build();
        }
        return null;
    }
}
