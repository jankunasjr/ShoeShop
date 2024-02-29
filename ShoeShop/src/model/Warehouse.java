package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Warehouse implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String address;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private List<Product> inStockProducts;
    public Warehouse(String title, String address) {
        this.title = title;
        this.address = address;
        this.inStockProducts = new ArrayList<>();
    }

    @Override
    public String toString() {
        return title;
    }
}
