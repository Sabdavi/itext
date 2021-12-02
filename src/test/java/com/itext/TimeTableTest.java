package com.itext;

import com.ittext.Company;
import com.ittext.Service;
import com.ittext.TimeTable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TimeTableTest {

    List<Service> initTimeTable;
    List<Service> resultTimeTable;
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
    TimeTable timeTable = new TimeTable();

    @BeforeAll
    public  void initEnvironment() throws ParseException {
        initTimeTable = new ArrayList<>();
        initTimeTable.add(new Service(Company.Posh, formatter.parse("10:15"), formatter.parse("11:10")));
        initTimeTable.add(new Service(Company.Posh, formatter.parse("10:10"), formatter.parse("11:00")));
        initTimeTable.add(new Service(Company.Grotty, formatter.parse("10:10"), formatter.parse("11:00")));
        initTimeTable.add(new Service(Company.Grotty, formatter.parse("16:30"), formatter.parse("18:45")));
        initTimeTable.add(new Service(Company.Posh, formatter.parse("12:05"), formatter.parse("12:30")));
        initTimeTable.add(new Service(Company.Grotty, formatter.parse("12:30"), formatter.parse("13:25")));
        initTimeTable.add(new Service(Company.Grotty, formatter.parse("12:45"), formatter.parse("13:25")));
        initTimeTable.add(new Service(Company.Posh, formatter.parse("17:25"), formatter.parse("18:01")));

        resultTimeTable = new ArrayList<>();
        resultTimeTable.add(new Service(Company.Posh, formatter.parse("10:10"), formatter.parse("11:00")));
        resultTimeTable.add(new Service(Company.Posh, formatter.parse("10:15"), formatter.parse("11:10")));
        resultTimeTable.add(new Service(Company.Posh, formatter.parse("12:05"), formatter.parse("12:30")));
        resultTimeTable.add(new Service(Company.Posh, formatter.parse("17:25"), formatter.parse("18:01")));
        resultTimeTable.add(new Service(Company.Grotty, formatter.parse("12:45"), formatter.parse("13:25")));


    }

    @Test
    public void testGetMoreEfficientServicesThanThisService() throws ParseException {
        Service service = new Service(Company.Posh, formatter.parse("10:15"), formatter.parse("11:10"));
        List<Service> services = new ArrayList<>();
        services.add(new Service(Company.Posh, formatter.parse("10:15"), formatter.parse("11:5")));
        services.add(new Service(Company.Posh, formatter.parse("10:17"), formatter.parse("11:10")));
        services.add(new Service(Company.Posh, formatter.parse("10:18"), formatter.parse("11:9")));

        List<Service> efficientServices = new ArrayList<>();
        efficientServices.add(new Service(Company.Posh, formatter.parse("10:15"), formatter.parse("11:5")));
        efficientServices.add(new Service(Company.Posh, formatter.parse("10:17"), formatter.parse("11:10")));
        efficientServices.add(new Service(Company.Posh, formatter.parse("10:18"), formatter.parse("11:9")));


        List<Service> services1 = new ArrayList<>();
        services1.add(new Service(Company.Posh, formatter.parse("10:15"), formatter.parse("11:5")));
        services1.add(new Service(Company.Posh, formatter.parse("10:17"), formatter.parse("11:10")));
        services1.add(new Service(Company.Posh, formatter.parse("10:18"), formatter.parse("11:9")));
        services1.add(new Service(Company.Posh, formatter.parse("10:10"), formatter.parse("11:10")));
        services1.add(new Service(Company.Posh, formatter.parse("10:15"), formatter.parse("11:15")));
        services1.add(new Service(Company.Posh, formatter.parse("10:10"), formatter.parse("11:15")));

        List<Service> efficientServices1 = new ArrayList<>();
        efficientServices1.add(new Service(Company.Posh, formatter.parse("10:15"), formatter.parse("11:5")));
        efficientServices1.add(new Service(Company.Posh, formatter.parse("10:17"), formatter.parse("11:10")));
        efficientServices1.add(new Service(Company.Posh, formatter.parse("10:18"), formatter.parse("11:9")));

        List<Service> services2 = new ArrayList<>();
        services2.add(new Service(Company.Posh, formatter.parse("10:10"), formatter.parse("11:10")));
        services2.add(new Service(Company.Posh, formatter.parse("10:15"), formatter.parse("11:15")));
        services2.add(new Service(Company.Posh, formatter.parse("10:10"), formatter.parse("11:15")));

        List<Service> efficientServices2 = new ArrayList<>();


        List<Service> moreEfficientServicesThanThisService = timeTable.
                getMoreEfficientServicesThanThisService(service, services);

        List<Service> moreEfficientServicesThanThisService1 = timeTable.
                getMoreEfficientServicesThanThisService(service, services1);

        List<Service> moreEfficientServicesThanThisService2 = timeTable.
                getMoreEfficientServicesThanThisService(service, services2);


        assertEquals(moreEfficientServicesThanThisService.size(),efficientServices.size());
        assertEquals(moreEfficientServicesThanThisService1.size(),efficientServices1.size());
        assertEquals(moreEfficientServicesThanThisService2.size(),efficientServices2.size());

        for(int i = 0 ; i < moreEfficientServicesThanThisService.size() ; i++){
            assertTrue(moreEfficientServicesThanThisService.get(i).equals(efficientServices.get(i)));
        }

        for(int i = 0 ; i < services.size() ; i++){
            assertTrue(moreEfficientServicesThanThisService1.get(i).equals(efficientServices1.get(i)));
        }
    }

    @Test
    public void testCreateTimeTable(){
        List<Service> timeTableList = timeTable.createTimeTable(initTimeTable);

        assertTrue(timeTableList.size()==resultTimeTable.size());
        for(int i = 0 ; i < timeTableList.size() ; i++){
            assertEquals(timeTableList.get(i),resultTimeTable.get(i));
        }
    }
}
