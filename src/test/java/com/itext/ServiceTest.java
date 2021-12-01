package com.itext;


import com.ittext.Company;
import com.ittext.Service;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServiceTest {

    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

    @Test
    public void testServiceEquality() throws ParseException {
        Service service = new Service(Company.POSH, formatter.parse("10:10"), formatter.parse("11:00"));
        Service otherService = new Service(Company.POSH, formatter.parse("10:10"), formatter.parse("11:00"));

        Service service1 = new Service(Company.GROTTY, formatter.parse("10:10"), formatter.parse("11:00"));
        Service otherService1 = new Service(Company.POSH, formatter.parse("10:10"), formatter.parse("11:00"));

        Service service2 = new Service(Company.GROTTY, formatter.parse("10:10"), formatter.parse("12:00"));
        Service otherService2 = new Service(Company.POSH, formatter.parse("10:10"), formatter.parse("11:00"));

        Service service3 = new Service(Company.POSH, formatter.parse("10:20"), formatter.parse("11:00"));
        Service otherService3 = new Service(Company.POSH, formatter.parse("10:10"), formatter.parse("11:00"));

        assertTrue(service.equals(otherService));
        assertFalse(service1.equals(otherService1));
        assertFalse(service2.equals(otherService2));
        assertFalse(service3.equals(otherService3));
    }

    @Test
    public void testIsMoreEfficientService() throws ParseException {
        Service service = new Service(Company.POSH, formatter.parse("10:10"), formatter.parse("11:00"));
        Service service1 = new Service(Company.POSH, formatter.parse("10:10"), formatter.parse("11:00"));
        Service service2 = new Service(Company.GROTTY, formatter.parse("10:10"), formatter.parse("11:00"));
        Service service3 = new Service(Company.GROTTY, formatter.parse("10:10"), formatter.parse("10:50"));
        Service service4 = new Service(Company.GROTTY, formatter.parse("10:20"), formatter.parse("11:00"));
        Service service5 = new Service(Company.GROTTY, formatter.parse("10:20"), formatter.parse("10:50"));
        Service service6 = new Service(Company.GROTTY, formatter.parse("10:05"), formatter.parse("11:00"));
        Service service7 = new Service(Company.GROTTY, formatter.parse("10:10"), formatter.parse("11:10"));
        Service service8 = new Service(Company.GROTTY, formatter.parse("10:05"), formatter.parse("11:10"));

        assertFalse(service.isMoreEfficient(service1));
        assertFalse(service.isMoreEfficient(service2));
        assertTrue(service.isMoreEfficient(service3));
        assertTrue(service.isMoreEfficient(service4));
        assertTrue(service.isMoreEfficient(service5));
        assertFalse(service.isMoreEfficient(service6));
        assertFalse(service.isMoreEfficient(service7));
        assertFalse(service.isMoreEfficient(service8));
    }
}
