package org.game.admin.model;

import org.game.utils.EncryptionUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
public class User {


    @PrePersist
    private void encryptPass() {
        encryptPassword(password);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Email
    @Column(unique = true)
    private String email;

    private String password;

    @OneToOne(targetEntity = Account.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Account account;

    @Column(columnDefinition = "boolean default true")
    private boolean enabled;


    public User() {
    }

    public User(Long id, String username, String email, String password, Account account) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return decryptPassword(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private String encryptPassword(String password) {
        try {
            return EncryptionUtils.encrypt(password);
        } catch (Exception e) {
            throw new RuntimeException("Error al encriptar la contraseña", e);
        }
    }

    private String decryptPassword(String encryptedPassword) {
        try {
            return EncryptionUtils.decrypt(encryptedPassword);
        } catch (Exception e) {
            throw new RuntimeException("Error al desencriptar la contraseña", e);
        }
    }
}
