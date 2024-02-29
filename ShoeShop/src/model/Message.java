package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    public Message(String content, Cart cart) {
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", cartId=" + (cart != null ? cart.getId() : null) +
                '}';
    }

    public int getCartId() {
        return this.cart != null ? this.cart.getId() : null;
    }
}
