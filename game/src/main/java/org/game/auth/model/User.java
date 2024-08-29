package org.game.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.game.base.model.MainBase;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "\"user\"")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonProperty("last_name")
    private String lastname;

    @Column(unique = true)
    private String username;

    @Email
    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    @JsonProperty("public_id")
    private UUID publicId;

    private long level;

    @OneToOne(targetEntity = MainBase.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MainBase mainBase;

    @Column(columnDefinition = "boolean default true")
    private boolean enabled;

    @Column(columnDefinition = "boolean default true")
    private boolean news;

    @JsonIgnore
    private LocalDateTime lastAccessAt;

    @JsonIgnore
    private String token;


    public User(Long id, String name, String lastname, String username, String email, String password) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String name, String lastname, String username, String email, String password, UUID publicId, long level, boolean enabled, LocalDateTime lastAccessAt) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.publicId = publicId;
        this.level = level;
        this.enabled = enabled;
        this.lastAccessAt = lastAccessAt;
    }
}
