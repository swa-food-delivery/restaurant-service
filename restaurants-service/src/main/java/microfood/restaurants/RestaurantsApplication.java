package microfood.restaurants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
//@EnableJpaRepositories("microfood.restaurants.repository")
@EnableFeignClients(basePackages = "microfood.orders.client")
public class RestaurantsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantsApplication.class, args);
	}

}