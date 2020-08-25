/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team189.backend.chama.repository;

import com.team189.backend.chama.entity.Customers;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author moha
 */
public interface CustomerRepository extends CrudRepository<Customers, Long> {
    @Query("SELECT COUNT(c.id) FROM Customers c WHERE c.msisdn = :msisdn")
    public long customerRecordCount(@Param("msisdn") String msisdn);

    @Query("SELECT c FROM Customers c WHERE  c.msisdn = :msisdn")
    public Customers fetchCustomerRecordByPhone(@Param("msisdn") String msisdn);
    
}
