package com.example.demo.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Administrator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Email
    @Size(min = 5, max = 255)
    private String email;

    @Size(min = 1, max = 255)
    private String password;

    @NotBlank
    @Size(min = 1, max = 50)
    private String lastName;

    @NotBlank
    @Size(min = 1, max = 50)
    private String firstName;

    @NotBlank
    @Pattern(regexp = "^[0-9]+$")
    @Size(min = 10, max = 15)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
      name = "administrator_roles",
      joinColumns = @JoinColumn(name = "administrator_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
      name = "administrator_permissions",
      joinColumns = @JoinColumn(name = "administrator_id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions;
}
