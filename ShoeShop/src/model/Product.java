package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String title;
    String author;
    String description;
    String manufacturer;
    String language;
    double price;
    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    Warehouse warehouse;
    @ManyToOne
    Cart cart;

    public Product(String title, String description, double price) {
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public Product(String title, String description, double price, String manufacturer) {
        this.title = title;
        this.description = description;
        this.manufacturer = manufacturer;
        this.price = price;
    }

    public Product(String title, String description, double price,  String manufacturer, Warehouse warehouse) {
        this.title = title;
        this.description = description;
        this.manufacturer = manufacturer;
        this.warehouse = warehouse;
        this.price = price;
    }

    @Override
    public String toString() {
        return author + "; " + title + "; price: " + price+ "€";
    }

}
