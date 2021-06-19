package microfood.restaurants.client;

import microfood.restaurants.client.exception.RestaurantServiceUnavailableException;
import microfood.restaurants.dto.FoodDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component("RestaurantsClientFallback")
public class RestaurantsClientFallback implements RestaurantsClient {
    @Override
    public List<FoodDTO> getMenu(UUID resId) {
        throw new RestaurantServiceUnavailableException("Restaurants service is unavailable");
    }
}
