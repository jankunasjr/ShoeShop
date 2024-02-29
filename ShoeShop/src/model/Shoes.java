package model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Shoes extends Product {

    private String size;
    private Productsex sex;
    private String color;
    private ProductSType stype;

    public Shoes(String title, String description, double price, String manufacturer, Warehouse warehouse, String size, Productsex sex, String color, ProductSType stype) {
        super(title, description, price, manufacturer, warehouse);
        this.size = size;
        this.sex = sex;
        this.color = color;
        this.stype = stype;
    }

    @Override
    public String toString() {
        return "Shoes{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", size='" + size + '\'' +
                ", sex=" + sex +
                ", color='" + color + '\'' +
                ", stype='" + stype + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                ", warehouse=" + warehouse +
                '}';
    }
}
