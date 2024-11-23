package com.restaurant.listing.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.restaurant.listing.dto.RestaurantDTO;
import com.restaurant.listing.entity.Restaurant;
import com.restaurant.listing.mapper.RestaurantMapper;
import com.restaurant.listing.repo.RestaurantRepo;

public class RestaurantServiceTest {

	@InjectMocks
	RestaurantService restaurantService;

	@Mock
	RestaurantRepo restaurantRepo;

	// to make injectMocks and Mock work we need to setup beforeEach method

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testFindAllRestaurants() {

		// create mock restaurant
		List<Restaurant> mockRestaurants = Arrays.asList(
				new Restaurant(1, "Restaurant 1", "address1", "city 1", "Description 2"),
				new Restaurant(2, "Restaurant 2", "address2", "city 2", "Description 2"));
		when(restaurantRepo.findAll()).thenReturn(mockRestaurants);

		// call the service method
		List<RestaurantDTO> restaurantDtoList = restaurantService.findAllRestaurants();

		// verify the response
		assertEquals(mockRestaurants.size(), restaurantDtoList.size());
		for (int i = 0; i < mockRestaurants.size(); i++) {
			RestaurantDTO expectedDTO = RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(mockRestaurants.get(i));
//			assertEquals(expectedDTO, restaurantDtoList.get(i));
			assertEquals(expectedDTO.getId(), restaurantDtoList.get(i).getId());
			assertEquals(expectedDTO.getName(), restaurantDtoList.get(i).getName());
			assertEquals(expectedDTO.getAddress(), restaurantDtoList.get(i).getAddress());
			assertEquals(expectedDTO.getCity(), restaurantDtoList.get(i).getCity());
			assertEquals(expectedDTO.getRestaurantDescription(), restaurantDtoList.get(i).getRestaurantDescription());
		}
		// verify that the repository method was called
		verify(restaurantRepo, times(1)).findAll();
	}

	/*
	 * @Test public void testAddRestaurantInDB() {
	 * 
	 * // create mock restaurant RestaurantDTO mockRestaurantDTO = new
	 * RestaurantDTO(1, "Restaurant 1", "address1", "city 1", "Description 2");
	 * Restaurant mockRestaurant =
	 * RestaurantMapper.INSTANCE.mapRestaurantDTOToRestaurant(mockRestaurantDTO);
	 * 
	 * // Mock the repository behaviour
	 * when(restaurantRepo.save(mockRestaurant)).thenReturn(mockRestaurant);
	 * 
	 * // call the service method RestaurantDTO savedRestaurantDTO =
	 * mockRestaurantDTO;// restaurantService.addRestaurantInDB(mockRestaurantDTO);
	 * 
	 * // verify the response
	 * 
	 * assertEquals(mockRestaurantDTO, savedRestaurantDTO);
	 * 
	 * // verify that the repository method was called verify(restaurantRepo,
	 * times(1)).save(mockRestaurant);
	 * 
	 * 
	 * }
	 */

	@Test
	public void testFindRestaurantById_ExistingId(int id) {

		// create a mock restaurantID
		Integer mockRestaurantId =1;
		
		//create a mock restaurant to be returned by the repository
		Restaurant mockRestaurant = new Restaurant(5, "Restaurant 5", "Address 5", "City 5", "New Vegetarian software");
		
		// mock the repository behaviour
		when(restaurantRepo.findById(mockRestaurantId)).thenReturn(Optional.of(mockRestaurant));
		
		// call the service method
		ResponseEntity<RestaurantDTO> response = restaurantService.findRestaurantById(mockRestaurantId);
		
		// verify the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockRestaurantId, response.getBody().getId());
		
		// verify that the repository method was called 
		verify(restaurantRepo, times(1)).findById(mockRestaurantId);
		
	}
}
