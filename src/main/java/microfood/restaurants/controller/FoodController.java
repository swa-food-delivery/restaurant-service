package microfood.restaurants.controller;

import microfood.restaurants.dto.FoodDTO;
import microfood.restaurants.dto.RestaurantDTO;
import microfood.restaurants.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
public class FoodController {

    private final FoodService fs;

    @Autowired
    public FoodController(FoodService fs) {
        this.fs=fs;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FoodDTO createRestaurant(@RequestBody FoodDTO fdo) {
        return fs.addMenuItem(fdo);
    }
}
