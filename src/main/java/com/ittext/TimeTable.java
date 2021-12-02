package com.ittext;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TimeTable {

    SimpleDateFormat format = new SimpleDateFormat("HH:mm");

    public Path createTimeTableFile(String fileName) throws IOException, ParseException {
        List<String> stringList = FileUtils.readFile(fileName);
        List<Service> services =  convertStringToService(stringList);
        List<Service> timeTableObjects = createTimeTableObjects(services);
        return FileUtils.writeFile(timeTableObjects, "/home/saeid/Downloads/itext/resultTimeTable.txt");

    }
    public List<Service> convertStringToService(List<String> stringServices) throws ParseException {
        List<Service> services = new ArrayList<>();
        for(String srv : stringServices){
            String[] serviceElements = srv.split(" ");
            Service service = new Service(Company.valueOf(serviceElements[0]),
                    format.parse(serviceElements[1]),format.parse(serviceElements[2]));
            services.add(service);
        }
        return services;
    }

    public List<Service> createTimeTableObjects(List<Service> serviceList){
        List<Service> timeTable = new ArrayList<>();
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
        timeTable.addAll(timeTableForPosh);
        timeTable.addAll(timeTableForGrotty);
        return timeTable;
    }

    public List<Service> getMoreEfficientServicesThanThisService(Service service ,List<Service> services){
        List<Service> moreEfficientServices = new ArrayList<>();
        for(Service srv : services){
            if(service.isMoreEfficient(srv)){
                moreEfficientServices.add(srv);
            }
        }
        return moreEfficientServices;
    }
}
