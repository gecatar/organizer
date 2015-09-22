package com.organizer.beans;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.organizer.model.Record;

@Named
@RequestScoped
public class SearchBean implements Serializable {

	private long registerSize = 0;

	private List<Record> records = Collections.emptyList();

	private String searchArg;

	private String firstName;

	private String lastName;

	private String address;

	private String phoneNumber;

	private boolean orderByFirstName;

	private boolean orderByLastName;

	@Inject
	private RecordsService recordsService;

	public void advancedSearch() {
		Map<String, String> searchFields = new HashMap<String, String>();
		Map<String, Boolean> orderBy = new HashMap<String, Boolean>();
		if (firstName.length() > 0) {
			searchFields.put("firstName", firstName);
		}
		if (lastName.length() > 0) {
			searchFields.put("lastName", lastName);
		}
		if (address.length() > 0) {
			searchFields.put("address", address);
		}
		if (orderByFirstName) {
			orderBy.put("firstName", true);
		}
		if (orderByLastName) {
			orderBy.put("lastName", true);
		}
		records = recordsService.searchByCriteria(searchFields, orderBy);
		registerSize = records.size();
	}

	public String getAddress() {
		return address;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public List<Record> getRecords() {
		return records;
	}

	public String getSearchArg() {
		return searchArg;
	}

	public long getSize() {
		return registerSize;
	}

	public boolean isOrderByFirstName() {
		return orderByFirstName;
	}

	public boolean isOrderByLastName() {
		return orderByLastName;
	}

	public void search() throws Throwable {
		records = recordsService.findByName(searchArg);
		registerSize = records.size();
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setOrderByFirstName(boolean orderByFirstName) {
		this.orderByFirstName = orderByFirstName;
	}

	public void setOrderByLastName(boolean orderByLastName) {
		this.orderByLastName = orderByLastName;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setSearchArg(String searchArg) {
		this.searchArg = searchArg;
	}
}
