package com.bathanh.apibook.persistence.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    public static final String ID_FIELD = "id";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private boolean enabled;

    private String avatar;

    private UUID roleId;
}
