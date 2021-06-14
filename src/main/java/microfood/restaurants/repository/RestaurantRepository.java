package microfood.restaurants.repository;

import microfood.restaurants.entity.Food;
import microfood.restaurants.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

    Optional<Restaurant> getByResId(UUID id);

    List<Restaurant> getByName(String name);

    List<Restaurant> getByAddress(String address);

}