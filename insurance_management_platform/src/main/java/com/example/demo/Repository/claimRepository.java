package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Claim;

@Repository
public interface claimRepository extends JpaRepository<Claim, Long>{
	
}
