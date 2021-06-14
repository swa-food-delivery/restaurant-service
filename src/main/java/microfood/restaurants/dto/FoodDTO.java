package microfood.restaurants.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import microfood.restaurants.entity.Restaurant;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodDTO {
    private int id;

    private String name;

    private Restaurant restaurant;
}
