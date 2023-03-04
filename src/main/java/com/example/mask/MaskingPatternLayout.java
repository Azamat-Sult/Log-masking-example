package com.example.mask;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaskingPatternLayout extends PatternLayout {

    private Pattern multilinePattern;
    private final List<String> maskPatterns = new ArrayList<>();
    private static final String MASKED_FIELD = "maskedField";
    private static final String[] masks = {
            "\\\"" + MASKED_FIELD + "\\\":\\\"([^\\\"]*)"
    };
    private static final String fixMask = "***";

    public void addMaskedFields(String maskedFields) {

        if (maskedFields != null) {
            String[] fields = maskedFields.split(",");
            Arrays.stream(fields).map(String::trim).toArray(field -> fields);
            for (String field : fields) {
                for (String mask : masks) {
                    maskPatterns.add(mask.replace(MASKED_FIELD, field));
                }
            }
            multilinePattern = Pattern.compile(String.join("|", maskPatterns), Pattern.MULTILINE);
            System.out.println(multilinePattern);
        }

    }

    @Override
    public String doLayout(ILoggingEvent event) {
        return maskMessage(super.doLayout(event));
    }

    private String maskMessage(String message) {
        if (multilinePattern == null) {
            return message;
        }
        StringBuilder sb = new StringBuilder(message);
        Matcher matcher = multilinePattern.matcher(sb);
        return matcher.replaceAll(mr -> mr.group().replace(mr.group().split(":\"")[1], fixMask));
    }

}