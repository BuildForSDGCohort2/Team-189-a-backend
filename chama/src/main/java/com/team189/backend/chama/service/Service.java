package com.team189.backend.chama.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.team189.backend.chama.entity.Customers;
import com.team189.backend.chama.exception.NonRollbackException;
import com.team189.backend.chama.utils.Utils;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author moha
 */
public class Service {
   @Autowired
   CrudService crudeService;

    public String changeCustomerPin(String msisdn, String pin, String newpin) {
         String q = "SELECT firstname FROM Customers where msisdn='"+msisdn+"'";
         List<Object> response = crudeService.fetchWithNativeQuery(q, Collections.EMPTY_MAP, 0, 1);
        if(response.isEmpty()){
             try {
                 throw new NonRollbackException("Customer record not found");
             } catch (NonRollbackException ex) {
                 Logger.getLogger(CustomerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
          String fname= String.valueOf(response.get(0));
          String sq = "SELECT pin FROM Customers where msisdn='"+msisdn+"'";
          List<Object> rs = crudeService.fetchWithNativeQuery(q, Collections.EMPTY_MAP, 0, 1);
          if(rs.get(0).toString().equals(Utils.getSHA256(pin))){
          String sql = "UPDATE Customers SET pin='"+Utils.getSHA256(newpin)+"' WHERE msidn='"+msisdn+"'";
          crudeService.executeNativeQuery(sql, Collections.EMPTY_MAP);
          }
          return fname;
    }

    public String fetchCustomers() {
        try{
        Map<String,String> map = new HashMap<>();
        String q = "SELECT * FROM Customers where status='active'";
        List<Customers> rs = crudeService.fetchWithHibernateQuery(q, Collections.EMPTY_MAP);
        for(Customers tx:rs){
            map.put("firstname", tx.getFirstname());
            map.put("surname",tx.getSurname());
            map.put("msisdn", tx.getMsisdn());
            map.put("status", tx.getStatus());
            map.put("idno",tx.getIdno());
            map.put("location",tx.getLocation());
        }
        return new ObjectMapper().writeValueAsString(map);
        }catch(JsonProcessingException | HibernateException e){
            e.getLocalizedMessage();
        }
        return null;
    }

    public String AuthenticateCustomer(String msisdn, String pin) {
        try{
         String q = "SELECT pin FROM Customers where msisdn='"+msisdn+"'";
         List<Object> response = crudeService.fetchWithNativeQuery(q, Collections.EMPTY_MAP, 0, 1);
         if(String.valueOf(response.get(0)).equalsIgnoreCase(Utils.getSHA256(pin))){
          String sql = "SELECT firstname FROM Customers where msisdn='"+msisdn+"'";
          String msg = crudeService.fetchWithNativeQuery(q, Collections.EMPTY_MAP, 0, 1).get(0).toString();
          return msg; 
         }else{
             throw new NonRollbackException("Invalid Credentials");
         }
        }catch(Exception e){
           return e.getLocalizedMessage();
        }
    }
    
}
