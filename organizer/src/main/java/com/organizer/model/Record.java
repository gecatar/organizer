package com.organizer.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name = "All users names", query = "SELECT r.firstName FROM Record as r"),
	@NamedQuery(name = "User with name", query = "SELECT r FROM Record as r WHERE r.firstName LIKE :firstName OR r.lastName LIKE :firstName ORDER BY r.firstName ASC") })
public class Record implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String firstName;

	private String lastName;

	private String address;

	private String phoneNumber;

	private Date date;

	@Lob
	private byte[] photo;

	public Record() {

	}

	public String getAddress() {
		return address;
	}

	public Date getDate() {
		return date;
	}

	public String getFirstName() {
		return firstName;
	}

	public Long getId() {
		return id;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public Record setAddress(String address) {
		this.address = address;
		return this;
	}

	public Record setDate(Date date) {
		this.date = date;
		return this;
	}

	public Record setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public Record setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public Record setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

}