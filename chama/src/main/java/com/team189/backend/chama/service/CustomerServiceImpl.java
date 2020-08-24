/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team189.backend.chama.service;

import com.team189.backend.chama.entity.Customers;
import com.team189.backend.chama.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author moha
 */
public class CustomerServiceImpl implements CustomerService {
   @Autowired
   CustomerRepository customerRepository;
    @Override
    public void createAccount(Customers customers) {
        customerRepository.save(customers);
    }
    
}
