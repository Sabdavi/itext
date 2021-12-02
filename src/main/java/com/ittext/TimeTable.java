package com.ittext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TimeTable {

    public List<Service> createTimeTable(List<Service> serviceList){
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
