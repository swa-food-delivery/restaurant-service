package microfood.restaurants.mappers;

import java.util.List;
import java.util.stream.Collectors;

import microfood.restaurants.dto.FoodDTO;
import microfood.restaurants.dto.RestaurantDTO;
import microfood.restaurants.entity.Food;
import microfood.restaurants.entity.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

    private final ModelMapper mapper;
    private final FoodMapper foodMapper;

    @Autowired
    public RestaurantMapper(FoodMapper orderItemMapper) {
        this.mapper = new ModelMapper();
        this.foodMapper = orderItemMapper;
        mapper.createTypeMap(Restaurant.class, RestaurantDTO.class).addMapping(Restaurant::getName, RestaurantDTO::setId);
        mapper.createTypeMap(RestaurantDTO.class, Restaurant.class).addMapping(RestaurantDTO::getId, Restaurant::setName)
                .addMappings(mp -> mp.using(ctx -> orderItemMapper.mapDtosToEntitiesList((List<FoodDTO>) ctx.getSource()))
                        .map(RestaurantDTO::getMenu, Restaurant::setMenu));
    }

    public Restaurant mapDtoToEntity(RestaurantDTO resDTO) {
        Restaurant restaurant = mapper.map(resDTO, Restaurant.class);
        restaurant.getMenu().forEach(food->food.setRestaurant(restaurant));
        return restaurant;
    }

    public RestaurantDTO mapEntityToDto(Restaurant order) {
        return mapper.map(order, RestaurantDTO.class);
    }

    public List<RestaurantDTO> mapEntitiesToDtoList(List<Restaurant> orders) {
        return orders.stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }
}