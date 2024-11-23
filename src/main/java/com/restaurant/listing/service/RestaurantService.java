package com.restaurant.listing.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restaurant.listing.dto.RestaurantDTO;
import com.restaurant.listing.entity.Restaurant;
import com.restaurant.listing.mapper.RestaurantMapper;
import com.restaurant.listing.repo.RestaurantRepo;

@Service
public class RestaurantService {

	@Autowired
	RestaurantRepo restaurantRepo;

	public List<RestaurantDTO> findAllRestaurants() {

		List<Restaurant> restaurants = restaurantRepo.findAll();
		List<RestaurantDTO> restaurantList = restaurants.stream()
				.map(restaurant -> RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(restaurant))
				.collect(Collectors.toList());

		return restaurantList;
	}

	public RestaurantDTO addRestaurantInDB(RestaurantDTO restaurantDTO) {
		Restaurant restaurant = RestaurantMapper.INSTANCE.mapRestaurantDTOToRestaurant(restaurantDTO);
		Restaurant rasturantAdded = restaurantRepo.save(restaurant);
		RestaurantDTO response = RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(rasturantAdded);
		return response;
	}

	public ResponseEntity<RestaurantDTO> findRestaurantById(int id) {
		Optional<Restaurant> restaurant = restaurantRepo.findById(id);
		if (restaurant.isPresent()) {
			RestaurantDTO restaurantDetails = RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(restaurant.get());
			return new ResponseEntity<>(restaurantDetails, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
}
