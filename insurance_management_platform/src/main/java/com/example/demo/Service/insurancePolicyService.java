package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Exception.clientExceptionHandler.clientException;
import com.example.demo.Exception.insurancePolicyExceptionHandler.insurancePolicyException;
import com.example.demo.Model.InsurancePolicy;
import com.example.demo.Repository.insurancePolicyRepository;

@Service
public class insurancePolicyService {
	@Autowired
	insurancePolicyRepository insurancepolicyrepository;

	public List<InsurancePolicy> getInsurancePolicies() {
		List<InsurancePolicy> list = new ArrayList<>();
		try {
			list = insurancepolicyrepository.findAll();
		}
		catch (Exception e) {
			throw new insurancePolicyException("Something went wrong while fetching insurance policy list.");
		}
		if(list.isEmpty()) {
			throw new insurancePolicyException("No insurance policy found ,List is Empty.");
		}
		return list;
	}

	public InsurancePolicy getPolicyById(String id) {
		Optional<InsurancePolicy> insurancepolicy = null;
		try {
			insurancepolicy = insurancepolicyrepository.findById(Long.parseLong(id));
		}
		catch (Exception e) {
			throw new insurancePolicyException("Something went wrong while fetching insurance policy");
		}
		
		if(insurancepolicy.isEmpty()){
            throw new insurancePolicyException("insurance policy Not Found");
        }
		return insurancepolicy.get();
	}

	public void registerInsurancePolicy(InsurancePolicy insurancepolicy) {
		try {
			insurancepolicyrepository.save(insurancepolicy);
		}
		catch (IllegalArgumentException e) {
			throw new insurancePolicyException("Fields should not be empty.");
		}
	}

	public void updatePolicy(String id, InsurancePolicy insurancepolicy) {
		try {
			InsurancePolicy policy_status = insurancepolicyrepository.getReferenceById(Long.parseLong(id));
			policy_status.setPolicyType(insurancepolicy.getPolicyType());
			policy_status.setCoverageAmount(insurancepolicy.getCoverageAmount());
			policy_status.setPremium(insurancepolicy.getPremium());
			policy_status.setStartDate(insurancepolicy.getStartDate());
			policy_status.setEndDate(insurancepolicy.getEndDate());
			insurancepolicyrepository.save(policy_status);
		}
		catch (EntityNotFoundException e) {
			throw new clientException("Policy Not Found");
		}
		catch (IllegalArgumentException e) {
			throw new clientException("Fields should not be empty.");
		}
	}

	public void deletePolicy(String id) {
		try {
			insurancepolicyrepository.deleteById(Long.parseLong(id));
		}
		catch (Exception e) {
			throw new clientException("policy does not found.");
		}
	}

}
