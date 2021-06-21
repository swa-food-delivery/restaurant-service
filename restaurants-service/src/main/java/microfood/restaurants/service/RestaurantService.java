package microfood.restaurants.service;

import lombok.extern.slf4j.Slf4j;
import microfood.restaurants.dto.FoodDTO;
import microfood.restaurants.dto.RestaurantDTO;
import microfood.restaurants.entity.Restaurant;
import microfood.restaurants.exceptions.NoRestaurantsException;
import microfood.restaurants.exceptions.RestaurantNotFoundException;
import microfood.restaurants.mappers.FoodMapper;
import microfood.restaurants.mappers.RestaurantMapper;
import microfood.restaurants.repository.FoodRepository;
import microfood.restaurants.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class RestaurantService {

    private final RestaurantRepository rr;
    private final RestaurantMapper rm;
    private final FoodMapper fm;
    private final FoodRepository fr;

    @Autowired
    public RestaurantService(RestaurantRepository rr, RestaurantMapper rm, FoodMapper fm, FoodRepository fr) {
        this.rr=rr;
        this.rm=rm;
        this.fm=fm;
        this.fr=fr;
    }

    public RestaurantDTO createRestaurant(RestaurantDTO rdo) {
        log.info("CREATING RESTAURANT WITH NAME: " + rdo.getName());
        Restaurant res = rm.mapDtoToEntity(rdo);
        Restaurant create = rr.save(res);
        return rm.mapEntityToDto(create);
    }

    public RestaurantDTO getRestaurantById(UUID resid) throws RestaurantNotFoundException {
        Restaurant res = rr.getById(resid).orElseThrow(RestaurantNotFoundException::new);
        log.info("SUCCESSFULLY FOUND A RESTAURANT WITH AN ID OF: " + resid);
        return rm.mapEntityToDto(res);
    }

    public List<RestaurantDTO> getRestaurantByName(String name) throws RestaurantNotFoundException {
        List<RestaurantDTO> res = rm.mapEntitiesToDtoList(rr.getByName(name));
        if (res!=null) {
            log.info("FOUND " + res.size() + " RESTAURANTS MATCHING THE NAME: " + name);
            return res;
        } else {
            log.info("NO RESTAURANTS WERE FOUND MATCHING NAME: " + name);
            throw new RestaurantNotFoundException();
        }
    }

    public List<RestaurantDTO> getRestaurantsByAddress(String address) throws RestaurantNotFoundException {
        List<RestaurantDTO> res = rm.mapEntitiesToDtoList(rr.getByAddress(address));
        if (res!=null) {
            log.info("FOUND " + res.size() + " RESTAURANTS MATCHING THE ADDRESS: " + address);
            return res;
        } else {
            log.info("NO RESTAURANTS FOUND MATCHING ADDRESS: " + address);
            throw new RestaurantNotFoundException();
        }
    }

    public void removeRestaurant(UUID resId) throws RestaurantNotFoundException {
        Restaurant res = rr.getById(resId).orElseThrow(RestaurantNotFoundException::new);
        rr.delete(res);
        log.info("DELETED RESTAURANT WITH ID: " + res.getId());
    }

    public List<RestaurantDTO> getAllRestaurants() throws NoRestaurantsException {
        List<RestaurantDTO> res = rm.mapEntitiesToDtoList(rr.findAll());
        if (res!=null) {
            log.info("FOUND " + res.size() + " RESTAURANTS REGISTERED");
            return res;
        } else {
            log.debug("A CALL WAS MADE TO GET ALL RESTAURANTS, BUT THE RESULT COULD NOT BE RETRIEVED");
            throw new NoRestaurantsException();
        }
    }

    public List<FoodDTO> getMenu(UUID resId) throws RestaurantNotFoundException {
        rr.getById(resId).orElseThrow(RestaurantNotFoundException::new);
        log.info("RETURNING MENU OF A RESTAURANT WITH AN ID: " + resId);
        return fm.mapEntitiesToListDto(fr.getAllByRestaurantId(resId));
    }
}
