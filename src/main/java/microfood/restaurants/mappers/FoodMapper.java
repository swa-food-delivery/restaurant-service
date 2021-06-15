package microfood.restaurants.mappers;

import microfood.restaurants.dto.FoodDTO;
import microfood.restaurants.dto.RestaurantDTO;
import microfood.restaurants.entity.Food;
import microfood.restaurants.entity.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FoodMapper {

    public final ModelMapper mapper;
    //private final RestaurantMapper rm;

    @Autowired
    public FoodMapper() {
        this.mapper = new ModelMapper();
        //this.rm=rm;
        mapper.createTypeMap(Food.class, FoodDTO.class).addMapping(Food::getName, FoodDTO::setName)
                .addMapping(src -> src.getRestaurant().getId(), FoodDTO::setResId);
        mapper.typeMap(FoodDTO.class, Food.class).addMappings(mp -> mp.skip(Food::setId));
    }

    public FoodDTO mapEntityToDto(Food food) {
        //return mapper.map(food, FoodDTO.class);
        FoodDTO fdo = mapper.map(food, FoodDTO.class);
        fdo.setResId(food.getRestaurant().getId());
        return fdo;
    }

    public Food mapDtoToEntity(FoodDTO food) {
        return mapper.map(food, Food.class);
/*        Food res = mapper.map(food, Food.class);
        res.setRestaurant(food.getResId());*/
    }

    public List<Food> mapDtosToEntitiesList(List<FoodDTO> foodDTOs) {
        return foodDTOs.stream().map(this::mapDtoToEntity).collect(Collectors.toList());
    }

    public List<FoodDTO> mapEntitiesToListDto(List<Food> foodlist) {
        return foodlist.stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }
}