package service.customers.service;

import service.customers.model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAllCustomers();
}
