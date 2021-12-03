package com.ittext;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;

public class Launcher {
    public static void main(String[] args) throws IOException, ParseException {
        if(args.length == 0){
            throw new RuntimeException("You should provide a path to the file as a command line argument");
        }else{
            String pathToFile = args[0];
            EfficiencyStrategy efficiencyStrategy = new EfficiencyStrategyImpl();
            SyntaxChecker syntaxChecker = new SyntaxCheckerImpl();

            TimeTable timeTable = new TimeTable(efficiencyStrategy, syntaxChecker);
            Path timeTableFile = timeTable.createTimeTableFile(pathToFile);
            System.out.println("the file is in the location "+timeTableFile);
        }
    }
}
