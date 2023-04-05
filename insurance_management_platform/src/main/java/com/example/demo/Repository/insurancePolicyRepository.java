package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.InsurancePolicy;

@Repository
public interface insurancePolicyRepository extends JpaRepository<InsurancePolicy, Long>{
	
}
