package microfood.restaurants.mappers;

import microfood.restaurants.dto.FoodDTO;
import microfood.restaurants.entity.Food;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FoodMapper {

    public final ModelMapper mapper;

    public FoodMapper() {
        this.mapper = new ModelMapper();
        mapper.typeMap(FoodDTO.class, Food.class).addMappings(mp -> mp.skip(Food::setId));
    }

    public FoodDTO mapEntityToDto(Food orderItem) {
        return mapper.map(orderItem, FoodDTO.class);

    }

    public Food mapDtoToEntity(FoodDTO orderItemDTO) {
        return mapper.map(orderItemDTO, Food.class);

    }

    public List<Food> mapDtosToEntitiesList(List<FoodDTO> orderItemDTOS) {
        return orderItemDTOS.stream().map(this::mapDtoToEntity).collect(Collectors.toList());
    }

    public List<FoodDTO> mapEntitiesToListDto(List<Food> orderItems) {
        return orderItems.stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }
}