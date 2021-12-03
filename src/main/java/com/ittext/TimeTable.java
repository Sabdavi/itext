package com.ittext;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TimeTable {

    SimpleDateFormat format = new SimpleDateFormat("HH:mm");

    EfficiencyStrategy efficiencyStrategy;
    SyntaxChecker syntaxChecker;

    public TimeTable(EfficiencyStrategy efficiencyStrategy, SyntaxChecker syntaxChecker) {
        this.efficiencyStrategy = efficiencyStrategy;
        this.syntaxChecker = syntaxChecker;
    }

    public Path createTimeTableFile(String fileName) throws IOException, ParseException {
        List<String> stringList = FileUtils.readFile(fileName);
        List<Service> services =  convertStringToService(stringList,syntaxChecker);
        Map<String,List<Service>> timeTableObjects = createTimeTableObjects(services);
        return FileUtils.writeFile(timeTableObjects, "/home/saeid/Downloads/itext/resultTimeTable.txt");

    }
    public List<Service> convertStringToService(List<String> stringServices, SyntaxChecker syntaxChecker) throws ParseException {
        List<Service> services = new ArrayList<>();
        long lineNumber = 1;
        for(String srv : stringServices){
            validateSyntax(srv, lineNumber, syntaxChecker);
            Map<String, Integer> mapping = syntaxChecker.getMapping();
            String[] serviceElements = srv.split(syntaxChecker.getSeparator());
            Service service = new Service(Company.valueOf(serviceElements[mapping.get("company")]),
                    format.parse(serviceElements[mapping.get("departureTime")]),format.parse(serviceElements[mapping.get("arrivalTime")]));
            services.add(service);
            lineNumber++;
        }
        return services;
    }

    private void validateSyntax(String srv, long lineNumber, SyntaxChecker syntaxChecker) {
        if(!syntaxChecker.getPattern().matcher(srv).find()){
            throw new InvalidSyntaxException(String.format("invalid formant at line %d",lineNumber));
        }
    }

    public Map<String,List<Service>> createTimeTableObjects(List<Service> serviceList){
        Map<String,List<Service>> serviceMap = new HashMap<>();
        List<Service> timeTableForPosh = new ArrayList<>();
        List<Service> timeTableForGrotty = new ArrayList<>();
        for(Service service : serviceList){
            List<Service> moreEfficientServicesThanThisService = getMoreEfficientServicesThanThisService(service, serviceList);
            if(moreEfficientServicesThanThisService.isEmpty()){
                if(service.company.equals(Company.Posh)){
                    timeTableForPosh.add(service);
                }else{
                    timeTableForGrotty.add(service);
                }
            }
        }
        Collections.sort(timeTableForPosh);
        Collections.sort(timeTableForGrotty);
        serviceMap.put(Company.Posh.toString(),timeTableForPosh);
        serviceMap.put(Company.Grotty.toString(),timeTableForGrotty);
        return serviceMap;
    }

    public List<Service> getMoreEfficientServicesThanThisService(Service service ,List<Service> services){
        List<Service> moreEfficientServices = new ArrayList<>();
        for(Service srv : services){
            if(efficiencyStrategy.isMoreEfficient(srv,service)){
                moreEfficientServices.add(srv);
            }
        }
        return moreEfficientServices;
    }
}
