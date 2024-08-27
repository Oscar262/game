package org.game.army.character.inventory.weapon.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.game.admin.model.User;
import org.game.army.character.model.Character;
import org.hibernate.annotations.Type;
import org.springframework.data.util.Pair;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Weapon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = User.class)
    private User user;

    private String name;

    private byte[] image;

    @org.hibernate.annotations.Type(type = "json")
    @Column(
            name = "skills",
            columnDefinition = "jsonb"
    )
    private Map<Character.Attribute, Pair<String, Long>> attribute;

    @JsonProperty("secondary_enabled")
    @Column(name = "secondary_enabled", columnDefinition = "boolean default true")
    private boolean secondaryEnabled;

    private Type type;

    public enum Type{
        SWORD,
        BOW,
        SPEAR,
        GUN,
        SHIELD,
        MAGIC
    }
}
