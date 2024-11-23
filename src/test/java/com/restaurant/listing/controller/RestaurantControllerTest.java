package com.restaurant.listing.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.restaurant.listing.dto.RestaurantDTO;
import com.restaurant.listing.service.RestaurantService;

public class RestaurantControllerTest {

	@InjectMocks
	RestaurantController restaurantController;

	@Mock
	RestaurantService restaurantService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this); // in order for Mock and InjectMocks annotations to take effect
	}

	@Test
	public void testFetchAllRestaurants() {

//		1. Create the test data 
		// Mock the service behaviour
		List<RestaurantDTO> mockRestaurants = Arrays.asList(
				new RestaurantDTO(1, "Restaurant 1", "address1", "city 1", "Description 2"),
				new RestaurantDTO(2, "Restaurant 2", "address2", "city 2", "Description 2"));
		when(restaurantService.findAllRestaurants()).thenReturn(mockRestaurants);

//		2. Call the test
		// Call the controller method
		ResponseEntity<List<RestaurantDTO>> response = restaurantController.fetchAllRestaurants();

//		3. verify the test
		// verify the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockRestaurants, response.getBody());

		// verify that the service method was called
		verify(restaurantService, times(1)).findAllRestaurants();
	}

/*	@Test
	public void testAddRestaurant() {

		// create a mock restaurant to be saved
		RestaurantDTO mockRestaurant = new RestaurantDTO(1, "Restaurant 1", "address1", "city 1", "Description 2");

		// mock the service behavior
		when(restaurantService.addRestaurantInDB(mockRestaurant)).thenReturn(mockRestaurant);

		// call the constructor method
		ResponseEntity<RestaurantDTO> response = restaurantController.addRestaurant(mockRestaurant);

		// verify the response
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(mockRestaurant, response.getBody());

		// verify that the service method was called
		verify(restaurantService, times(1)).addRestaurantInDB(mockRestaurant);

	} */

	@Test
	public void testFindRestaurantById() {

		// create a mock restaurant data
		RestaurantDTO mockRestaurant = new RestaurantDTO(1, "Restaurant 1", "address1", "city 1", "Description 2");

		// mock the service behavior
		when(restaurantService.findRestaurantById(mockRestaurant.getId()))
				.thenReturn(new ResponseEntity<RestaurantDTO>(mockRestaurant, HttpStatus.OK));

		// call the controller method
		ResponseEntity<RestaurantDTO> response = restaurantController.findRestaurantById(mockRestaurant.getId());

		// verify the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockRestaurant, response.getBody());

		// verify that the service method was called
		verify(restaurantService, times(1)).findRestaurantById(mockRestaurant.getId());

	}

}
