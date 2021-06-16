package microfood.restaurants.controller;

import microfood.restaurants.dto.FoodDTO;
import microfood.restaurants.dto.RestaurantDTO;
import microfood.restaurants.exceptions.NoRestaurantsException;
import microfood.restaurants.exceptions.RestaurantNotFoundException;
import microfood.restaurants.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService rs;

    @Autowired
    public RestaurantController(RestaurantService rs) {
        this.rs=rs;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantDTO createRestaurant(@RequestBody RestaurantDTO rdo) {
        return rs.createRestaurant(rdo);
    }

    @GetMapping("/{resId}")
    public RestaurantDTO getRestaurantById(@PathVariable UUID resId) throws RestaurantNotFoundException {
        return rs.getRestaurantById(resId);
    }

    @GetMapping("/address/{address}")
    public List<RestaurantDTO> getRestaurantsByAddress(@PathVariable String address) throws RestaurantNotFoundException {
        return rs.getRestaurantsByAddress(address);
    }

    @GetMapping("/{resId}/menu")
    public List<FoodDTO> getRestaurantMenu(@PathVariable UUID resId) throws RestaurantNotFoundException {
        return rs.getFoodByRestaurantId(resId);
    }

    @GetMapping("/name/{name}")
    public List<RestaurantDTO> getRestaurantsByName(@PathVariable String name) throws RestaurantNotFoundException {
        return rs.getRestaurantByName(name);
    }

    @GetMapping
    public List<RestaurantDTO> getAllRestaurants() throws NoRestaurantsException {
        return rs.getAllRestaurants();
    }

    @DeleteMapping("/{resId}")
    public void deleteRestaurant(@PathVariable UUID resId) throws RestaurantNotFoundException {
        rs.removeRestaurant(resId);
    }


}
