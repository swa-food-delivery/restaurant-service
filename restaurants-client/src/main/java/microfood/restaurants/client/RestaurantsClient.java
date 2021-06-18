package microfood.restaurants.client;

import microfood.restaurants.dto.FoodDTO;
import microfood.restaurants.exceptions.RestaurantNotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@FeignClient(name="restaurants-service")
public interface RestaurantsClient {
    @RequestMapping(method = RequestMethod.GET, value = "/restaurants/{resId}/menu")
    List<FoodDTO> getMenu(@PathVariable UUID resId) throws RestaurantNotFoundException;
}