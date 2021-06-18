package microfood.restaurants.exceptions;

public class RestaurantException extends Exception {

    public RestaurantException() {

    }

    public RestaurantException(String message) {
        super(message);
    }

    public RestaurantException(String message, final Throwable cause) {
        super(message, cause);
    }

    public RestaurantException(final Throwable cause) {
        super(cause);
    }
}
