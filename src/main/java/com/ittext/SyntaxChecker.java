package com.ittext;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public interface SyntaxChecker {
    default Pattern getPattern(){
        return Pattern.compile("^(Posh|Grotty) (\\d\\d):(\\d\\d) (\\d\\d):(\\d\\d)");
    }

    default String getSeparator(){
        return " ";
    }

    default Map<String,Integer> getMapping(){
        Map<String,Integer> mapping = new HashMap<>();
        mapping.put("company",0);
        mapping.put("departureTime",1);
        mapping.put("arrivalTime",2);

        return mapping;
    }
}
