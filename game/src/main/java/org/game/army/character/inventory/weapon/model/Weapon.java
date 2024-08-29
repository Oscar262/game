package org.game.army.character.inventory.weapon.model;

import org.game.auth.model.User;
import org.game.army.character.model.Character;

import javax.persistence.*;
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
    private Map<Character.Attribute, Long> attribute;

    private Type type;


    public enum Type{
        SWORD,
        BOW,
        SPEAR,
        GUN,
        SHIELD,
        MAGIC,
        DAGGER
    }
}
