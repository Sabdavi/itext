package com.itext;

import com.ittext.Company;
import com.ittext.Service;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TimeTableTest {

    List<Service> initTimeTable;
    List<Service> resultTimeTable;
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

    @BeforeAll
    public  void initEnvironment() throws ParseException {
        initTimeTable = new ArrayList<>();
        initTimeTable.add(new Service(Company.POSH, formatter.parse("10:15"), formatter.parse("11:10")));
        initTimeTable.add(new Service(Company.POSH, formatter.parse("10:10"), formatter.parse("11:00")));
        initTimeTable.add(new Service(Company.GROTTY, formatter.parse("10:10"), formatter.parse("11:00")));
        initTimeTable.add(new Service(Company.GROTTY, formatter.parse("16:30"), formatter.parse("18:45")));
        initTimeTable.add(new Service(Company.POSH, formatter.parse("12:05"), formatter.parse("12:30")));
        initTimeTable.add(new Service(Company.GROTTY, formatter.parse("12:30"), formatter.parse("13:25")));
        initTimeTable.add(new Service(Company.GROTTY, formatter.parse("12:45"), formatter.parse("13:25")));
        initTimeTable.add(new Service(Company.POSH, formatter.parse("17:25"), formatter.parse("18:10")));

        resultTimeTable = new ArrayList<>();
        resultTimeTable.add(new Service(Company.POSH, formatter.parse("10:10"), formatter.parse("11:00")));
        resultTimeTable.add(new Service(Company.POSH, formatter.parse("10:15"), formatter.parse("11:10")));
        resultTimeTable.add(new Service(Company.POSH, formatter.parse("12:05"), formatter.parse("12:30")));
        resultTimeTable.add(new Service(Company.POSH, formatter.parse("17:25"), formatter.parse("18:10")));
        resultTimeTable.add(new Service(Company.GROTTY, formatter.parse("12:45"), formatter.parse("13:25")));


    }

    @Test
    public void TestEqualService() throws ParseException {
        Service service = new Service(Company.POSH, formatter.parse("10:10"), formatter.parse("11:00"));
        Service otherService = new Service(Company.POSH, formatter.parse("10:10"), formatter.parse("11:00"));

        assertFalse(initTimeTable.get(1).equals(initTimeTable.get(2)));
        assertTrue(service.equals(otherService));
    }
}
