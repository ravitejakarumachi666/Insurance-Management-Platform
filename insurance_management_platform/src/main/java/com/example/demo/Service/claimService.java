package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Exception.claimExceptionHandler.claimException;
import com.example.demo.Exception.clientExceptionHandler.clientException;
import com.example.demo.Model.Claim;
import com.example.demo.Model.Client;
import com.example.demo.Repository.claimRepository;

@Service
public class claimService {
	@Autowired
	claimRepository claimrepository;
	
	public List<Claim> getClaims() {
		List<Claim> list = new ArrayList<>();
		try {
			list = claimrepository.findAll();
		}
		catch (Exception e) {
			throw new claimException("Something went wrong while fetching claims.");
		}
		if(list.isEmpty()) {
			throw new clientException("No Claim found ,List is Empty.");
		}
		return list;
	}

	public Claim getClaimById(String id) {
		Optional<Claim> claim = null;
		try {
			claim = claimrepository.findById(Long.parseLong(id));
		}
		catch (Exception e) {
			throw new claimException("Something went wrong while fetching claim");
		}
		
		if(claim.isEmpty()){
            throw new clientException("Claim Not Found");
        }
		return claim.get();
	}

	public void registerClaim(Claim claim) {
		try {
			claimrepository.save(claim);
		}
		catch (IllegalArgumentException e) {
			throw new claimException("Fields should not be empty.");
		}
	}

	public void updateclaim(String id, Claim claim) {
		try {
			Claim claim_status = claimrepository.getReferenceById(Long.parseLong(id));
			claim_status.setDescription(claim.getDescription());
			claim_status.setClaimStatus(claim.getClaimStatus());
			claim_status.setClaimDate(claim.getClaimDate());
			claimrepository.save(claim_status);
		}
		catch (EntityNotFoundException e) {
			throw new claimException("Claim Not Found");
		}
		catch (IllegalArgumentException e) {
			throw new claimException("Fields should not be empty.");
		}
	}

	public void deleteClaim(String id) {
		try {
			claimrepository.deleteById(Long.parseLong(id));
		}
		catch (Exception e) {
			throw new claimException("claim does not found.");
		}
	}

}
