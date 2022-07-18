package com.wishow;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class RomanParsingResult {
    private int result;
    private Boolean isValid;
    private String nonValidityReason;
}
