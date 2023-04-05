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

import com.example.demo.Exception.claimExceptionHandler.claimException;

import com.example.demo.Model.Claim;

import com.example.demo.Service.claimService;


@RestController
public class claimController {
	@Autowired
	claimService claimservice;
	
	
	
	@GetMapping("/api/claims")
	public ResponseEntity<?> getAllClaims() {
		
		List<Claim> list = new ArrayList<>();
		
		try {
			list = claimservice.getClaims();
		}
		catch (claimException e) {
			return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping("/api/claims/{id}")
	public ResponseEntity<?> getClaim(@PathVariable String id) {
		
		if(id == null || id.isEmpty()) {
			claimException cException = new claimException("Invalid Request");
			return new ResponseEntity<>(cException,HttpStatus.BAD_REQUEST);
		}
		Claim claim = null;
		try {
			claim = claimservice.getClaimById(id);
		}
		catch (claimException e) {
			return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(claim,HttpStatus.OK);
	}
	@PostMapping("/api/claims")
	public ResponseEntity<?> createClaim(@RequestBody Claim claim) {
		if(!checkValid(claim)) {
			claimException cException = new claimException("Fields should not be empty.");
			return new ResponseEntity<>(cException,HttpStatus.BAD_REQUEST);
		}
		try {
			claimservice.registerClaim(claim);
		}
		catch (claimException  e) {
			return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Successfully Registered.",HttpStatus.OK);
	}
	@PutMapping("/api/claims/{id}")
	public ResponseEntity<?> updateClaim(@PathVariable String id,@RequestBody Claim claim) {
		if(!checkValid(claim)) {
			claimException cException = new claimException("Fields should not be empty.");
			return new ResponseEntity<>(cException,HttpStatus.BAD_REQUEST);
		}
		try {
			claimservice.updateclaim(id,claim);
		}
		catch (claimException  e) {
			return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Updated Successfully.",HttpStatus.OK);
	}
	@DeleteMapping("/api/claims/{id}")
	public ResponseEntity<?> deleteClaim(@PathVariable String id) {
		
		if(id == null || id.isEmpty()) {
			claimException cException = new claimException("Invalid Request");
			return new ResponseEntity<>(cException,HttpStatus.BAD_REQUEST);
		}
		try {
			claimservice.deleteClaim(id);
		}
		catch (claimException e) {
			return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("deleted successfully",HttpStatus.OK);
	}
	
	public boolean checkValid(Claim claim) {
		if(claim == null || claim.getClaimDate() == null || claim.getClaimStatus() == null || claim.getDescription() == null) {
			return false;
		}
		
		return true;
	}
}
