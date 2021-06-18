package microfood.restaurants;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import microfood.restaurants.controller.RestaurantController;
import microfood.restaurants.dto.FoodDTO;
import microfood.restaurants.dto.RestaurantDTO;
import microfood.restaurants.exceptions.RestaurantNotFoundException;
import microfood.restaurants.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.util.ArrayList;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext
@AutoConfigureMessageVerifier
public class OtherTestClass {

    @Autowired
    private RestaurantController evenOddController;

    @MockBean
    RestaurantService rs;

    @BeforeEach
    public void setup() throws RestaurantNotFoundException {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(evenOddController);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);

        Mockito.when(rs.getRestaurantById(UUID.fromString("96bf508e-245a-4423-8f6d-22f46472e02d"))).thenReturn(new RestaurantDTO(
                UUID.fromString("96bf508e-245a-4423-8f6d-22f46472e02d"), "test", "testAddr", new ArrayList<FoodDTO>()
        ));
    }
}