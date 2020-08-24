/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team189.backend.chama.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team189.backend.chama.entity.Customers;
import com.team189.backend.chama.model.CustomerPayload;
import com.team189.backend.chama.service.CustomerService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author moha
 */
@RestController
@RequestMapping(value ="/team189")
public class CustomerController {
    private final Logger LOG = LoggerFactory.getLogger(CustomerController.class);
     @Autowired
    CustomerService customerService;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
     Map<String, Object> responseMap = new HashMap<>();
    @PostMapping("/user/registration")
	public ResponseEntity<String> createCustomer(@RequestBody CustomerPayload payload) {
		try {
		 Customers customers = new Customers();
                 Integer pin = ThreadLocalRandom.current().nextInt(1001, 9999);
                 customers.setFirstname(payload.getFirstname());
                 customers.setSurname(payload.getSurname());
                 customers.setGender(payload.getGender());
                 customers.setIdno(payload.getIdno());
                 customers.setStatus("active");
                 customers.setDateCreated(new Date());
                 customers.setDob(sdf.parse(payload.getDob()));
                 customers.setPin(String.valueOf(pin));
                 customerService.createAccount(customers);
                 responseMap.put("responseCode","00");
                 responseMap.put("responseMessage","Successfully registered,your pin is "+String.valueOf(pin));
			return new ResponseEntity<>(new ObjectMapper().writeValueAsString(responseMap), HttpStatus.OK);
		} catch (JsonProcessingException | ParseException e) {
                 responseMap.put("responseCode", "01");
                 responseMap.put("responseMessage", e.getMessage());
                }
                return null;
	}
    
}
