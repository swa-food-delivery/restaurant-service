package microfood.restaurants.service;

import microfood.restaurants.dto.FoodDTO;
import microfood.restaurants.entity.Food;
import microfood.restaurants.mappers.FoodMapper;
import microfood.restaurants.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


}
