package org.binaracademy.challenge_4.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private LocalDateTime time;
    private String destination;
    @Column(name = "completed")
    private Boolean isCompleted;
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private OrderDetail orderDetails;

    public Order(User user, String destination, Boolean isCompleted){
        this.user = user;
        this.destination = destination;
        this.isCompleted = isCompleted;
    }
}
