package com.qa.ims.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.persistence.domain.Customer;

import Services.CustomerServices;

import com.qa.ims.persistence.*;
import com.qa.ims.persistence.domain.*;
import com.qa.ims.controller.*;
import com.qa.ims.controllers.*;
import com.qa.ims.exceptions.*;
import com.qa.ims.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {
	
	
	private CustomerServices customerServices;
	
	@Spy
	@InjectMocks
	private CustomerController customerController;

	@Test
	public void readAllTest() {
		CustomerController customerController = new CustomerController(customerServices);
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer("Chris", "P"));
		customers.add(new Customer("Rhys", "T"));
		customers.add(new Customer("Nic", "J"));
		Mockito.when(customerServices.readAll()).thenReturn(customers);
		assertEquals(customers, customerController.readAll());
	}

	@Test
	public void createTest() {
		String id = "1";
		String firstName = "Chris";
		String surname = "Perrins";
		String address = "address";
		Mockito.doReturn(id, firstName, surname, address).when(customerController).getInput();
		Customer customer = new Customer(firstName, surname);
		Customer savedCustomer = new Customer(1L, "Chris", "Perrins");
		Mockito.when(customerServices.create(customer)).thenReturn(savedCustomer);
		assertEquals(savedCustomer, customerController.create());
	}

	/**
	 * 
	 */
	@Test
	public void updateTest() {
		String id = "1";
		String firstName = "Rhys";
		String surname = "Thompson";
		String address = "address";
		Mockito.doReturn(id, firstName, surname, address).when(customerController).getInput();
		Customer customer = new Customer(1L, firstName, surname);
		Mockito.when(customerServices.update(customer)).thenReturn(customer);
		assertEquals(customer, customerController.update());
	}
	

	/**
	 * Delete doesn't return anything, so we can just verify that it calls the delete method
	 */
	@Test
	public void deleteTest() {
		String id = "1";
		Mockito.doReturn(id).when(customerController).getInput();
		customerController.delete();
		Mockito.verify(customerServices, Mockito.times(1)).delete(1L);
	}
	
}