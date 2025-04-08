package com.customerservice.service.impl;

import com.customerservice.entity.Customer;
import com.customerservice.repository.CustomerRepository;
import com.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Override
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        if (repository.existsByEmail(customer.getEmail())) {
            throw new RuntimeException("Email đã được đăng ký!");
        }
        return repository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        customer.setId(id);
        return repository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        repository.deleteById(id);
    }
}
