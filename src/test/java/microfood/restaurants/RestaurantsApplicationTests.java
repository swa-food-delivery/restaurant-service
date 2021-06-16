package microfood.restaurants;

import com.google.common.io.Resources;
import microfood.restaurants.entity.Restaurant;
import microfood.restaurants.service.RestaurantService;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.StringBody.exact;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.mockserver.model.Header;
import org.mockserver.verify.VerificationTimes;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import javax.annotation.Resource;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.stop.Stop.stopQuietly;

@SpringBootTest
@Ignore
class RestaurantsApplicationTests {

/*    @Mock
    RestaurantService rs;

    public static MockWebServer mockBE;

    @BeforeAll
            static void setUp() throws IOException {
                mockBE = new MockWebServer();
                mockBE.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBE.shutdown();
    }

    @BeforeEach
            void initialize() {
        String url = String.format("http://localhost:%s", mockBE.getPort());
    }*/

    private static ClientAndServer proxy;
    private static ClientAndServer mockServer;
    @Resource
    private Environment environment;
    @Resource
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeClass
    public static void startProxy() {
        proxy = ClientAndServer.startClientAndServer(4444);
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", String.valueOf(proxy.getPort()));
        System.out.println("PORT OF PROXY IS " + proxy.getPort());
        mockServer = startClientAndServer(4444);
        System.setProperty("bookService.port", String.valueOf(mockServer.getPort()));
        System.out.println("PORT OF MOCKJ SERVER IS " + mockServer.getPort());
    }

    @AfterClass
    public static void stopProxy() {
        stopQuietly(mockServer);
        stopQuietly(proxy);
        System.clearProperty("http.proxyHost");
        System.clearProperty("http.proxyPort");
    }

    @Before
    public void setupFixture() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Before
    public void startMockServer() {
        mockServer.reset();
        proxy.reset();
    }

    public void testProxyTypeEnabled() {

    }

    @Test
    public void shouldKillMe() throws IOException {
        URL url = Resources.getResource("restaurants.json");
        String response = Resources.toString(url, StandardCharsets.UTF_8);
        new MockServerClient("localhost", 4444)  .when(    request()
                .withMethod("GET")
                .withPath("/restaurants")).
                respond(
                        response().withStatusCode(200)
                                .withBody(response));

        new MockServerClient("localhost", 4444)
                .verify(
                        request()
                        .withMethod("GET")
                        .withPath("/restaurants"), VerificationTimes.exactly(1)
                );
    }

    @Test
    public void shouldLoadRestaurantWithMenu() throws Exception {
        testProxyTypeEnabled();
        URL url = Resources.getResource("restaurants.json");
        String response = Resources.toString(url, StandardCharsets.UTF_8);
        proxy
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/restaurants")
                                //.withHeader("\"Content-type\", \"application/json\"")
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                )
                                .withBody(response)
                );
        MvcResult result = mockMvc.perform(get("/restaurants").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        proxy.verify(
                request()
                .withPath("/restaurants"), VerificationTimes.exactly(1)
        );
        String body = result.getResponse().getContentAsString();
        Assert.assertEquals("test", body);
    }

//	@Test
	void contextLoads() {
	}

}
