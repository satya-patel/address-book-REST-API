package com.AddressBook_RestAPI;

import java.util.ArrayList;
import java.util.List;

public class AddressBookPayrollService {

	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	}

	private List<AddressBookData> addressbookList;

	public AddressBookPayrollService(List<AddressBookData> addressbookList) {
		this.addressbookList = new ArrayList<>(addressbookList);
	}

	public AddressBookPayrollService() {
	}

	public long countEntries(IOService ioService) {
		return addressbookList.size();
	}
	
	public void addContactToPayroll(AddressBookData addressBookData, IOService ioService) {
		addressbookList.add(addressBookData);
	}
	
	public void updateContactCity(String firstName, String city, IOService ioService) {
		AddressBookData addressBookData = this.getAddressBookData(firstName);
		if (addressBookData != null)
			addressBookData.city = city;
	}

	public AddressBookData getAddressBookData(String firstName) {
		AddressBookData addressBookData;
		addressBookData = this.addressbookList.stream().filter(dataItem -> dataItem.firstName.equals(firstName))
				.findFirst().orElse(null);
		return addressBookData;
	}
	
	public void deleteContactPayroll(String firstName, IOService ioService) {
		AddressBookData addressBookData = this.getAddressBookData(firstName);
		addressbookList.remove(addressBookData);
	}
}