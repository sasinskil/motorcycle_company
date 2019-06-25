package info.sasinski.response;

import info.sasinski.entity.Customer;
import info.sasinski.entity.Employee;
import info.sasinski.entity.MotorcycleDetails;

public class ActionResponse {
    private MotorcycleDetails motorcycleDetails;
    private Employee employee;
    private Customer customer;

    public ActionResponse() {
    }

    public ActionResponse(MotorcycleDetails motorcycleDetails, Employee employee, Customer customer) {
        this.motorcycleDetails = motorcycleDetails;
        this.employee = employee;
        this.customer = customer;
    }

    public MotorcycleDetails getMotorcycleDetails() {
        return motorcycleDetails;
    }

    public void setMotorcycleDetails(MotorcycleDetails motorcycleDetails) {
        this.motorcycleDetails = motorcycleDetails;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "ActionResponse{" +
                "motorcycleDetails=" + motorcycleDetails +
                ", employee=" + employee +
                ", customer=" + customer +
                '}';
    }
}
