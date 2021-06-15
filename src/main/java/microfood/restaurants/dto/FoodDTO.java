package microfood.restaurants.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import microfood.restaurants.entity.Restaurant;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodDTO {
    private int id;

    private String name;

    private UUID resId;
}
