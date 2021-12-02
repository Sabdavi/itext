package com.itext;


import com.ittext.Company;
import com.ittext.Service;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {

    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

    @Test
    public void testServiceEquality() throws ParseException {
        Service service = new Service(Company.Posh, formatter.parse("10:10"), formatter.parse("11:00"));
        Service otherService = new Service(Company.Posh, formatter.parse("10:10"), formatter.parse("11:00"));

        Service service1 = new Service(Company.Grotty, formatter.parse("10:10"), formatter.parse("11:00"));
        Service otherService1 = new Service(Company.Posh, formatter.parse("10:10"), formatter.parse("11:00"));

        Service service2 = new Service(Company.Grotty, formatter.parse("10:10"), formatter.parse("12:00"));
        Service otherService2 = new Service(Company.Posh, formatter.parse("10:10"), formatter.parse("11:00"));

        Service service3 = new Service(Company.Posh, formatter.parse("10:20"), formatter.parse("11:00"));
        Service otherService3 = new Service(Company.Posh, formatter.parse("10:10"), formatter.parse("11:00"));

        assertTrue(service.equals(otherService));
        assertFalse(service1.equals(otherService1));
        assertFalse(service2.equals(otherService2));
        assertFalse(service3.equals(otherService3));
    }

    @Test
    public void TestSortServices() throws ParseException {
        List<Service> services = new ArrayList<>();
        services.add(new Service(Company.Posh, formatter.parse("10:10"), formatter.parse("11:00")));
        services.add(new Service(Company.Posh, formatter.parse("10:50"), formatter.parse("11:00")));
        services.add(new Service(Company.Posh, formatter.parse("09:10"), formatter.parse("11:00")));
        services.add(new Service(Company.Posh, formatter.parse("07:10"), formatter.parse("10:50")));
        services.add(new Service(Company.Posh, formatter.parse("10:20"), formatter.parse("11:00")));
        services.add(new Service(Company.Posh, formatter.parse("09:40"), formatter.parse("10:50")));

        List<Service> sortedServices = new ArrayList<>();
        sortedServices.add(new Service(Company.Posh, formatter.parse("07:10"), formatter.parse("10:50")));
        sortedServices.add(new Service(Company.Posh, formatter.parse("09:10"), formatter.parse("11:00")));
        sortedServices.add(new Service(Company.Posh, formatter.parse("09:40"), formatter.parse("10:50")));
        sortedServices.add(new Service(Company.Posh, formatter.parse("10:10"), formatter.parse("11:00")));
        sortedServices.add(new Service(Company.Posh, formatter.parse("10:20"), formatter.parse("11:00")));
        sortedServices.add(new Service(Company.Posh, formatter.parse("10:50"), formatter.parse("11:00")));

        Collections.sort(services);
        for(int i = 0 ; i <= 0 ; i++){
            assertEquals(sortedServices.get(i),services.get(i));
        }



    }
}
