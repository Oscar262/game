package org.game.army.character.inventory.costume.model;

import org.game.admin.model.User;
import org.game.army.character.model.Character;
import org.springframework.data.util.Pair;

import javax.persistence.*;
import java.util.Map;

@Entity
public class Costume {

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
        BOOTS,
        GLOVES,
        HELMET,
        PANTS,
        RING,
        NECKLACE
    }
}
