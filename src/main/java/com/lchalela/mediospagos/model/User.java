package com.lchalela.mediospagos.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lchalela.mediospagos.dto.AccountDTO;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name= "email")
    private String email;
    
    @JsonIgnore
    @Column(name= "password")
    private String password;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "last_name")
    private String lastName;
    
    private Boolean enabled;
    
    @Transient
    private List<AccountDTO> accountDTO;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="users_roles" ,
    joinColumns = @JoinColumn(name="user_id"), 
    inverseJoinColumns = @JoinColumn(name="role_id"),
    uniqueConstraints = { @UniqueConstraint(columnNames = {"user_id"," role_id"}) })
    private List<Role> roles;
    
    @PrePersist
    public void initEnabled() {
    	this.enabled = true;
    }
}
