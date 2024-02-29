package model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Laces extends Product {
    private String size;
    private String color;

    public Laces(String title, String description, double price, String manufacturer, Warehouse warehouse, String size, String color) {
        super(title, description, price, manufacturer, warehouse);
        this.size = size;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Laces{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", color='" + color + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", price='" + price + '\'' +
                ", warehouse=" + warehouse +
                '}';
    }
}
