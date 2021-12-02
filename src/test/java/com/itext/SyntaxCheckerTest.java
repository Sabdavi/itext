package com.itext;

import com.ittext.SyntaxChecker;
import com.ittext.SyntaxCheckerImpl;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SyntaxCheckerTest {

    @Test
    public void testSyntaxChecker(){
        String s1 = "GRUTTY 10:10 12:20";
        String s2 = "POSH 10:10 12:20";
        String s3 = "GRUTTY a0:10 1220";
        String s4 = "BMW 10:10 12:20";
        String s5 = "GRUTTY sasas 12:20";
        String s6 = "GRUTTY sasas fffdfd";
        String s7 = "GRUTTY sasas 3333ewe";

        SyntaxChecker syntaxChecker = new SyntaxCheckerImpl();
        Pattern pattern = syntaxChecker.getPattern();
        Matcher matcher = pattern.matcher(s1);
        Matcher matcher1 = pattern.matcher(s2);
        Matcher matcher2 = pattern.matcher(s3);
        Matcher matcher3 = pattern.matcher(s4);
        Matcher matcher4 = pattern.matcher(s5);
        Matcher matcher5 = pattern.matcher(s6);
        Matcher matcher6 = pattern.matcher(s7);

        assertTrue(matcher.find());
        assertTrue(matcher1.find());
        assertFalse(matcher2.find());
        assertFalse(matcher3.find());
        assertFalse(matcher4.find());
        assertFalse(matcher5.find());
        assertFalse(matcher6.find());



    }

}
