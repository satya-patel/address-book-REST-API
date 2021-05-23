package com.AddressBook_RestAPI;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.AddressBook_RestAPI.AddressBookPayrollService.IOService;
import com.EmployeePayroll_RESTAPI.EmployeePayrollData;
import com.EmployeePayroll_RESTAPI.EmployeePayrollService;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AddressBookRestAPITest {

	private static final IOService REST_IO = null;
	static AddressBookPayrollService addressbookPayrollService;

	@BeforeClass
	public static void createObj() {
		addressbookPayrollService = new AddressBookPayrollService();
	}

	@AfterClass
	public static void nullObj() {
		addressbookPayrollService = null;
	}

	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}

	private AddressBookData[] getAddressbookList() {
		Response response = RestAssured.get("/addressBook");
		System.out.println("Adddressbook entries in JsonServer :\n" + response.asString());
		AddressBookData[] arrayOfPerson = new Gson().fromJson(response.asString(), AddressBookData[].class);
		return arrayOfPerson;
	}
	
	private Response addContactToJsonServer(AddressBookData addressBookData) {
		String employeeJson = new Gson().toJson(addressBookData);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(employeeJson);
		return request.post("/addressbook");
	}

	@Test
	public void givenAddressbookDataInJsonServer_WhenRetrieved_ShouldMatchCount() {
		AddressBookData[] arrayOfPerson = getAddressbookList();
		addressbookPayrollService = new AddressBookPayrollService(Arrays.asList(arrayOfPerson));
		long entries = addressbookPayrollService.countEntries(REST_IO);
		assertEquals(4, entries);
	}

	@Test
	public void givenMultipleContact_WhenAdded_ShouldMatch201ResponseAndCount() {
		AddressBookData[] arrayOfPerson = getAddressbookList();
		addressbookPayrollService = new AddressBookPayrollService(Arrays.asList(arrayOfPerson));
		AddressBookData[] arrayOfPersonPayroll = {
				new AddressBookData(0, "Akash", "Gupta", "Jhajha","Jamui","Bihar", "123456", "9876543210", "akash@gmail.com"),
				new AddressBookData(0, "Raman", "Sharma", "Juhu","Mumbai","Maharashtra", "725666", "9876543245", "raman@gmail.com") };

		for (AddressBookData addressBookData : arrayOfPersonPayroll) {

			Response response = addContactToJsonServer(addressBookData);
			int statusCode = response.getStatusCode();
			assertEquals(201, statusCode);

			addressBookData = new Gson().fromJson(response.asString(), AddressBookData.class);
			addressbookPayrollService.addContactToPayroll(addressBookData, REST_IO);
		}
		long entries = addressbookPayrollService.countEntries(REST_IO);
		assertEquals(4, entries);
	}
}
