package edu.stanford.bmir.radx.datadictionary.lib;

import java.util.regex.Pattern;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-08-21
 */
public class JavaCcUtil {

    protected static final Pattern PATTERN = Pattern.compile("Encountered: '(\\d+)'");

    public static String getTokenManagerErrorMessage(TokenMgrError e) {
        // Fixes messed up JAVACC Token Manager error message
        var msg = e.getMessage();
        var matcher = PATTERN.matcher(msg);
        if(matcher.find()) {
            var matchedNumber = matcher.group(1);
            var matchAsInt = Integer.parseInt(matchedNumber);
            var groupStart = matcher.start(1);
            var groupEnd = matcher.end(1);
            return msg.substring(0, groupStart) + Character.toString(matchAsInt) + msg.substring(groupEnd);
        }
        else {
            return msg;
        }
    }

    public static String getParseExceptionMessage(ParseException e) {
        return e.getMessage().replaceAll("\\s+", " ")
                .replace("<QUOTED_VALUE>", "enumeration choice value")
                .replace("<QUOTED_LABEL>", "enumeration choice label")
                .replace("<IRI>", "enumeration choice IRI")
                .replace("<DELIMETER>", "enumeration choice separator (|)")
                .replace("<EOF>", "end of enumeration");
    }
}
