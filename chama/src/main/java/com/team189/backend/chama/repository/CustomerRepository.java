package com.team189.backend.chama.repository;

import com.team189.backend.chama.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 *
 * @author moha
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customers, Long>{

}
