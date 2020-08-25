///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.team189.backend.chama.service;
//
//import com.team189.backend.chama.entity.Customers;
//import com.team189.backend.chama.repository.CustomerRepository;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import com.team189.backend.chama.exception.NonRollbackException;
//import java.util.Collections;
//import com.team189.backend.chama.utils.Utils;
//
///**
// *
// * @author moha
// */
//public class CustomerServiceImpl implements CustomerService {
//   @Autowired
//   CustomerRepository customerRepository;
//   @Autowired
//   CrudService crudeService;
//    @Override
//    public void createAccount(Customers customers) {
//        customerRepository.save(customers);
//    }
//
//    @Override
//    public String changeCustomerPin(String msisdn, String pin, String newpin) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public String fetchCustomers() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//   
//    }
//    
//    
//
