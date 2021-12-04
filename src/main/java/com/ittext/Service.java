package com.ittext;

import java.util.Date;

import static com.ittext.util.TimeUtils.convertDateToString;

public class Service implements Comparable<Service> {

    Company company;
    Date departureTime;
    Date arrivalTime;

    public Service(Company company, Date departureTime, Date arrivalTime) {
        this.company = company;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Service)) {
            return false;
        }
        Service service = (Service) obj;
        return service.company == this.company &&
                service.departureTime.equals(this.departureTime) &&
                service.arrivalTime.equals(this.arrivalTime);
    }

    @Override
    // @todo we assumed that combination of these three fields are unique,otherwise we should develop a better hash function
    public int hashCode() {
        return (company.toString() + departureTime.toString() + arrivalTime.toString()).hashCode();
    }

    @Override
    public String toString() {
        return company.toString()+" "+ convertDateToString(departureTime)+" "+convertDateToString(arrivalTime);
    }

    @Override
    public int compareTo(Service o) {
        return departureTime.compareTo(o.getDepartureTime());
    }
}
