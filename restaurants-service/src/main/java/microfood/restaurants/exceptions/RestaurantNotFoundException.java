package microfood.restaurants.exceptions;

public class RestaurantNotFoundException extends RestaurantException {
    public RestaurantNotFoundException() {
        super();
    }

    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
