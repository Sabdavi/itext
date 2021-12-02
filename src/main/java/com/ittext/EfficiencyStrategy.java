package com.ittext;

public interface EfficiencyStrategy {
    default boolean isMoreEfficient(Service service, Service otherService) {
        if (service.equals(otherService)) {
            return false;
        } else if (hasSameDepartureAndArrival(service, otherService)) {
            return service.getCompany().equals(Company.Posh);
        } else {
            return ((service.getDepartureTime().equals(otherService.getDepartureTime())
                            && service.getArrivalTime().compareTo(otherService.getArrivalTime()) < 0) ||
                    (service.getArrivalTime().equals(otherService.getArrivalTime())
                            && service.getDepartureTime().compareTo(otherService.getDepartureTime()) > 0)) ||
                    (service.getDepartureTime().compareTo(otherService.getDepartureTime()) > 0
                            && service.getArrivalTime().compareTo(otherService.getArrivalTime()) < 0);
        }
    }

    private boolean hasSameDepartureAndArrival(Service service, Service otherService) {
        return service.getDepartureTime().equals(otherService.getDepartureTime())
                && service.getArrivalTime().equals(otherService.getArrivalTime());
    }
}
