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

}