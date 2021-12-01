package com.ittext;

import java.util.ArrayList;
import java.util.List;

public class TimeTable {

    public List<Service> createTimeTable(List<Service> serviceList){
        return new ArrayList<>();
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
