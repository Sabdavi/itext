package com.ittext;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.*;

import static com.ittext.TimeUtils.convertStringToDate;

public class TimeTable {


    private EfficiencyStrategy efficiencyStrategy;
    private SyntaxChecker syntaxChecker;

    public TimeTable(EfficiencyStrategy efficiencyStrategy, SyntaxChecker syntaxChecker) {
        this.efficiencyStrategy = efficiencyStrategy;
        this.syntaxChecker = syntaxChecker;
    }

    public Path createTimeTableFile(String fileName) throws IOException, ParseException {
        String homePath = System.getProperty("user.home");
        List<String> stringList = FileUtils.readFile(fileName);
        List<Service> services =  convertStringToService(stringList,syntaxChecker);
        Map<String,List<Service>> timeTableObjects = createTimeTableObjects(services);
        return FileUtils.writeFile(timeTableObjects, homePath+"/resultTimeTable.txt");

    }
    public List<Service> convertStringToService(List<String> stringServices, SyntaxChecker syntaxChecker) throws ParseException {
        List<Service> services = new ArrayList<>();
        long lineNumber = 1;
        for(String srv : stringServices){
            validateSyntax(srv, lineNumber, syntaxChecker);
            Service service = mapToService(srv, syntaxChecker);
            validateSemantic(service, lineNumber);
            if(!isLongService(service)){
                services.add(service);
            }
            lineNumber++;
        }
        return services;
    }

    private void validateSyntax(String srv, long lineNumber, SyntaxChecker syntaxChecker) {
        if(!syntaxChecker.getPattern().matcher(srv).find()){
            throw new InvalidSyntaxException(String.format("invalid formant at line %d",lineNumber));
        }
    }

    private Service mapToService(String stringService, SyntaxChecker syntaxChecker) throws ParseException {
        Map<String, Integer> mapping = syntaxChecker.getMapping();
        String[] serviceElements = stringService.split(syntaxChecker.getSeparator());
        return new Service(Company.valueOf(serviceElements[mapping.get("company")]),
                convertStringToDate(serviceElements[mapping.get("departureTime")])
                ,convertStringToDate(serviceElements[mapping.get("arrivalTime")]));

    }

    private boolean isLongService(Service service){
        return (service.arrivalTime.getTime() - service.departureTime.getTime()) / (60*60*1000) > 1 ;
    }

    public void validateSemantic(Service service, long lineNumber) {
        if(service.departureTime.compareTo(service.arrivalTime) > 0){
            throw new InvalidSemanticException(String.format("invalid semantic at line %d departure time could " +
                    "not be greater than arrival time",lineNumber));
        }
        if(service.arrivalTime.compareTo(service.departureTime) == 0){
            throw new InvalidSemanticException(String.format("invalid semantic at line %d departure time could " +
                    "not be equal to arrival time",lineNumber));
        }
    }

    public Map<String,List<Service>> createTimeTableObjects(List<Service> serviceList){
        Map<String,List<Service>> serviceMap = new HashMap<>();
        List<Service> timeTableForPosh = new ArrayList<>();
        List<Service> timeTableForGrotty = new ArrayList<>();

        extractEfficientServices(timeTableForPosh, timeTableForGrotty , serviceList);

        Collections.sort(timeTableForPosh);
        Collections.sort(timeTableForGrotty);

        serviceMap.put(Company.Posh.toString(),timeTableForPosh);
        serviceMap.put(Company.Grotty.toString(),timeTableForGrotty);

        return serviceMap;
    }

    private void extractEfficientServices(List<Service> timeTableForPosh , List<Service> timeTableForGrotty ,
                                          List<Service> serviceList){
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
