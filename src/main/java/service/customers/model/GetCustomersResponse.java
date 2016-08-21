package service.customers.model;


import java.util.ArrayList;
import java.util.List;

public class GetCustomersResponse {
    private List<Customer> customers;

    public List<Customer> getCustomers() {
        if (customers == null)
            customers = new ArrayList<>();
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
