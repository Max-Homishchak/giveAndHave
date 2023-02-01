package com.khomishchak.giveAndHave.model;

import com.khomishchak.giveAndHave.dto.UserDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password;
    private String email;

    @Column(name = "group")
    private String groupName;

    private int age;
    private int balance;

    private boolean isVerified;

    @ManyToMany
    @JoinTable(
            name = "transaction_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id"))
    private Set<Transaction> transactions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Task> tasks;

    public UserDto toDto () {
        return UserDto.builder()
                .name(name)
                .password(password)
                .email(email)
                .groupName(groupName)
                .age(age)
                .balance(balance)
                .isVerified(isVerified)
                .transactions(transactions)
                .tasks(tasks)
                .build();
    }
}