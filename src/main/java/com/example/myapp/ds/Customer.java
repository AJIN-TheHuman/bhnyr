package com.example.myapp.ds;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String name;
    private String password;
    @Embedded
    private Address address;
    @OneToMany
    private List<CustomerBookOrder> customerBookOrders=new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Roles> rolesSet=new ArrayList<>();

    @ManyToMany
    private List<Book>  books=new ArrayList<>();

    public void addBook(Book book){
        book.getCustomers().add(this);
        books.add(book);
    }
    public void addRole(Roles roles){
        roles.getCustomers().add(this);
        rolesSet.add(roles);
    }

    public void bookOrder(CustomerBookOrder bookOrder){
        bookOrder.setCustomer(this);
        customerBookOrders.add(bookOrder);
    }
}
