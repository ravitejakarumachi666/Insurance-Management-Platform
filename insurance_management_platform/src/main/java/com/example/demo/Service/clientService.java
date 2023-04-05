package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Exception.clientExceptionHandler.clientException;
import com.example.demo.Model.Client;
import com.example.demo.Repository.clientRepository;


@Service
public class clientService {

	@Autowired
	clientRepository clientrepository;
	
	public List<Client> getClients() {
		
		List<Client> list = new ArrayList<>();
		try {
			list = clientrepository.findAll();
		}
		catch (Exception e) {
			throw new clientException("Something went wrong while fetching client list.");
		}
		if(list.isEmpty()) {
			throw new clientException("No Client found ,List is Empty.");
		}
		return list;
	}

	public Client getClientById(String id) {
		Optional<Client> client = null;
		try {
			client = clientrepository.findById(Long.parseLong(id));
		}
		catch (Exception e) {
			throw new clientException("Something went wrong while fetching client");
		}
		
		if(client.isEmpty()){
            throw new clientException("Client Not Found");
        }
		return client.get();
	}

	public void registerClient(Client client) {
		try {
			clientrepository.save(client);
		}
		catch (IllegalArgumentException e) {
			throw new clientException("Fields should not be empty.");
		}
	}

	public void updateclient(String id, Client client) {
		try {
			Client client_status = clientrepository.getReferenceById(Long.parseLong(id));
			client_status.setAddress(client.getAddress());
			client_status.setContact(client.getContact());
			client_status.setDateOfBirth(client.getDateOfBirth());
			client_status.setName(client.getName());
			clientrepository.save(client_status);
		}
		catch (EntityNotFoundException e) {
			throw new clientException("Client Not Found");
		}
		catch (IllegalArgumentException e) {
			throw new clientException("Fields should not be empty.");
		}
	}

	public void deleteClient(String id) {
		
		try {
			clientrepository.deleteById(Long.parseLong(id));
		}
		catch (Exception e) {
			throw new clientException("client does not found.");
		}
		
	}
	
}
