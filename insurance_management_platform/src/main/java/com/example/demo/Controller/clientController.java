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

import com.example.demo.Exception.clientExceptionHandler.clientException;
import com.example.demo.Model.Client;
import com.example.demo.Service.clientService;


@RestController
public class clientController {
	
	@Autowired
	clientService clientservice;
	
	
	
	@GetMapping("/api/clients")
	public ResponseEntity<?> getAllClients() {
		
		List<Client> list = new ArrayList<>();
		
		try {
			list = clientservice.getClients();
		}
		catch (clientException e) {
			return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping("/api/clients/{id}")
	public ResponseEntity<?> getClient(@PathVariable String id) {
		
		if(id == null || id.isEmpty()) {
			clientException cException = new clientException("Invalid Request");
			return new ResponseEntity<>(cException,HttpStatus.BAD_REQUEST);
		}
		Client client = null;
		try {
			client = clientservice.getClientById(id);
		}
		catch (clientException e) {
			return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(client,HttpStatus.OK);
	}
	@PostMapping("/api/clients")
	public ResponseEntity<?> createClient(@RequestBody Client client) {
		if(!checkValid(client)) {
			clientException cException = new clientException("Fields should not be empty.");
			return new ResponseEntity<>(cException,HttpStatus.BAD_REQUEST);
		}
		try {
			clientservice.registerClient(client);
		}
		catch (clientException e) {
			return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Successfully Registered.",HttpStatus.OK);
	}
	@PutMapping("/api/clients/{id}")
	public ResponseEntity<?> updateClient(@PathVariable String id,@RequestBody Client client) {
		if(!checkValid(client)) {
			clientException cException = new clientException("Fields should not be empty.");
			return new ResponseEntity<>(cException,HttpStatus.BAD_REQUEST);
		}
		try {
			clientservice.updateclient(id,client);
		}
		catch (clientException e) {
			return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Updated Successfully.",HttpStatus.OK);
	}
	@DeleteMapping("/api/clients/{id}")
	public ResponseEntity<?> deleteClient(@PathVariable String id) {
		
		if(id == null || id.isEmpty()) {
			clientException cException = new clientException("Invalid Request");
			return new ResponseEntity<>(cException,HttpStatus.BAD_REQUEST);
		}
		try {
			clientservice.deleteClient(id);
		}
		catch (clientException e) {
			return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("deleted successfully",HttpStatus.OK);
	}
	
	public boolean checkValid(Client client) {
		if(client == null || client.getName()== null || client.getDateOfBirth() == null || client.getAddress() == null || client.getContact() == null ) {
			return false;
		}
		
		return true;
	}
}
