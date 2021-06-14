package microfood.restaurants.repository;

import microfood.restaurants.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FoodRepository extends JpaRepository<Food, UUID> {

    List<Food> getAllByRestaurantId(UUID id);

    List<Food> getAllByRestaurantName(String name);
}
