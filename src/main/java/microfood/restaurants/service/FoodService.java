package microfood.restaurants.service;

import microfood.restaurants.dto.FoodDTO;
import microfood.restaurants.entity.Food;
import microfood.restaurants.exceptions.RestaurantNotFoundException;
import microfood.restaurants.mappers.FoodMapper;
import microfood.restaurants.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class FoodService {

    private final FoodRepository fr;
    private final FoodMapper fm;

    @Autowired
    public FoodService(FoodRepository fr, FoodMapper fm) {
        this.fr=fr;
        this.fm=fm;
    }

    public FoodDTO addMenuItem(FoodDTO food) {
        Food item = fm.mapDtoToEntity(food);
        return fm.mapEntityToDto(fr.save(item));
    }

    public List<FoodDTO> getFoodByRestaurantId(UUID resId) throws RestaurantNotFoundException {
        List<FoodDTO> menu = fm.mapEntitiesToListDto(fr.getAllByRestaurantId(resId));
        if (menu!=null) {
            return menu;
        } else throw new RestaurantNotFoundException();
    }

    public FoodDTO getFoodById(int id) {
        return fm.mapEntityToDto(fr.getById(id));
    }

    public List<FoodDTO> getAllFood() {
        return fm.mapEntitiesToListDto(fr.findAll());
    }

    public void removeFood(int id) {
        Food food = fr.getById(id);
        fr.delete(food);
    }


}
