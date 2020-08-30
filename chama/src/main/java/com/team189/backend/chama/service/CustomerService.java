/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team189.backend.chama.service;

import com.team189.backend.chama.exception.NonRollbackException;
import org.springframework.stereotype.Component;

/**
 *
 * @author moha
 */
@Component
public interface CustomerService {

    public String changeCustomerPin(String msisdn, String pin, String newpin);

    public String fetchCustomers();

    public String AuthenticateCustomer(String msisdn, String pin);
    
    public String processSavingsDeposit(String msisdn, String amount, String ref) throws NonRollbackException;


    
}
