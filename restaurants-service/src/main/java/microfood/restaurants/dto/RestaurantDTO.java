package microfood.restaurants.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO implements Serializable {

    private UUID id;

    private String name;

    private String address;

    private List<FoodDTO> menu;
}
