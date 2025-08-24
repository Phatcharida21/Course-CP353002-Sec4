package com.lab7sec4.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
 ;

@Entity
@Table(name="customer")
public class Customer {

	@Id
	@GeneratedValue (strategy= GenerationType.AUTO)
	private long id;
	@Column(name="name")
	private String name;
	@Column(name="email")
	private String email;
	public Customer() {
		
	}
	public Customer(String name, String email) {
		this.email=email;
		this.name= name;
	}
	public long getId() { return id;}
	@JsonProperty("customerName")
	public String getName() { return name;}
	@JsonProperty("email")
	public String getEmail() { return email;}
	public void setId(long id) { this.id=id;}
	@JsonProperty("customerName")
	public void setName(String name) { this.name=name;}
	@JsonProperty("email")
	public void setEmail(String email) { this.email=email;}
	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", email=" + email + "]";
	}
	
}
