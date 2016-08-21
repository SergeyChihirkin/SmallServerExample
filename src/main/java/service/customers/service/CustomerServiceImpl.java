package service.customers.service;

import org.springframework.stereotype.Service;
import service.customers.model.Customer;

import java.util.Arrays;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Customer[] CUSTOMERS_DATA = {
            new Customer("John", "Doe"),
            new Customer("Anna", "Smith"),
            new Customer("Peter", "Jones")
    };

    @Override
    public List<Customer> findAllCustomers() {
        return Arrays.asList(CUSTOMERS_DATA);
    }
}
