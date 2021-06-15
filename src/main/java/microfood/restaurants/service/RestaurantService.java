package microfood.restaurants.service;

import microfood.restaurants.dto.FoodDTO;
import microfood.restaurants.dto.RestaurantDTO;
import microfood.restaurants.entity.Restaurant;
import microfood.restaurants.exceptions.NoRestaurantsException;
import microfood.restaurants.exceptions.RestaurantNotFoundException;
import microfood.restaurants.mappers.RestaurantMapper;
import microfood.restaurants.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class RestaurantService {

    private final RestaurantRepository rr;
    private final RestaurantMapper rm;

    @Autowired
    public RestaurantService(RestaurantRepository rr, RestaurantMapper rm) {
        this.rr=rr;
        this.rm=rm;
    }

    public RestaurantDTO createRestaurant(RestaurantDTO rdo) {
        Restaurant res = rm.mapDtoToEntity(rdo);
        Restaurant create = rr.save(res);
        return rm.mapEntityToDto(create);
    }

    public RestaurantDTO getRestaurantById(UUID resid) throws RestaurantNotFoundException {
        Restaurant res = rr.getById(resid).orElseThrow(RestaurantNotFoundException::new);
        return rm.mapEntityToDto(res);
    }

    public List<RestaurantDTO> getRestaurantByName(String name) throws RestaurantNotFoundException {
        List<RestaurantDTO> res = rm.mapEntitiesToDtoList(rr.getByName(name));
        if (res!=null) {
            return res;
        } else throw new RestaurantNotFoundException();
    }

    public List<RestaurantDTO> getRestaurantsByAddress(String address) throws RestaurantNotFoundException {
        List<RestaurantDTO> res = rm.mapEntitiesToDtoList(rr.getByAddress(address));
        if (res!=null) {
            return res;
        } else throw new RestaurantNotFoundException();
    }

    public void removeRestaurant(UUID resId) throws RestaurantNotFoundException {
        Restaurant res = rr.getById(resId).orElseThrow(RestaurantNotFoundException::new);
        rr.delete(res);
    }

    public List<RestaurantDTO> getAllRestaurants() throws NoRestaurantsException {
        List<RestaurantDTO> res = rm.mapEntitiesToDtoList(rr.findAll());
        if (res!=null) return res;
        else throw new NoRestaurantsException();
    }

/*    public List<FoodDTO> getFoodByRestaurantId(UUID resId) throws RestaurantNotFoundException {
        Restaurant res = rr.getByResId(resId).orElseThrow(RestaurantNotFoundException::new);
        return fm.mapEntitiesToListDto(fr.getAllByRestaurantId(resId));
    }*/

}
