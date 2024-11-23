package com.restaurant.listing.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.listing.entity.Restaurant;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, Integer>{

//	Restaurant saveRestaurant(Restaurant mapRestaurantDTOToRestaurant);
//
//	Restaurant addRestaurant(Restaurant mapRestaurantDTOToRestaurant);
	

}
