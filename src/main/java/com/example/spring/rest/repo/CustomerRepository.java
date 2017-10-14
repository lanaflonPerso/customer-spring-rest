package com.example.spring.rest.repo;

import com.example.spring.rest.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository <Customer, Long>{
}
