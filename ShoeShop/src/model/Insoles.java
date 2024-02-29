package model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Insoles extends Product {
    private int id;
    private String size;
    private String material;
    private String features;

    public Insoles(String title, String description, String manufacturer, double price, Warehouse warehouse, String size, String material, String features) {
        super(title, description, price, manufacturer, warehouse);
        this.size = size;
        this.material = material;
        this.features = features;
    }

    @Override
    public String toString() {
        return "Insoles{" +
                "id=" + id +
                "title='" + title + '\'' +
                ", size='" + size + '\'' +
                ", material='" + material + '\'' +
                ", features='" + features + '\'' +
                ", description='" + description + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", price='" + price + '\'' +
                ", warehouse=" + warehouse +
                '}';
    }
}
