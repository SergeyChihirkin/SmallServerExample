package service.customers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.customers.model.GetCustomersResponse;
import service.customers.service.CustomerService;

@RestController
@RequestMapping("/customers/")
public class CustomersController {
    @Autowired
    CustomerService customerService;

    @RequestMapping(value = "/getCustomers", method = RequestMethod.GET)
    public ResponseEntity<GetCustomersResponse> listAllUsers() {
        final GetCustomersResponse response = new GetCustomersResponse();
        response.setCustomers(customerService.findAllCustomers());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
