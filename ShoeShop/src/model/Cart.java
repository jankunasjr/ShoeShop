package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate dateCreated;
    private double cart_value;
    private boolean isCompleted;

    @ManyToOne // This annotation is for the many-to-one relationship with Customer
    @JoinColumn(name = "customer_id", nullable = false) // This is the foreign key column in the Cart table.
    private User user; // This field represents the customer associated with the cart

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Product> itemsInCart;

    public Cart(int id, LocalDate dateCreated, double cart_value, User user, boolean isCompleted){
        this.id = id;
        this.dateCreated = dateCreated;
        this.isCompleted = isCompleted;
        this.cart_value = cart_value;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Id: " + id +
                " " + dateCreated +
                " " + cart_value +
                " $ for: " + user.name;
    }
    public int getUserId() {
        return this.user.getId(); // for some reason doesnt work without this
    }

}
