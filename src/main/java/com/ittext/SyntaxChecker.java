package com.ittext;

import java.util.regex.Pattern;

public interface SyntaxChecker {
    default Pattern getPattern(){
        return Pattern.compile("^(POSH|GRUTTY) (\\d\\d):(\\d\\d) (\\d\\d):(\\d\\d)");
    }
}
