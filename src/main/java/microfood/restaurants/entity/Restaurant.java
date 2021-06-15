package microfood.restaurants.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import microfood.restaurants.dto.RestaurantDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="restaurants")
@NoArgsConstructor
public class Restaurant implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "rest_id")
    private UUID id;

    private String name;

    private String address;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Food> menu;

    public Restaurant(String name, String address, List<Food> menu) {
        this.name=name;
        this.address=address;
        this.menu=menu;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Food> getMenu() {
        return menu;
    }

    public void setMenu(List<Food> menu) {
        this.menu = menu;
    }
}
