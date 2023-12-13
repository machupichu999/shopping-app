package org.shopping.server.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Role role;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    List<CustomerOrder> orders;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    List<CartItem> cartItems;

    enum Role {
        ADMIN,
        CUSTOMER
    }
}
