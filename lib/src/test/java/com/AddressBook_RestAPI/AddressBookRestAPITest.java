package com.AddressBook_RestAPI;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.AddressBook_RestAPI.AddressBookPayrollService.IOService;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;

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

	@Test
	public void givenAddressbookDataInJsonServer_WhenRetrieved_ShouldMatchCount() {
		AddressBookData[] arrayOfPerson = getAddressbookList();
		addressbookPayrollService = new AddressBookPayrollService(Arrays.asList(arrayOfPerson));
		long entries = addressbookPayrollService.countEntries(REST_IO);
		assertEquals(2, entries);
	}

}
