package com.itext;

import com.ittext.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.itext.FileUtilsTest.compareToFile;
import static com.ittext.TimeUtils.convertStringToDate;
import static org.junit.jupiter.api.Assertions.*;

public class TimeTableTest {

    static List<Service> initTimeTable;
    static List<Service> resultTimeTable;
    static EfficiencyStrategy efficiencyStrategy;
    static SyntaxChecker syntaxChecker;
    static TimeTable timeTable;
    ClassLoader classLoader = getClass().getClassLoader();

    @BeforeAll
    public static void initEnvironment() throws ParseException {
        efficiencyStrategy = new EfficiencyStrategyImpl();
        syntaxChecker = new SyntaxCheckerImpl();
        timeTable = new TimeTable(efficiencyStrategy, syntaxChecker);

        initTimeTable = new ArrayList<>();
        initTimeTable.add(new Service(Company.Posh, convertStringToDate("10:15"), convertStringToDate("11:10")));
        initTimeTable.add(new Service(Company.Posh, convertStringToDate("10:10"), convertStringToDate("11:00")));
        initTimeTable.add(new Service(Company.Grotty, convertStringToDate("10:10"), convertStringToDate("11:00")));
        initTimeTable.add(new Service(Company.Posh, convertStringToDate("12:05"), convertStringToDate("12:30")));
        initTimeTable.add(new Service(Company.Grotty, convertStringToDate("12:30"), convertStringToDate("13:25")));
        initTimeTable.add(new Service(Company.Grotty, convertStringToDate("12:45"), convertStringToDate("13:25")));
        initTimeTable.add(new Service(Company.Posh, convertStringToDate("17:25"), convertStringToDate("18:01")));

        resultTimeTable = new ArrayList<>();
        resultTimeTable.add(new Service(Company.Posh, convertStringToDate("10:10"), convertStringToDate("11:00")));
        resultTimeTable.add(new Service(Company.Posh, convertStringToDate("10:15"), convertStringToDate("11:10")));
        resultTimeTable.add(new Service(Company.Posh, convertStringToDate("12:05"), convertStringToDate("12:30")));
        resultTimeTable.add(new Service(Company.Posh, convertStringToDate("17:25"), convertStringToDate("18:01")));
        resultTimeTable.add(new Service(Company.Grotty, convertStringToDate("12:45"), convertStringToDate("13:25")));
    }

    @Test
    public void testGetMoreEfficientServicesThanThisService() throws ParseException {
        Service service = new Service(Company.Posh, convertStringToDate("10:15"), convertStringToDate("11:10"));
        List<Service> services = new ArrayList<>();
        services.add(new Service(Company.Posh, convertStringToDate("10:15"), convertStringToDate("11:5")));
        services.add(new Service(Company.Posh, convertStringToDate("10:17"), convertStringToDate("11:10")));
        services.add(new Service(Company.Posh, convertStringToDate("10:18"), convertStringToDate("11:9")));

        List<Service> efficientServices = new ArrayList<>();
        efficientServices.add(new Service(Company.Posh, convertStringToDate("10:15"), convertStringToDate("11:5")));
        efficientServices.add(new Service(Company.Posh, convertStringToDate("10:17"), convertStringToDate("11:10")));
        efficientServices.add(new Service(Company.Posh, convertStringToDate("10:18"), convertStringToDate("11:9")));


        List<Service> services1 = new ArrayList<>();
        services1.add(new Service(Company.Posh, convertStringToDate("10:15"), convertStringToDate("11:5")));
        services1.add(new Service(Company.Posh, convertStringToDate("10:17"), convertStringToDate("11:10")));
        services1.add(new Service(Company.Posh, convertStringToDate("10:18"), convertStringToDate("11:9")));
        services1.add(new Service(Company.Posh, convertStringToDate("10:10"), convertStringToDate("11:10")));
        services1.add(new Service(Company.Posh, convertStringToDate("10:15"), convertStringToDate("11:15")));
        services1.add(new Service(Company.Posh, convertStringToDate("10:10"), convertStringToDate("11:15")));

        List<Service> efficientServices1 = new ArrayList<>();
        efficientServices1.add(new Service(Company.Posh, convertStringToDate("10:15"), convertStringToDate("11:5")));
        efficientServices1.add(new Service(Company.Posh, convertStringToDate("10:17"), convertStringToDate("11:10")));
        efficientServices1.add(new Service(Company.Posh, convertStringToDate("10:18"), convertStringToDate("11:9")));

        List<Service> services2 = new ArrayList<>();
        services2.add(new Service(Company.Posh, convertStringToDate("10:10"), convertStringToDate("11:10")));
        services2.add(new Service(Company.Posh, convertStringToDate("10:15"), convertStringToDate("11:15")));
        services2.add(new Service(Company.Posh, convertStringToDate("10:10"), convertStringToDate("11:15")));

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

        Path resultPath = Paths.get("src/test/resources/result");
        Path sourcePath = Paths.get("src/test/resources/source");

        List<Path> resultFilePaths = Files.list(resultPath).collect(Collectors.toList());
        resultFilePaths.sort(Comparator.comparing(s ->s.toString()));
        List<Path> sourceFilePaths = Files.list(sourcePath).collect(Collectors.toList());
        sourceFilePaths.sort(Comparator.comparing(s ->s.toString()));

        for(int i = 0 ; i < resultFilePaths.size() ; i++){
            Path createdFile = timeTable.createTimeTableFile(sourceFilePaths.get(i).toString());
            assertEquals(-1,compareToFile(resultFilePaths.get(i),createdFile));
        }
    }

    @Test
    public void testCreateServiceObjects() throws ParseException {
        List<String> stringServices = Arrays.asList(
                "Posh 10:15 11:10",
                "Posh 10:10 11:00",
                "Grotty 10:10 11:00",
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

    @Test
    public void TestValidateSemantic() throws ParseException {
        Service service = new Service(Company.Posh, convertStringToDate("12:15"), convertStringToDate("11:5"));
        assertThrows(InvalidSemanticException.class, () -> timeTable.validateSemantic(service,0));
    }

    @Test
    public void TestValidateSemantic1() throws ParseException {
        Service service = new Service(Company.Posh, convertStringToDate("12:15"), convertStringToDate("12:15"));
        assertThrows(InvalidSemanticException.class, () -> timeTable.validateSemantic(service,0));
    }

    @Test
    public void TestValidateSemantic2() throws ParseException {
        Service service = new Service(Company.Posh, convertStringToDate("12:15"), convertStringToDate("13:15"));
        assertDoesNotThrow(() -> timeTable.validateSemantic(service,0));
    }

}
