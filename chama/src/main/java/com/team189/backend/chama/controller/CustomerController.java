/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team189.backend.chama.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team189.backend.chama.entity.Chama;
import com.team189.backend.chama.entity.Customers;
import com.team189.backend.chama.exception.NonRollbackException;
import com.team189.backend.chama.model.CustomerPayload;
import com.team189.backend.chama.model.PinChange;
import com.team189.backend.chama.service.CrudService;
import com.team189.backend.chama.service.CustomerService;
import com.team189.backend.chama.utils.Utils;
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
import com.team189.backend.chama.model.ChamaModel;
import com.team189.backend.chama.model.SaveModel;
import java.util.logging.Level;


/**
 *
 * @author moha
 */
@RestController
@RequestMapping(value ="/team189")
public class CustomerController {
    private final Logger LOG = LoggerFactory.getLogger(CustomerController.class);
     @Autowired
     CrudService crudService;
     @Autowired
     CustomerService service;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
     Map<String, Object> responseMap = new HashMap<>();
    @PostMapping("/user/registration")
	public ResponseEntity<String> createCustomer(@RequestBody CustomerPayload payload) {
		try {
                    LOG.info("MOGAKA");
		 Customers customers = new Customers();
                 Integer pin = ThreadLocalRandom.current().nextInt(1001, 9999);
                 customers.setFirstname(payload.getFirstname());
                 customers.setSurname(payload.getSurname());
                 customers.setGender(payload.getGender());
                 customers.setIdno(payload.getIdno());
                 customers.setStatus("active");
                 customers.setDateCreated(new Date());
                 customers.setDob(sdf.parse(payload.getDob()));
                 customers.setPin(Utils.getSHA256(String.valueOf(pin)));
                 customers.setLocation(payload.getLocation());
                 customers.setMarital_status(payload.getMaritalStatus());
                 customers.setMsisdn(payload.getMsisdn());
                 customers.setChama_id(payload.getChama_id());
                 crudService.save(customers);
                 responseMap.put("responseCode","00");
                 responseMap.put("responseMessage","Successfully registered,your pin is "+String.valueOf(pin));
			return new ResponseEntity<>(new ObjectMapper().writeValueAsString(responseMap), HttpStatus.OK);
		} catch (JsonProcessingException | ParseException e) {
                 responseMap.put("responseCode", "01");
                 responseMap.put("responseMessSage", e.getMessage());
                }
                return null;
	}
     @PostMapping(value = "/customer/pinchange")
    public ResponseEntity<String> pinChange(@RequestBody PinChange payload) throws JsonProcessingException {
        try {
            String customer_name = service.changeCustomerPin(payload.getMsisdn(), payload.getPin(), payload.getNewpin());   
            responseMap.put("responseCode", "00");
            responseMap.put("responseMessage", "Dear "+customer_name+ "Change Pin request processed successfully");
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(responseMap), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            responseMap.put("responseCode", "01");
            responseMap.put("responseMessage", e.getMessage());
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(responseMap), HttpStatus.OK);
        }
    }
      @PostMapping(value = "/customers")
    public ResponseEntity<String> fetchCustomers() throws JsonProcessingException {
        try {
            String customers = service.fetchCustomers();   
            responseMap.put("responseCode", "00");
            responseMap.put("responseMessage",customers );
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(responseMap), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            responseMap.put("responseCode", "01");
            responseMap.put("responseMessage", e.getMessage());
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(responseMap), HttpStatus.OK);
        }
    }
      @PostMapping(value = "/customer/pinauthentication")
    public ResponseEntity<String> AuthenticateCustomer(@RequestBody PinChange payload) throws JsonProcessingException {
        try {
            String customer_name = service.AuthenticateCustomer(payload.getMsisdn(),payload.getPin());   
            responseMap.put("responseCode", "00");
            responseMap.put("responseMessage", "Dear "+customer_name+ "Change Pin request processed successfully");
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(responseMap), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            responseMap.put("responseCode", "01");
            responseMap.put("responseMessage", e.getMessage());
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(responseMap), HttpStatus.OK);
        }
    }
    @PostMapping(value="/chama/registration")
    public ResponseEntity<String>registerChame(@RequestBody ChamaModel payload){
        try{
          Chama chama = new Chama();
          chama.setChama_name(payload.getName());
          chama.setChama_id(payload.getChamaId());
          crudService.save(chama);
            responseMap.put("responseCode", "00");
            responseMap.put("responseMessage", "Chama Created Successfully");
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(responseMap),HttpStatus.OK);
            
    }catch(JsonProcessingException e){
            try {
                responseMap.put("responseCode", "01");
                responseMap.put("responseMessage", e.getMessage()); 
                return new ResponseEntity<>(new ObjectMapper().writeValueAsString(responseMap), HttpStatus.OK);
            } catch (JsonProcessingException ex) {
                java.util.logging.Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
   return null; 
}     
/**
 * 
 * @param payload
 * @return 
 */
      @PostMapping(value="/savings")
    public ResponseEntity<String>SaveMoney(@RequestBody SaveModel payload) throws NonRollbackException, JsonProcessingException{
         try {
            String customers = service.processSavingsDeposit(payload.getAmount(),payload.getMsisdn(),"N"+payload.getMsisdn());   
            responseMap.put("responseCode", "00");
            responseMap.put("responseMessage","Request Received for Processing" );
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(responseMap), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            responseMap.put("responseCode", "01");
            responseMap.put("responseMessage", e.getMessage());
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(responseMap), HttpStatus.OK);
        }
            
}
}

