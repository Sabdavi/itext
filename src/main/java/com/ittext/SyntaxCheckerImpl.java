package com.ittext;

import java.util.regex.Pattern;

public class SyntaxCheckerImpl implements SyntaxChecker{
    @Override
    public Pattern getPattern() {
        return Pattern.compile("^(POSH|GRUTTY) (\\d\\d):(\\d\\d) (\\d\\d):(\\d\\d)");
    }
}
