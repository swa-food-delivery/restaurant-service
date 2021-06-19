package microfood.restaurants.client.exception;

public class RestaurantServiceUnavailableException extends RuntimeException {
    public RestaurantServiceUnavailableException(String message) {
        super(message);
    }
}
