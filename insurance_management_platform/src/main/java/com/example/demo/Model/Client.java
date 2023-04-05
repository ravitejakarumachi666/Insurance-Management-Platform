package com.example.demo.Model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;




@Entity
@Table(name = "clientDb")

public class Client {
	
	@Id
    @GeneratedValue
    private Long id;
	private String name;
	private String dateOfBirth;
	private String address;
	private Long contact;
	@JsonIgnore
	@ManyToMany
	@JoinTable(
			  name = "client_insurancepolicy",
			  joinColumns = @JoinColumn(name = "client_id"),
			  inverseJoinColumns = @JoinColumn(name = "insurance_policy_id"))
	private List<InsurancePolicy> insurancePolicies;
	
	public List<InsurancePolicy> getList() {
		return insurancePolicies;
	}
	public void setList(List<InsurancePolicy> insurancePolicies) {
		this.insurancePolicies = insurancePolicies;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getContact() {
		return contact;
	}
	public void setContact(Long contact) {
		this.contact = contact;
	}
}
