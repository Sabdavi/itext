package com.itext;

import com.ittext.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.itext.FileUtilsTest.compareToFile;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TimeTableTest {

    List<Service> initTimeTable;
    List<Service> resultTimeTable;
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
    EfficiencyStrategy efficiencyStrategy = new EfficiencyStrategyImpl();
    SyntaxChecker syntaxChecker = new SyntaxCheckerImpl();
    TimeTable timeTable = new TimeTable(efficiencyStrategy, syntaxChecker);
    ClassLoader classLoader = getClass().getClassLoader();

    @BeforeAll
    public void initEnvironment() throws ParseException {
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


        assertEquals(moreEfficientServicesThanThisService.size(), efficientServices.size());
        assertEquals(moreEfficientServicesThanThisService1.size(), efficientServices1.size());
        assertEquals(moreEfficientServicesThanThisService2.size(), efficientServices2.size());

        for (int i = 0; i < moreEfficientServicesThanThisService.size(); i++) {
            assertTrue(moreEfficientServicesThanThisService.get(i).equals(efficientServices.get(i)));
        }

        for (int i = 0; i < services.size(); i++) {
            assertTrue(moreEfficientServicesThanThisService1.get(i).equals(efficientServices1.get(i)));
        }
    }

    @Test
    public void testCreateTimeTableObjects() {
        Map<String, List<Service>> timeTableObjects = timeTable.createTimeTableObjects(initTimeTable);
        List<Service> timeTableList = new ArrayList<>();
        timeTableList.addAll(timeTableObjects.get(Company.Posh.toString()));
        timeTableList.addAll(timeTableObjects.get(Company.Grotty.toString()));

        assertTrue(timeTableList.size() == resultTimeTable.size());
        for (int i = 0; i < timeTableList.size(); i++) {
            if (timeTableList.get(i) != null) {
                assertEquals(timeTableList.get(i), resultTimeTable.get(i));
            }
        }
    }

    @Test
    public void testCreateTimeTableFile() throws IOException, ParseException {
        Path createdPath = timeTable.createTimeTableFile(classLoader.getResource("timeTable.txt").getPath());
        Path existedPath = Path.of(classLoader.getResource("resultTimeTable.txt").getPath());

        assertEquals(compareToFile(createdPath,existedPath),-1);
    }

    @Test
    public void testCreateServiceObjects() throws ParseException {
        List<String> stringServices = Arrays.asList(
                "Posh 10:15 11:10",
                "Posh 10:10 11:00",
                "Grotty 10:10 11:00",
                "Grotty 16:30 18:45",
                "Posh 12:05 12:30",
                "Grotty 12:30 13:25",
                "Grotty 12:45 13:25",
                "Posh 17:25 18:01"
        );
        List<Service> services = timeTable.convertStringToService(stringServices, syntaxChecker);
        assertTrue(services.size() == stringServices.size());
        for(int i = 0 ; i < services.size() ; i++ ){
            assertEquals(services.get(i),initTimeTable.get(i));
        }
    }

    @Test
    public void testCreateServiceObjects1() {
        List<String> stringServices = Arrays.asList(
                "fdsfsd 10:10 11:00"

        );
        assertThrows(InvalidSyntaxException.class,() -> timeTable.convertStringToService(stringServices, syntaxChecker));
    }

    @Test
    public void testCreateServiceObjects2() {
        List<String> stringServices = Arrays.asList(
                "Posh qw:10 11:00"

        );
        assertThrows(InvalidSyntaxException.class,() -> timeTable.convertStringToService(stringServices, syntaxChecker));
    }

    @Test
    public void testCreateServiceObjects3() {
        List<String> stringServices = Arrays.asList(
                "Posh 10:10 asas:00"

        );
        assertThrows(InvalidSyntaxException.class,() -> timeTable.convertStringToService(stringServices, syntaxChecker));
    }

}
