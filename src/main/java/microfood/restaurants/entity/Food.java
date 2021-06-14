package microfood.restaurants.entity;

import javax.persistence.*;
import java.lang.annotation.Target;

@Entity
@Table(name="food")
public class Food {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rest_id", referencedColumnName = "rest_id")
    private Restaurant restaurant;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
