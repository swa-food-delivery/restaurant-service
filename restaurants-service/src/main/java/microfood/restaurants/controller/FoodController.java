package microfood.restaurants.controller;

import microfood.restaurants.dto.FoodDTO;
import microfood.restaurants.dto.RestaurantDTO;
import microfood.restaurants.exceptions.RestaurantNotFoundException;
import microfood.restaurants.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public FoodDTO createFood(@RequestBody FoodDTO fdo) {
        return fs.addMenuItem(fdo);
    }

    @GetMapping("/{foodId}")
    public FoodDTO getFoodById(@PathVariable int foodId) {
        return fs.getFoodById(foodId);
    }

    @DeleteMapping("/{foodId}")
    public void removeFood(@PathVariable int foodId) {
        fs.removeFood(foodId);
    }

    @GetMapping
    public List<FoodDTO> getAllFood() {
        return fs.getAllFood();
    }
}
