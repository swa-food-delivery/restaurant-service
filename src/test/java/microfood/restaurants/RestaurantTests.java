package microfood.restaurants;

import com.google.common.io.Resources;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestaurantTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mock;

    String body;

    @BeforeEach
    public void setup() throws Exception {

        URL url = Resources.getResource("request.json");
        body = Resources.toString(url, StandardCharsets.UTF_8);
        this.mock = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @Order(1)
    public void restaurantsAreInitiallyEmpty() throws Exception {
        MvcResult res = this.mock.perform(get("/restaurants")).andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = res.getResponse().getContentAsString();
        Assert.assertEquals("[]", content);
    }

    @Test
    public void canAddAndSeeNewRestaurant() throws Exception {
        MvcResult res = this.mock.perform(get("/restaurants")).andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = res.getResponse().getContentAsString();
        Assert.assertFalse(content.contains("\"name\":\"testName\""));

        URL url = Resources.getResource("request.json");
        String body = Resources.toString(url, StandardCharsets.UTF_8);

        this.mock.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(jsonPath("$.address").value("Test street"));

        res = this.mock.perform(get("/restaurants")).andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        content = res.getResponse().getContentAsString();
        System.out.println(content);
        Assert.assertTrue(content.contains("\"name\":\"testName\""));
    }

    @Test
    public void canAccessRestaurantsMenu() throws Exception {
        this.mock.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated());

        MvcResult res = this.mock.perform(get("/restaurants")).andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = res.getResponse().getContentAsString();
        JSONArray o = new JSONArray(content);
        String resId = o.getJSONObject(0).getString("id");

        this.mock.perform(get("/restaurants/"+resId+"/menu")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..name",hasSize(2)))
                .andExpect(jsonPath("$[0]name").value("eda"))
                .andExpect(jsonPath("$[1]name").value("test food"))
                .andReturn();

    }
}
