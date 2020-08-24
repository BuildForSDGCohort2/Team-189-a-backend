/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team189.backend.chama.repository;

import com.team189.backend.chama.entity.Customers;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author moha
 */
public interface CustomerRepository extends CrudRepository<Customers, Long> {
    
}
