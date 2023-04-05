package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Exception.insurancePolicyExceptionHandler.insurancePolicyException;
import com.example.demo.Model.InsurancePolicy;
import com.example.demo.Service.insurancePolicyService;

@RestController
public class InsurancePoliciesController {
	
	@Autowired
	insurancePolicyService insurancepolicyservice;
	
	
	
	@GetMapping("/api/policies")
	public ResponseEntity<?> getAllPolicies() {
		
		List<InsurancePolicy> list = new ArrayList<>();
		
		try {
			list = insurancepolicyservice.getInsurancePolicies();
		}
		catch (insurancePolicyException e) {
			return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping("/api/policies/{id}")
	public ResponseEntity<?> getPolicy(@PathVariable String id) {
		
		if(id == null || id.isEmpty()) {
			insurancePolicyException iException = new insurancePolicyException("Invalid Request");
			return new ResponseEntity<>(iException,HttpStatus.BAD_REQUEST);
		}
		InsurancePolicy insurancepolicy = null;
		try {
			insurancepolicy = insurancepolicyservice.getPolicyById(id);
		}
		catch (insurancePolicyException e) {
			return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(insurancepolicy,HttpStatus.OK);
	}
	@PostMapping("/api/policies")
	public ResponseEntity<?> createPolicy(@RequestBody InsurancePolicy insurancepolicy) {
		if(!checkValid(insurancepolicy)) {
			insurancePolicyException iException = new insurancePolicyException("Fields should not be empty.");
			return new ResponseEntity<>(iException,HttpStatus.BAD_REQUEST);
		}
		try {
			insurancepolicyservice.registerInsurancePolicy(insurancepolicy);
		}
		catch (insurancePolicyException e) {
			return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Successfully Registered.",HttpStatus.OK);
	}
	@PutMapping("/api/policies/{id}")
	public ResponseEntity<?> updateInsurancePolicy(@PathVariable String id,@RequestBody InsurancePolicy insurancepolicy) {
		if(!checkValid(insurancepolicy)) {
			insurancePolicyException iException = new insurancePolicyException("Fields should not be empty.");
			return new ResponseEntity<>(iException,HttpStatus.BAD_REQUEST);
		}
		try {
			insurancepolicyservice.updatePolicy(id,insurancepolicy);
		}
		catch (insurancePolicyException e) {
			return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Updated Successfully.",HttpStatus.OK);
	}
	@DeleteMapping("/api/policies/{id}")
	public ResponseEntity<?> deletePolicy(@PathVariable String id) {
		
		if(id == null || id.isEmpty()) {
			insurancePolicyException iException = new insurancePolicyException("Invalid Request");
			return new ResponseEntity<>(iException,HttpStatus.BAD_REQUEST);
		}
		try {
			insurancepolicyservice.deletePolicy(id);
		}
		catch (insurancePolicyException e) {
			return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("deleted successfully",HttpStatus.OK);
	}
	
	public boolean checkValid(InsurancePolicy ip) {
		if(ip == null || ip.getPolicyType()== null || ip.getCoverageAmount() == null || ip.getStartDate() == null || ip.getEndDate() == null || ip.getPremium() ==null ) {
			return false;
		}
		
		return true;
	}
}
