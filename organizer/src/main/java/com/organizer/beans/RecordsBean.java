package com.organizer.beans;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import com.organizer.model.Record;
import com.organizer.utils.TransferObject;

@Named
@RequestScoped
public class RecordsBean implements Serializable {
	private static final Logger LOGGER = Logger.getLogger(RecordsBean.class
			.getName());

	private static final int _MAX_FILE_SIZE = 2097152;

	private static final int bufferSize = 1000;

	private static final String pictureMimeType = "image/jpeg";

	private String firstName;

	private String lastName;

	private String address;

	private String phoneNumber;

	private String firstNameAlert;

	private String lastNameAlert;

	private String addressAlert;

	private String phoneNumberAlert;

	private String fileAlert;

	private Part file;

	private String operationResult = "";

	@Inject
	private RecordsService recordsService;

	private Date date;

	public String create() {
		Record record = new Record();
		if (verifyInputData()) {
			record.setFirstName(firstName).setLastName(lastName)
			.setPhoneNumber(phoneNumber).setAddress(address)
			.setDate(date);
			if (verifyFile()) {
				try (TransferObject transferObject = new TransferObject()) {
					InputStream inputStream = file.getInputStream();
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					transferObject.transferAllBytes(inputStream, outputStream,
							bufferSize);
					record.setPhoto(outputStream.toByteArray());
				} catch (IOException e) {
					LOGGER.log(Level.SEVERE, "Error while saving thumbnail", e);
				}
			}
			recordsService.saveRecord(record);
			return "index";
		}
		return "create";
	}

	public String getAddress() {
		return address;
	}

	public String getAddressAlert() {
		return addressAlert;
	}

	public Date getDate() {
		return date;
	}

	public Part getFile() {
		return file;
	}

	public String getFileAlert() {
		return fileAlert;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getFirstNameAlert() {
		return firstNameAlert;
	}

	public String getLastName() {
		return lastName;
	}

	public String getLastNameAlert() {
		return lastNameAlert;
	}

	public String getOperationResult() {
		return operationResult;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getPhoneNumberAlert() {
		return phoneNumberAlert;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setAddressAlert(String addressAlert) {
		this.addressAlert = addressAlert;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public void setFileAlert(String fileAlert) {
		this.fileAlert = fileAlert;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setFirstNameAlert(String firstNameAlert) {
		this.firstNameAlert = firstNameAlert;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setLastNameAlert(String lastNameAlert) {
		this.lastNameAlert = lastNameAlert;
	}

	public void setOperationResult(String operationResult) {
		this.operationResult = operationResult;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setPhoneNumberAlert(String phoneNumberAlert) {
		this.phoneNumberAlert = phoneNumberAlert;
	}

	private boolean verifyFile() {
		if (file != null) {
			if (file.getSize() < _MAX_FILE_SIZE
					&& file.getContentType().equals(pictureMimeType)) {
				return true;
			}
		}
		return false;
	}

	private boolean verifyInputData() {
		boolean correctInputData = true;
		if (firstName.length() < 1 || firstName.length() > 255) {
			firstNameAlert = "First name must be with lenght between 1 and 255!!!";
			correctInputData = false;
		}
		if (lastName.length() < 1 || lastName.length() > 255) {
			lastNameAlert = "Last name must be with lenght between 1 and 255!!!";
			correctInputData = false;
		}
		if (address.length() < 1 || lastName.length() > 255) {
			addressAlert = "Address must be with lenght between 1 and 255!!!";
			correctInputData = false;
		}
		if (phoneNumber.length() < 1 || lastName.length() > 255) {
			phoneNumberAlert = "Phone number must be with lenght between 1 and 255!!!";
			correctInputData = false;
		}
		return correctInputData;
	}

}
